package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


public class Balance extends AbstractItem {


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("available_credits")
	public double availableCredits;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("history")
	public BalanceHistory[] history;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("past_due")
	public boolean pastDue;


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty("total_outstanding")
	public double totalOutstanding;




}