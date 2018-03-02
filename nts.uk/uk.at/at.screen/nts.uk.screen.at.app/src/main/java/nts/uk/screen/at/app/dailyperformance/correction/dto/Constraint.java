package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Constraint {
	private String cDisplayType;
	private Boolean required;
	private String primitiveValue;
//	private int min;
//	private int max;
//	public Constraint(String cDisplayType, Boolean required) {
//		super();
//		this.cDisplayType = cDisplayType;
//		this.required = required;
//	}
	
}
