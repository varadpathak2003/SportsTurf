package com.example.sport.controller;

import com.example.sport.dto.DashboardStats;
import com.example.sport.model.*;
import com.example.sport.repository.*;
import com.example.sport.service.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameService gameService;

    @Autowired
    private GroundService groundService;
    
    private final DashboardService dashboardService;

    public AuthController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    
    @GetMapping("/venue")
    public String showVenuePage(HttpSession session, Model model) {
        Object user = session.getAttribute("loggedInUser"); // or whatever your session key is
        
        if (user != null) {
            // Optionally, pass any user info to the model
            model.addAttribute("user", user);
            return "user/userVenue";  // Loads userVenue.html (authenticated version)
        }
        return "user/venue"; // Loads venue.html (guest version)
    }

    
    
    @GetMapping("/contact")
    public String showContactPage() {
        return "contant";  // Loads venue.html
    }
    
    @GetMapping("/getAllGrounds")
    @ResponseBody
    public List<Ground> getAllGrounds(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Long gameId) {

        List<Ground> allGrounds = groundService.getAllGrounds();
        
        // Apply filters if provided
        return allGrounds.stream()
                .filter(g -> (name == null || g.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(g -> (city == null || g.getLocation().equalsIgnoreCase(city)))
                .filter(g -> (gameId == null || g.getGame().getId().equals(gameId)))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/")
    public String home() {
        return "index"; // Redirects to index.html in 'static'
    }
    @GetMapping("/login1")
    public String showLoginPage() {
        return "login"; // This remains the same
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password, 
                        HttpServletRequest request,
                        HttpSession session,
                        Model model,
                        HttpServletResponse response) {

        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "Email and password are required");
            return "login"; // Return login.html with error
        }

        try {
            User user = userRepository.findByEmailAndPassword(email, password);

            if (user != null) {
                
                session.setAttribute("loggedInUser", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole().getRoleName());
                session.setMaxInactiveInterval(30 * 60);

                return user.getRole().getRoleName().equalsIgnoreCase("ADMIN") ?
                    "redirect:/admin/dashboard" : "redirect:/user/dashboard";
            } else {
                model.addAttribute("error", "Invalid email or password");
                return "login"; // Stay on login page with error message
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred. Please try again.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
       return showLoginPage();
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login1"; // Redirect if not logged in
        }
        
        // Fetch all games and ground locations dynamically
        List<Game> games = gameService.getAllGames();
        List<Ground> grounds = groundService.getGroundsByCity(loggedInUser.getCity());
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("games", games);
        model.addAttribute("grounds", grounds);
        return "user/userDash";
    }

  
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        // Check if session exists
        if (loggedInUser == null) {
            return "redirect:/login1"; // Redirect if not logged in
        }

        // Ensure user has ADMIN role
        if (!"ADMIN".equals(loggedInUser.getRole().getRoleName())) {
            return "redirect:/user/dashboard"; // Redirect non-admin users
        }
        DashboardStats stats = dashboardService.getDashboardStats();
        model.addAttribute("stats", stats);
        model.addAttribute("loggedInUser", loggedInUser); // Pass admin details to view
        return "admin/adminDash";
    }


}
