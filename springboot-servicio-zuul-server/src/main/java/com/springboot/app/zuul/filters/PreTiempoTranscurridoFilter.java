package com.springboot.app.zuul.filters;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


@Component
public class PreTiempoTranscurridoFilter extends ZuulFilter {

	private static Logger log =  LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class); 
	
	//Para validar si o no ejecutaremos el filtro
	@Override
	public boolean shouldFilter() {
		return true;
	}

	//Logica
	@Override
	public Object run() throws ZuulException {
		
			//Necesitamos pasar datos al request
		//1.- Obtenemos el objeto HttpRequest
		RequestContext ctx =  RequestContext.getCurrentContext();
		//2.-A traves del objeto contexto obtenemos el request
		HttpServletRequest request = ctx.getRequest();
		
		log.info(String.format("%s request enrutado a %s", request.getMethod(), request.getRequestURL().toString()));
		
		//3.- Pasamos datos al request
		Long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		return null;
	}

	//Definimos el tipo de filtro
	@Override
	public String filterType() {
		return "pre";
	}

	//Orden
	@Override
	public int filterOrder() {
		return 1;
	}

}
 