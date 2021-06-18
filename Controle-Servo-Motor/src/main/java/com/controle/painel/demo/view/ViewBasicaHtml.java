package com.controle.painel.demo.view;

import org.json.JSONObject;

public class ViewBasicaHtml {

    private final static String ip = "192.168.1.175";

    private final static String tela = "<html><body>\n" +
            "\n" +
            "    <center>\n" +
            "        <p>{texto}</p>\n" +
            "        <p></p>\n" +
            "        <p>Passando posição para o Servo Motor e desativando modo automático:</p>\n" +
            "        <input type=\"text\" id=\"in\">\n" +
            "        <button type=\"submit\" onclick=\"Manual()\">Manual</button>\n" +
            "        <p>Ativando Modo automático:</p>\n" +
            "        <button type=\"submit\" onclick=\"Automatico()\">Automatico</button>\n" +
            "    </center>\n" +
            "\n" +
            "</body></html>\n" +
            "<script>\n" +
            "    function Manual() {\n" +
            "\n" +
            "        var texto = document.querySelector(\"#in\").value;\n" +
            "        document.querySelector(\"#in\").value = '';\n" +
            "        window.location.assign(\"http://" + ip + ":8080/PositionPainel?position=\" + texto);\n" +
            "    }\n" +
            "\n" +
            "    function Automatico() {\n" +
            "        window.location.assign(\"http://" + ip + ":8080/unlock\");\n" +
            "    }\n" +
            "</script>";

    private final static String tela2 = "<html><body>\n" +
            "    <center>\n" +
            "        <p>Para voltar para a tela anterios aperto o botão abaixo:</p>\n" +
            "        <button type=\"submit\" onclick=\"Voltar()\">Voltar</button>\n" +
            "    </center>\n" +
            "</body></html>\n" +
            "<script>\n" +
            "        alert('{texto}');\n" +
            "    function Voltar() {\n" +
            "        window.location.assign(\"http://" + ip + ":8080/\");" +
            "    }\n" +
            "</script>";

    public String getTela(String jsonStr) {

        JSONObject json = new JSONObject(jsonStr);

        if (json.getString("error").equals("null")) {

            StringBuffer texto = new StringBuffer();
            texto.append("<p>Na cidade de ");
            texto.append(json.getString("city_name"));
            texto.append(" na data ");
            texto.append(json.getString("date"));
            texto.append(" as ");
            texto.append(json.getString("time"));
            texto.append(", a descrição é: ");
            texto.append(json.getString("description"));
            texto.append(", com humidade em torno de ");
            texto.append(json.getString("humidity"));
            texto.append(". O motor está da posição de: ");
            texto.append(json.getString("positon_engine"));
            texto.append("</p>");

            texto.append("\n<p>");
            texto.append(" maxima do dia: ");
            texto.append(json.getString("max"));
            texto.append(" minima do dia: ");
            texto.append(json.getString("min"));
            texto.append("</p>");

            return tela.replace("{texto}", texto.toString());
        }

        jsonStr = jsonStr.replace("{", "");
        jsonStr = jsonStr.replace("}", "");

        return tela.replace("{texto}", jsonStr);

    }

    public String getTela2(String json) {

        json = json.replace("{", "");
        json = json.replace("}", "");
        json = json.replace("sucesso:", "");
        json = json.replace("error:", "");
        return tela2.replace("{texto}", json);

    }
}
