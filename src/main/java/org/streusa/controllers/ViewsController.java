package org.streusa.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class ViewsController {

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("login");
        response.addHeader("Cache-Control", "no-store");
        return modelAndView;
    }

    @GetMapping("/myShop")
    public ModelAndView myShop(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true)) {

            modelAndView = new ModelAndView("myShop");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

    @GetMapping("/deadliners")
    public ModelAndView deadliners(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true)) {
            modelAndView = new ModelAndView("deadliners");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

    @GetMapping("/users")
    public ModelAndView users(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true)) {
            modelAndView = new ModelAndView("users");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

    @GetMapping("/books")
    public ModelAndView books(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true)) {
            modelAndView = new ModelAndView("books");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

    @GetMapping("/editors")
    public ModelAndView editors(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true)) {
            modelAndView = new ModelAndView("editors");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

    @GetMapping("/suppliers")
    public ModelAndView suppliers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true)) {
            modelAndView = new ModelAndView("suppliers");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

    @GetMapping("/genres")
    public ModelAndView genres(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true)) {
            modelAndView = new ModelAndView("genres");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

    @GetMapping("/merchandise")
    public ModelAndView merchandise(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true)) {
            modelAndView = new ModelAndView("merchandise");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true)) {
            modelAndView = new ModelAndView("shoppingCart");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

    @GetMapping("/reportingActive")
    public ModelAndView reportingActive(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("authenticated") != null && session.getAttribute("authenticated").equals(true) && session.getAttribute("role").equals("SuperUser")) {
            modelAndView = new ModelAndView("reportingActive");
            response.addHeader("Cache-Control", "no-store");
            return modelAndView;
        } else {
            response.sendRedirect("/login");
            return null;
        }
    }

}
