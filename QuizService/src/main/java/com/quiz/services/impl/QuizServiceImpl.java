package com.quiz.services.impl;

import java.text.Collator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.quiz.entities.Quiz;
import com.quiz.repo.QuizRepo;
import com.quiz.services.QuestionClient;
import com.quiz.services.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

	private QuizRepo quizRepo;
	private QuestionClient questionClient;
	
	public QuizServiceImpl(QuizRepo quizRepo, QuestionClient questionClient) {
		super();
		this.quizRepo = quizRepo;
		this.questionClient = questionClient;
	}

	@Override
	public Quiz getOne(Integer id) {
		// TODO Auto-generated method stub
		Quiz quiz = quizRepo.findById(id).orElseThrow(()->new RuntimeException("Quiz not found"));
		quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
		return quiz;
	}

	@Override
	public Quiz add(Quiz quiz) {
		// TODO Auto-generated method stub
		return quizRepo.save(quiz);
	}

	@Override
	public List<Quiz> get() {
		// TODO Auto-generated method stub
		List<Quiz> quizes = quizRepo.findAll();
		List<Quiz> newQuizes = quizes.stream().map(quiz->{
		quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
		return quiz;
		}).collect(Collectors.toList());
		return newQuizes;
	}

}
