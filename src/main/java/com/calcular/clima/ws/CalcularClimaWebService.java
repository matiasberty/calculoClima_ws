package com.calcular.clima.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calcular.clima.ws.datos.InfoDias;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import procesoclima.clima.Climas;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ "*/*" })
@Path("/clima")
public class CalcularClimaWebService {
	private static final Logger LOG = LoggerFactory.getLogger(CalcularClimaWebService.class);

	@GET
	@Path("/calcularClima")
	public String calcularClima() throws Exception {
		String climas = Climas.procesarClima();
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(climas);

		InfoDias.setInfo(json.get("dias").getAsString());

		JsonObject jo = (JsonObject) parser.parse(json.get("resultados").getAsString());

		String res = "";
		res += "Proceso Finalizado - Resultados obtenidos: \n";
		res += "  - Cantidad de días de Normal: " + jo.get("diasNormal").getAsString() + ". \n";
		res += "  - Cantidad de días de Sequía: " + jo.get("diasSequia").getAsString() + ". \n";
		res += "  - Cantidad de días con Condiciones Optimas: " + jo.get("diasCondicionesOptimas").getAsString() + ". \n";
		res += "  - Cantidad de días de Lluvia: " + jo.get("diasLluvia").getAsString() + ". \n";
		res += "  - Cantidad de Períodos de Lluvia: " + jo.get("cantPeriodoLluvia").getAsString() + ". \n";
		res += "  - Picos Max. de Lluvia: " + jo.get("picoMaxLluvia").getAsString() + ". \n";
		res += "  - Donde el pico máximo será: " + jo.get("diaMaxLluvia").getAsString();

		return res;
	}

	@GET
	@Path("/getClimaDia")
	public String getClima(@QueryParam("dia") String dia) throws Exception {
		if (dia == null || dia.equals("")) {
			return "Debe ingresar el día del que quiere obtener el clima.";
		}

		if (InfoDias.getInfo() != null && !InfoDias.getInfo().equals("")) {
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(InfoDias.getInfo());

			String res = "";
			switch (json.get(dia).getAsString()) {
			case "N":
				res = "Normal";
				break;
			case "L":
				res = "Lluvia";
				break;
			case "CO":
				res = "Condiciones Optimas";
				break;
			case "S":
				res = "Sequía";
				break;
			}

			return "El clima que tendrán en el día " + dia + " será " + res + ".";
		} else {
			return "Actualmente no calculó los climas y por eso no se pudo obtener el clima del día solicitado.";
		}
	}
}