package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class LateOrLeaveEarlyImport {

	/**
	 * 申請日
	 */
	private GeneralDate appDate;
	
	/**
	 * 早退１
	 */
	private Integer early1;
	
	/**
	 * 遅刻１
	 */
	private Integer late1;
	
	/**
	 * 早退2
	 */
	private Integer early2;
	
	/**
	 * 遅刻2
	 */
	private Integer late2;
}
