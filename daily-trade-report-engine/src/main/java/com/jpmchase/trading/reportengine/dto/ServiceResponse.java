package com.jpmchase.trading.reportengine.dto;

public class ServiceResponse {
	private String responseMessage;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public String toString() {
		return "ServiceResponse{" +
				"responseMessage='" + responseMessage + '\'' +
				'}';
	}
}
