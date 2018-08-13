package nts.uk.ctx.at.request.dom.application.appabsence;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.AppTimeDigest;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubDigestion;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubTargetDigestion;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author loivt
 * 休暇申請
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppAbsence extends AggregateRoot{
	/**
	 * application
	 */
	private Application_New application;
	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 休暇種類
	 */
	private HolidayAppType holidayAppType;
	/**
	 * 勤務種類コード
	 */
	private WorkTypeCode workTypeCode;
	/**
	 * 就業時間帯コード
	 */
	private WorkTimeCode workTimeCode;
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
	private AllDayHalfDayLeaveAtr allDayHalfDayLeaveAtr;
	/**
	 * 開始時刻
	 */
	private TimeWithDayAttr startTime1;
	/**
	 * 終了時刻
	 */
	private TimeWithDayAttr endTime1;
	/**
	 * 開始時刻2
	 */
	private TimeWithDayAttr startTime2;
	/**
	 * 終了時刻2
	 */
	private TimeWithDayAttr endTime2;
	/**
	 * 消化対象代休管理
	 */
	private List<SubTargetDigestion> subTargetDigestions;
	
	/**
	 * 消化対象振休管理
	 */
	private List<SubDigestion> subDigestions;
	/**
	 * 特別休暇申請
	 */
	private AppForSpecLeave appForSpecLeave;
	
	/**
	 * 時間消化申請
	 */
	private AppTimeDigest appTimeDigest;
	
	public AppAbsence(String companyID,
			String appID,
			Integer holidayAppType,
			String workTypeCode,
			String workTimeCode,
			boolean halfDayFlg,
			boolean changeWorkHour,
			int allDayHalfDayLeaveAtr,
			Integer startTime1,
			Integer endTime1,
			Integer startTime2,
			Integer endTime2,
			AppForSpecLeave appForSpecLeave){
		this.companyID = companyID;
		this.appID = appID;
		this.holidayAppType = holidayAppType == null ? null : EnumAdaptor.valueOf(holidayAppType, HolidayAppType.class);
		this.workTypeCode = workTypeCode == null ? null : new WorkTypeCode(workTypeCode);
		this.workTimeCode = workTimeCode == null ?  null : new WorkTimeCode(workTimeCode);
		this.halfDayFlg = halfDayFlg;
		this.changeWorkHour = changeWorkHour;
		this.allDayHalfDayLeaveAtr = EnumAdaptor.valueOf(allDayHalfDayLeaveAtr, AllDayHalfDayLeaveAtr.class);
		this.startTime1 = startTime1 == null ? null : new TimeWithDayAttr(startTime1);
		this.endTime1 = endTime1 == null ? null : new TimeWithDayAttr(endTime1);
		this.startTime2 = startTime2 == null ? null : new TimeWithDayAttr(startTime2);
		this.endTime2 = endTime2 == null ? null : new TimeWithDayAttr(endTime2);
		this.appForSpecLeave = appForSpecLeave;
	}
}
