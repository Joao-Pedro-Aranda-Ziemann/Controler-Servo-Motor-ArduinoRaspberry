package com.controle.painel.demo.ContollerServoMotor;

import com.controle.painel.demo.exceptions.PassingPositionEngineException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ControllerServoMotor {

    private static final String HOME = System.getenv("HOME");
    private static int grausMotor = 0;

    public static synchronized Integer chooseModule(String module, Integer position) throws Exception {

        if (module.equals("passingPositionEngine"))
            passingPositionEngine(position);
        else
            return getCurrentPositonEngine();

        return null;
    }

    private static synchronized void passingPositionEngine(Integer positon) throws Exception {

        String[] commandPy = {"python", HOME + "/controle-servo-motor/passingPositionEngine.py", positon.toString()};

        try (InputStream is = new ProcessBuilder(commandPy).start().getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {

            br.readLine().lines().filter(n -> n.equals("Sucesso"))
                    .findFirst().orElseThrow(PassingPositionEngineException::new);

        }

        grausMotor = (positon == null ? 0 : positon);
        commandPy = null;
    }

    private static synchronized int getCurrentPositonEngine() {
        return grausMotor;
    }

}
