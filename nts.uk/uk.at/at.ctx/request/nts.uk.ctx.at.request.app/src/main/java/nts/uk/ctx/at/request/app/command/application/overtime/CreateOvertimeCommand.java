package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.common.appapprovalphase.AppApprovalPhaseCmd;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;

@Getter
public class CreateOvertimeCommand {

	/**
	 * 申請
	 */
	/**
	 * 申請.会社ID
	 */
	private String companyID;

	/**
	 * 申請.申請ID
	 */
	private String appID;

	/**
	 * 申請.申請日
	 */
	private GeneralDate applicationDate;

	/**
	 * 申請.事前事後区分
	 */
	private int prePostAtr;

	/**
	 * 申請.申請者
	 */
	private String applicantSID;

	/**
	 * 申請.申請理由 = 申請.申請定型理由 + \n + 申請.申請理由
	 */
	private String applicationReason;

	/** Phase list */
	private List<AppApprovalPhaseCmd> appApprovalPhaseCmds;

	/**
	 * 残業申請
	 */
	/**
	 * 残業申請.勤務種類コード
	 */
	private WorkTypeOvertime workType;

	/**
	 * 残業申請.就業時間帯コード
	 */
	private SiftType siftType;

	/**
	 * 残業申請.勤務開始時刻1
	 */
	private int workClockFrom1;
	/**
	 * 残業申請.勤務終了時刻1
	 */
	private int workClockTo1;
	/**
	 * 残業申請.勤務開始時刻2
	 */
	private int workClockFrom2;
	/**
	 * 残業申請.勤務終了時刻2
	 */
	private int workClockTo2;
	/**
	 * 残業申請時間設定
	 */
	private List<OvertimeInputCommand> overTimeInput;

	/**
	 * 残業申請.残業区分
	 */
	private int overtimeAtr;

	/**
	 * 残業申請.就業時間外深夜時間
	 */
	private int overTimeShiftNight;

	/**
	 * 残業申請.ﾌﾚｯｸｽ超過時間
	 */
	private int flexExessTime;

	/**
	 * 残業申請.乖離理由 = 残業申請.乖離定型理由 + \n + 残業申請.乖離理由
	 */
	private String divergenceReasonContent;
}
