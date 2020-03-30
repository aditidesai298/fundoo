package com.bridgelabz.fundoo.dto;
import io.swagger.annotations.ApiModelProperty;


public class LabelDto {
	
	
	@ApiModelProperty(notes = "Name of the label", name = "label name")

	private String labelName;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}