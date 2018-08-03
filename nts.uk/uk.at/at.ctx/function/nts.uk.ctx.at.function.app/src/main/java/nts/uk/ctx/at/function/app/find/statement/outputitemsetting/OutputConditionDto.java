package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.find.statement.export.EmployeeInfor;


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
	private List<EmployeeInfor> lstEmployee ;
	
	/** The output set code. */
	private String outputSetCode;
	
	/** The card num not register. */
	private boolean cardNumNotRegister;

}
