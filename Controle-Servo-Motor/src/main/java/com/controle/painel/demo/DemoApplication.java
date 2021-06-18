package com.controle.painel.demo;

import com.controle.painel.demo.ContollerServoMotor.ControllerServoMotor;
import com.controle.painel.demo.apiTempo.ApiTempo;
import com.controle.painel.demo.exceptions.PassingPositionEngineException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static volatile boolean block = true;

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
        // controle automatico do servo motor
        new Thread(() -> automaticControl()).start();

    }

    private static void automaticControl() {
        ApiTempo api = new ApiTempo();

        while (true) {

            try {

                if (block) {

                    int h = api.timeH();
                    int position = ((15 * h - 90) > 0 && (15 * h - 90) <= 180 ? (15 * h - 90) : 1);
                    ControllerServoMotor.chooseModule("passingPositionEngine", position);

                } else
                    Thread.sleep(1000);

                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (PassingPositionEngineException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
