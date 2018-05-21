package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Constraint {
	private String cDisplayType;
	private Boolean required;
	private String primitiveValue;
}
