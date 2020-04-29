package com.exchange.services.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exchange.services.exception.NoMappingFoundException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "Application error controller. (Just for presentation. No one needs this controller when using this api.)")
public class AppErrorController extends AbstractErrorController {
	 
	private static Logger logger = LoggerFactory.getLogger(AppErrorController.class);
	 
	private static final String ERROR = "/error";

	public AppErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@RequestMapping(value = ERROR, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value = "Prints directly NoMappingFoundException.", response = Map.class)
	public Map<String, Object> handleError(HttpServletRequest request) throws NoMappingFoundException {
		logger.warn("No mapping found...");
		throw new NoMappingFoundException("No mapping found.");
//		Map<String, Object> errorAttributes = super.getErrorAttributes(request, true);
//		return errorAttributes;
	}

	@Override
	public String getErrorPath() {
		return ERROR;
	}

}
