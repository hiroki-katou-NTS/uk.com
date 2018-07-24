package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Constraint {
	private String cDisplayType;
	private Boolean required;
	private String primitiveValue;
	
	private String min;
    private String max;
	
	public Constraint(String cDisplayType, Boolean required, String primitiveValue) {
		this.cDisplayType = cDisplayType;
		this.required = required;
		this.primitiveValue = primitiveValue;
	}
	
	public Constraint createMinMax(String min, String max){
		this.min = min;
		this.max = max;
		return this;
	}
//	public Constraint(String cDisplayType, Boolean required) {
//		super();
//		this.cDisplayType = cDisplayType;
//		this.required = required;
//	}
	
}
