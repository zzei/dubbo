package com.zei.happy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zei.happy.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PersonServiceImpl implements PersonService{

    @Override
    public Person getPerson(int id) {
        return new Person().setId(id).setName("张三" + id + "号").setAge(id+id+id);
    }
}
