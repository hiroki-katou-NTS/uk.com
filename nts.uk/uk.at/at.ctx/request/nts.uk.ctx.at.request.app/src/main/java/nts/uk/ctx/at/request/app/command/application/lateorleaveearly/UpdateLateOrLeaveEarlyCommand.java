package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class UpdateLateOrLeaveEarlyCommand {
	
	/** 会社ID */
	private String companyID;

	/** 申請ID */
	private String appID;
	
	private Long version;
	
   /** 申請日*/
	private GeneralDate applicationDate;
	
	private int prePostAtr;

	/** 実績取消区分 */
	private int actualCancel;

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
