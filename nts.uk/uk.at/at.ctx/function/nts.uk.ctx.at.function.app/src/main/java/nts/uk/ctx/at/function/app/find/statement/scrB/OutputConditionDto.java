package nts.uk.ctx.at.function.app.find.statement.scrB;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * class OutputConditionDto.
 */
@Getter
@NoArgsConstructor
public class OutputConditionDto {
	
	/** The start date. */
	private String startDate;
	
	/** The end date. */
	private String endDate;
	
	/** The lst employee. */
	private List<String> lstEmployee ;
	
	/** The output set code. */
	private String outputSetCode;
	
	/** The card num not register. */
	private boolean cardNumNotRegister;

}
