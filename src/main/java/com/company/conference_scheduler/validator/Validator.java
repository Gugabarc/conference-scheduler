package com.company.conference_scheduler.validator;

import java.util.List;

import com.company.conference_scheduler.model.Error;

public interface Validator<T> {

	List<Error> validate(T t);
}
