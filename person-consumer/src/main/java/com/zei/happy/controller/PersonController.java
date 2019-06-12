package com.zei.happy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zei.happy.domain.Person;
import com.zei.happy.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Reference
    PersonService personService;

    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable("id") int id){
        Person person = personService.getPerson(id);
        return person;
    }
}
