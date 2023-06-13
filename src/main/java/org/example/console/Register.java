package org.example.console;

import org.example.enums.MainMenuAns;
import org.example.models.User;
import org.example.services.VehicleService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Register {
    private final BufferedReader reader;
    private final VehicleService service;

    public Register(VehicleService service) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.service = service;
    }

    public void run() {
        boolean run = true;
        while (run) {
            try {
                MainMenuAns ans = getMainMenuAns();
                switch (ans) {
                    case LOGIN -> {
                        User user = getUserByLogin();
                        if (user == null) {
                            continue;
                        }

                        if (checkPassword(user)) {
                            Console console = new Console(service, reader, user);
                            System.out.println("Вы успешно вошли!");
                            console.run();
                        } else {
                            System.out.println("Неверный пароль");
                        }
                    }
                    case REGISTER -> {
                        String login = getLogin();
                        String password = getPassword();
                        User user = service.saveUser(login, password);

                        Console console = new Console(service, reader, user);
                        System.out.println("Вы успешно зарегистрировались!");
                        console.run();
                    }
                    case EXIT -> run = false;
                }


            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private User getUserByLogin() throws IOException {
        User user = null;

        System.out.println("Введите логин :");
        try {
            String login = reader.readLine();
            if (login == null || login.isBlank()) {
                throw new IllegalArgumentException("Логин не должен быть пустым, попробуйте ещё раз");
            }

            user = service.getUserByLogin(login);
            if (user == null) {
                throw new IllegalArgumentException("Пользователя с таким логином не существует");
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    private boolean checkPassword(User user) throws IOException {
        System.out.println("Введите пароль :");
        String password = reader.readLine();
        return password != null &&
                !password.isBlank() &&
                user.getPassword().equals(User.getHashedPassword(password));
    }

    private String getLogin() throws IOException {
        while (true) {
            System.out.println("Введите логин :");
            try {

                String login = reader.readLine();

                if (login == null || login.isBlank()) {
                    throw new IllegalArgumentException("Логин не должен быть пустым, попробуйте ещё раз");
                }

                User user = service.getUserByLogin(login);
                if (user != null) {
                    throw new IllegalArgumentException("Пользователь с таким логином уже существует");
                }

                return login;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String getPassword() throws IOException {
        while (true) {
            System.out.println("Введите пароль :");
            try {
                String password = reader.readLine();
                if (password.isBlank() || password.length() < 8) {
                    throw new IllegalArgumentException(
                            "Пароль не должен содержать не менее 8 символов"
                    );
                }

                System.out.println("Повторите пароль :");
                String passwordRepeat = reader.readLine();
                if (passwordRepeat.isBlank() || !password.equals(passwordRepeat)) {
                    throw new IllegalArgumentException("Пароли не совпадают");
                }

                return password;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private MainMenuAns getMainMenuAns() throws IOException {
        while (true) {
            StringBuilder ansLogOrReg = new StringBuilder()
                    .append("Введите :\n")
                    .append("\tВойти - 'log'\n")
                    .append("\tЗарегистрироваться - 'reg'\n")
                    .append("\tВыйти - 'exit'");
            System.out.println(ansLogOrReg);

            try {
                String ans = reader.readLine();
                if (ans.isBlank()) {
                    throw new IllegalArgumentException("Некорректный ответ");
                } else {
                    return switch (ans) {
                        case "log" -> MainMenuAns.LOGIN;
                        case "reg" -> MainMenuAns.REGISTER;
                        case "exit" -> MainMenuAns.EXIT;
                        default -> throw new IllegalArgumentException("Некорректный ответ");
                    };
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
