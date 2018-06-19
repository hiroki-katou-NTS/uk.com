package nts.uk.ctx.at.function.app.find.monthlyworkschedule;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new monthly data infor return dto.
 */
@Data
public class MonthlyDataInforReturnDto {

	/** The code. */
	private String code;
	
	/** The name. */
	private String name;

	/**
	 * Instantiates a new monthly data infor return dto.
	 *
	 * @param code the code
	 * @param name the name
	 */
	public MonthlyDataInforReturnDto(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	
}
