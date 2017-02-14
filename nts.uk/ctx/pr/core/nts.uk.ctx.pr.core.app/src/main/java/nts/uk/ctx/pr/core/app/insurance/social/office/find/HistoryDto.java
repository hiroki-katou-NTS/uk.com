package nts.uk.ctx.pr.core.app.insurance.social.office.find;

import lombok.Data;

@Data
public class HistoryDto {
	/** The company code. */
	private String companyCode;
	
	/** The office code. */
	public String officeCode;
	
	/** The start. */
	public String start;
	
	/** The end. */
	public String end;
	
	/** The code. */
	private String code;

	/** The name. */
	private String name;
}
