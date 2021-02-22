package nts.uk.ctx.at.function.app.find.annualworkschedule;

import lombok.Data;


/**
 * Dto copy screen C KWR008
 */
@Data
public class AnnualWorkScheduleDuplicateDto {
	
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The selected type. */
	public int selectedType;
	
	/** The duplicate code. */
	public String duplicateCode;
	
	/** The duplicate name. */
	public String duplicateName;
	
	/** The layout id. */
	public String layoutId;

}
