package com.codecool.labyrinth_generator.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorHandler implements ErrorController {

    @Override
    public String getErrorPath(){
        return "/error";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String renderErrorPage(HttpServletResponse response, Model model){

        String errorMsg = "";
        int statusCode = getErrorCode(response);

        switch (statusCode) {
            case 400:
                errorMsg = "Error 400: Bad Request";
                break;

            case 401:
                errorMsg = "Error 401: Unauthorized";
                break;

            case 404:
                errorMsg = "Error 404: Resource not found";
                break;

            case 500:
                errorMsg = "Error 500: Internal Server Error";
                break;
        }

        model.addAttribute("errormsg", errorMsg);
        return "main";
    }

    private int getErrorCode(HttpServletResponse response) {
        return response.getStatus();
    }
}