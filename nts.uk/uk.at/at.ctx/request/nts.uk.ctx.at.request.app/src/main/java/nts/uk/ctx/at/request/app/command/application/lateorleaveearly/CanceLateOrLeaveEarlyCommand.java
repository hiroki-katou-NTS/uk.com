package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author hieult
 *
 */
@Getter
public class CanceLateOrLeaveEarlyCommand {
	private int version;
	
	/** 会社ID */
	private String companyID;

	/** 申請ID */
	private String appID;
	
   /** 申請日*/
	private GeneralDate applicationDate;

	/** 実績取消区分 */
	private int actualCancelAtr;

	/** 早退1 */
	private int early1;

	/** 早退時刻1 */
	private int earlyTime1;

	/** 遅刻1 */
	private int late1;

	/** 遅刻時刻1 */
	private int lateTime1;

	/** 早退2 */
	private int early2;

	/** 早退時刻2 */
	private int earlyTime2;

	/** 遅刻2 */
	private int late2;

	/** 遅刻時刻2 */
	private int lateTime2;
	
	/** 定型理由 typicalReason :DB reasonTemp */ 
	
	private String reasonTemp;
	
	/** 申請理由 appReason */
	
	private String appReason;
	

}
