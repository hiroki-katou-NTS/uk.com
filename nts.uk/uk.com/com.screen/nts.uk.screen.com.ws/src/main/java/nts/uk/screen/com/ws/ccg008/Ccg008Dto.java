package nts.uk.screen.com.ws.ccg008;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ccg008Dto {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 締めID */
	private int closureId;

	/** 表示年月 */
	private int currentOrNextMonth;
	
	private String startDate;
	
	private String endDate;

}
