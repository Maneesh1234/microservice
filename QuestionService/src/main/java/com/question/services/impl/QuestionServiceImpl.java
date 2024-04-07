package com.question.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.question.entities.Question;
import com.question.repo.QuestionRepo;
import com.question.services.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	private QuestionRepo questionRepo;
	
	public QuestionServiceImpl(QuestionRepo questionRepo) {
		super();
		this.questionRepo = questionRepo;
	}

	@Override
	public Question create(Question question) {
		// TODO Auto-generated method stub
		return questionRepo.save(question);
	}

	@Override
	public Question getOne(Long questionId) {
		// TODO Auto-generated method stub
		return questionRepo.findById(questionId).orElseThrow(()->new RuntimeException("Question not found"));
	}

	@Override
	public List<Question> get() {
		// TODO Auto-generated method stub
		return questionRepo.findAll();
	}

	@Override
	public List<Question> getByQuizID(Long quizID) {
		// TODO Auto-generated method stub
		return questionRepo.findByQuizId(quizID);
	}

}
