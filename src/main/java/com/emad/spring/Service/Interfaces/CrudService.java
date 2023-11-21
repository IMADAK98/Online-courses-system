package com.emad.spring.Service.Interfaces;

import java.util.List;

public interface CrudService<T,ID> {

     List<T> getAll();

     T getById(ID id);

     T create(T t);

    void delete(ID id);

    T update (T t , ID id);


}
