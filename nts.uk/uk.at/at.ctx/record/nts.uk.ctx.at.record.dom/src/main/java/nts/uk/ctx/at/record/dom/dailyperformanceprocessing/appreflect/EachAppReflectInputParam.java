package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.goback.GobackReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime.HolidayWorktimePara;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.OvertimeParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange.WorkChangeCommonReflectPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType;
@Setter
@Getter
@AllArgsConstructor
public class EachAppReflectInputParam {
	/**
	 * 事前事後区分
	 * True：　事前
	 * False：　事後
	 */
	private boolean preApp;
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	private String sid;
	private GeneralDate ymd;
	/**
	 * 休暇
	 */
	private WorkChangeCommonReflectPara absence;
	/**
	 * 振休
	 */
	private CommonReflectParameter absenceLeave;
	/**
	 * 振出
	 */
	private CommonReflectParameter recruitment;
	/**
	 * 直行直帰
	 */
	private GobackReflectParameter goback;
	/**
	 * 休日出勤
	 */
	private HolidayWorktimePara holidayWork;
	/**
	 * 残業
	 */
	private OvertimeParameter overtime;
	/**
	 * 勤務変更
	 */
	private WorkChangeCommonReflectPara workChange;

}
