package com.quiz.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.entities.Quiz;
import com.quiz.services.QuizService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	private QuizService quizService;
	
	private Logger logger=LoggerFactory.getLogger(QuizController.class);

	public QuizController(QuizService quizService) {
		super();
		this.quizService = quizService;
	}
	
	@PostMapping("/")
	public Quiz create(@RequestBody Quiz quiz) {
		return quizService.add(quiz);
	}
	int retryCount =1;
	
	@GetMapping("/{quizId}")
	//@CircuitBreaker(name = "questionBreaker", fallbackMethod = "questionFallback")
//	@Retry(name="questionService", fallbackMethod = "questionFallback")
	@RateLimiter(name="questionRateLimiter", fallbackMethod = "questionFallback")
	public Quiz getQuiz(@PathVariable("quizId") Integer quizId) {
		logger.info("Retry count {} " , retryCount);
		retryCount++;
		return quizService.getOne(quizId);
	}
	
	
	//creating fallback method for circuitbreaker
	public Quiz questionFallback(Integer quizId, Exception ex) {
		//logger.info("Fallback method is called due to service is down ",ex.getMessage());
		Quiz quiz= new Quiz((long) 12345,"This quiz is created because some service is down ");
		return quiz;
	}
	
	@GetMapping("/")
	public List<Quiz> get(){
		return quizService.get();
	}
}
