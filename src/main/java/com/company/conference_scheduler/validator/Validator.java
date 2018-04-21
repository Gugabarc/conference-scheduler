package com.company.conference_scheduler.validator;

import com.company.conference_scheduler.model.ErrorList;

public interface Validator<T> {

	ErrorList validate(T t);
}
