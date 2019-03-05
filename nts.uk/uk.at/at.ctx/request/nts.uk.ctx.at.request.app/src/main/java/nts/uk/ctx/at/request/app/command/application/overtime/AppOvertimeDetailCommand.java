package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.NumberOfMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeAnnual;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimit;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitAverage;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneYear;

/**
 * 時間外時間の詳細
 */
@Data
@AllArgsConstructor
public class AppOvertimeDetailCommand {
	/**
	 * 申請時間
	 */
	private int applicationTime;

	/**
	 * 年月
	 */
	private int yearMonth;

	/**
	 * 実績時間
	 */
	private int actualTime;

	/**
	 * 限度エラー時間
	 */
	private int limitErrorTime;

	/**
	 * 限度アラーム時間
	 */
	private int limitAlarmTime;

	/**
	 * 特例限度エラー時間
	 */
	private Integer exceptionLimitErrorTime;

	/**
	 * 特例限度アラーム時間
	 */
	private Integer exceptionLimitAlarmTime;

	/**
	 * 36年間超過月
	 */
	private List<Integer> year36OverMonth;

	/**
	 * 36年間超過回数
	 */
	private int numOfYear36Over;

	public AppOvertimeDetail toDomain(String cid, String appId) {
		return new AppOvertimeDetail(
				cid, 
				appId, 
				new YearMonth(yearMonth), 
				new Time36Agree(
						new AttendanceTimeMonth(applicationTime) , 
						new Time36AgreeMonth(
								new AttendanceTimeMonth(actualTime), 
								new LimitOneMonth(limitAlarmTime), 
								new LimitOneMonth(limitErrorTime), 
								new NumberOfMonth(numOfYear36Over), 
								year36OverMonth.stream().map(x -> new YearMonth(x)).collect(Collectors.toList()), 
								Optional.ofNullable(new LimitOneMonth(exceptionLimitAlarmTime)), 
								Optional.ofNullable(new LimitOneMonth(exceptionLimitErrorTime))), 
						new Time36AgreeAnnual(
								new AttendanceTimeYear(actualTime), 
								new LimitOneYear(0))), 
				new Time36AgreeUpperLimit(
						new AttendanceTimeMonth(applicationTime), 
						new Time36AgreeUpperLimitMonth(
								new AttendanceTimeMonth(0), 
								new LimitOneMonth(0)), 
						new Time36AgreeUpperLimitAverage(
								Collections.emptyList(), 
								new LimitOneMonth(0))));
	}
}
