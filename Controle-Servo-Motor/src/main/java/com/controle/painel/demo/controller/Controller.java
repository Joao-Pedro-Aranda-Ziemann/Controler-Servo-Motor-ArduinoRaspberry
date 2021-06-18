package com.controle.painel.demo.controller;

import com.controle.painel.demo.ContollerServoMotor.ControllerServoMotor;
import com.controle.painel.demo.DemoApplication;
import com.controle.painel.demo.apiTempo.ApiTempo;
import com.controle.painel.demo.exceptions.CurrentPositionEngineException;
import com.controle.painel.demo.exceptions.HttpException;
import com.controle.painel.demo.exceptions.PassingPositionEngineException;
import com.controle.painel.demo.view.ViewBasicaHtml;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
public class Controller {

    private final ControllerServoMotor csm = new ControllerServoMotor();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String telaIncial() {

        String exceptionReturn;

        try {

            DemoApplication.block = false;

            ApiTempo api = new ApiTempo();
            JSONObject json = api.informationAboutApi();
            json.put("error", "null");

            int currentPositionEngine = csm.chooseModule("getCurrentPositonEngine", null);

            if (currentPositionEngine >= 0)
                json.put("positon_engine", currentPositionEngine);
            else
                throw new Exception("Não foi possível obter a posição do motor!");

            DemoApplication.block = true;

            return new ViewBasicaHtml().getTela(json.toString());

        } catch (HttpException httpEx) {
            httpEx.printStackTrace();
            exceptionReturn = "{error:\"Erro ao conectar na API tempo, favor entrar em contato com assistência técnica\",inf:\"" + httpEx.getMessage() + "\"}";
        } catch (UnknownHostException ue) {
            ue.printStackTrace();
            exceptionReturn = "{error:\"Não foi possível buscar os dados sobre o Tempo/Hora\",inf:\"" + ue.getMessage() + " \"}";
        } catch (PassingPositionEngineException | CurrentPositionEngineException ee) {
            exceptionReturn = "{error:\"Erro ao manipular o servo motor!\",inf:\"null\"";
        } catch (Exception e) {
            e.printStackTrace();
            exceptionReturn = "{error:\"Erro inesperado aconteceu, favor entrar em contato com assistência técnica\",inf:\"" + e.getMessage() + "\"}";
        }


        return new ViewBasicaHtml().getTela(exceptionReturn);
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.GET)
    public String unlock() {
        try {

            DemoApplication.block = true;
            return new ViewBasicaHtml().getTela2("{sucesso:\"Sucesso ao colocar no modo automático novamente!\"}");

        } catch (Exception e) {
            e.printStackTrace();
            return new ViewBasicaHtml().getTela2("{error:\"Não é possível colocar no modo automático, procure a assintecia tecnica\"}");
        }
    }

    @RequestMapping(value = "/PositionPainel", method = RequestMethod.GET)
    public String reiniciarPainel(String position) {
        try {

            csm.chooseModule("passingPositionEngine", Integer.parseInt(position));
            return new ViewBasicaHtml().getTela2("{sucesso:\"Sucesso ao colocar a placa no modo manual!\"}");

        } catch (Exception e) {
            e.printStackTrace();
            return new ViewBasicaHtml().getTela2("{error:\"Erro ao colocar no modo manual!\"}");
        }
    }
}

