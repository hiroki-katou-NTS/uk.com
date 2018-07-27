package nts.uk.ctx.at.request.pub.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class LateOrLeaveEarlyExport {
	
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
