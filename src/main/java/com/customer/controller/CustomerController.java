package com.customer.controller;

import com.customer.model.Customer;
import com.customer.model.CustomerForm;
import com.customer.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @Value("${upload-file}")
    private String uploadFile;

    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        List<Customer> customers = customerService.getAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        CustomerForm customerForm = new CustomerForm();
        modelAndView.addObject("customerForm", customerForm);
        return modelAndView;
    }

    @PostMapping("/create")
    public String createCustomer(@ModelAttribute("customerForm") CustomerForm customerForm) {
        MultipartFile file = customerForm.getImage();
        String fileName = file.getOriginalFilename();
        Customer customer = new Customer(customerForm.getId(), customerForm.getName(), customerForm.getAddress(), fileName);
        customerService.save(customer);
        try {
            byte[] bytes = file.getBytes();
            File file1 = new File(uploadFile + fileName);
            FileCopyUtils.copy(bytes, file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable("id") Long id) {
        Customer customer = customerService.getById(id);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/info/{id}")
    public ModelAndView showInfo(@PathVariable("id") Long id) {
        Customer customer = customerService.getById(id);
        ModelAndView modelAndView = new ModelAndView("/customer/info");
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable("id") Long id, Model model) {
        Customer customer = customerService.getById(id);
        model.addAttribute("customer", customer);
        return "/customer/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerService.delete(id);
        return "redirect:/customer";
    }

}
