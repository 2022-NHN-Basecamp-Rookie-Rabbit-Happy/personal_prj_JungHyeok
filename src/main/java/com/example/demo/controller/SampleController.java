package com.example.demo.controller;

import com.example.demo.dto.SampleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {

    @GetMapping("/ex1")
    public void ex1(){

    }

    @GetMapping({"/ex2","exLink"})
    public void exModel(Model model){
        List<SampleDTO> collect = LongStream.rangeClosed(1, 20).
                mapToObj(i -> new SampleDTO(i,"first.."+i,"last.."+i,LocalDateTime.now()))
                .collect(Collectors.toList());

        model.addAttribute("list", collect);
    }

    @GetMapping({"exInline"})
    public String exInline(RedirectAttributes redirectAttributes){

        SampleDTO sampleDTO = new SampleDTO(100L, "First...100", "Last...100", LocalDateTime.now());
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", sampleDTO);

        return "redirect:/sample/ex3";

    }

    @GetMapping("/ex3")
    public void ex3(){

    }

    @GetMapping({"/exLayout1","/exLayout2","/exTemplate","/exSidebar"})
    public void exLayout1(){

    }
}
