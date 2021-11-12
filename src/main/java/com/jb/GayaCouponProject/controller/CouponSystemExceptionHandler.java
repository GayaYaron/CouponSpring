package com.jb.GayaCouponProject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jb.GayaCouponProject.controller.model.ErrorDetail;
import com.jb.GayaCouponProject.exception.AlreadyExistsException;
import com.jb.GayaCouponProject.exception.CannotBeUpdatedException;
import com.jb.GayaCouponProject.exception.CategoryException;
import com.jb.GayaCouponProject.exception.CouponAlreadyPurchasedException;
import com.jb.GayaCouponProject.exception.CouponExpiredException;
import com.jb.GayaCouponProject.exception.InvalidEmailException;
import com.jb.GayaCouponProject.exception.JobGoWrongException;
import com.jb.GayaCouponProject.exception.LoginFailedException;
import com.jb.GayaCouponProject.exception.NoMoreCouponsException;
import com.jb.GayaCouponProject.exception.NotExistException;
import com.jb.GayaCouponProject.exception.NullVariableException;
import com.jb.GayaCouponProject.exception.util.ExceptionCode;

@ControllerAdvice
@RestController
public class CouponSystemExceptionHandler {
	@ExceptionHandler(AlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorDetail handleAlreadyExists(AlreadyExistsException exception) {
		return new ErrorDetail(ExceptionCode.ALREADY_EXIST.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(CannotBeUpdatedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorDetail handleCannotBeUpdated(CannotBeUpdatedException exception) {
		return new ErrorDetail(ExceptionCode.CANNOT_BE_UPDATED.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(CategoryException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDetail handleCategoryException(CategoryException exception) {
		return new ErrorDetail(ExceptionCode.CATEGORY.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(CouponAlreadyPurchasedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorDetail handleCouponPurchased(CouponAlreadyPurchasedException exception) {
		return new ErrorDetail(ExceptionCode.COUPON_ALREADY_PURCHASED.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(CouponExpiredException.class)
	@ResponseStatus(HttpStatus.GONE)
	public ErrorDetail handleCouponExpired(CouponExpiredException exception) {
		return new ErrorDetail(ExceptionCode.COUPON_EXPIRED.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(InvalidEmailException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDetail handleInvalidEmail(InvalidEmailException exception) {
		return new ErrorDetail(ExceptionCode.INVALID_EMAIL.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(JobGoWrongException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorDetail handleJobGoWrong(JobGoWrongException exception) {
		return new ErrorDetail(ExceptionCode.JOB_GO_WRONG.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(LoginFailedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorDetail handleLoginFailed(LoginFailedException exception) {
		return new ErrorDetail(ExceptionCode.LOGIN_FAILED.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(NoMoreCouponsException.class)
	@ResponseStatus(HttpStatus.OK)
	public ErrorDetail handleNoMoreCoupons(NoMoreCouponsException exception) {
		return new ErrorDetail(ExceptionCode.NO_MORE_COUPONS.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(NotExistException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDetail handleNotExist(NotExistException exception) {
		return new ErrorDetail(ExceptionCode.NOT_EXIST.getCode(), exception.getMessage());
	}
	
	@ExceptionHandler(NullVariableException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ErrorDetail handleNullVariable(NullVariableException exception) {
		return new ErrorDetail(ExceptionCode.NULL_VARIABLE.getCode(), exception.getMessage());
	}
}
