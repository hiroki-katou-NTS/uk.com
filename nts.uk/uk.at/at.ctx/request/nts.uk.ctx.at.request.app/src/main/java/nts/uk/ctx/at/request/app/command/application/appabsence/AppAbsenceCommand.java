package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.SubDigestionCmd;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.SubTargetDigestionCmd;

@Data
public class AppAbsenceCommand {
	
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 休暇種類
	 */
	private Integer holidayAppType;
	/**
	 * 勤務種類コード
	 */
	private String workTypeCode;
	/**
	 * 就業時間帯コード
	 */
	private String workTimeCode;
	/**
	 * 半日の組み合わせを表示する
	 */
	public boolean halfDayFlg;
	/**
	 * 就業時間帯変更する
	 */
	private boolean changeWorkHour;
	/**
	 * 終日半日休暇区分
	 */
	private Integer allDayHalfDayLeaveAtr;
	/**
	 * 開始時刻
	 */
	private Integer startTime1;
	/**
	 * 終了時刻
	 */
	private Integer endTime1;
	/**
	 * 開始時刻2
	 */
	private Integer startTime2;
	/**
	 * 終了時刻2
	 */
	private Integer endTime2;
	/**
	 * 消化対象代休管理
	 */
	private List<SubTargetDigestionCmd> subTargetDigestions;
	
	/**
	 * 消化対象振休管理
	 */
	private List<SubDigestionCmd> subDigestions;
	/**
	 * 特別休暇申請
	 */
	private AppForSpecLeaveCmd appForSpecLeave;
	
	/**
	 * 時間消化申請
	 */
	private AppTimeDigestCmd appTimeDigest;
}
