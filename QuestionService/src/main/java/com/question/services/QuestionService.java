package com.question.services;

import java.util.List;

import com.question.entities.Question;

public interface QuestionService {

	Question create(Question question);
	Question getOne(Long questionId);
	List<Question>  get();
	List<Question>  getByQuizID(Long quizID);
	
}
