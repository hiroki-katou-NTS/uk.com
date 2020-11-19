package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.Time36UpLimitMonthDto;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.NumberOfMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeAnnual;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimit;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitAverage;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitPerMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;

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
	
	/*
	 * 実績時間
	 */
	public Integer actualTimeAnnual;
	
	/*
	 * 限度時間
	 */
	public Integer limitTime;
	
	/*
	 * 申請時間
	 */
	public Integer appTimeAgreeUpperLimit;
	
	/*
	 * 時間外時間
	 */
	public Integer overTime;
	
	/*
	 * 上限時間
	 */
	public Integer upperLimitTimeMonth;
	
	/*
	 * 平均時間
	 */
	public List<Time36UpLimitMonthDto> averageTimeLst;
	
	/*
	 * 上限時間
	 */
	public Integer upperLimitTimeAverage;

	public AppOvertimeDetail toDomain(String cid, String appId) {
		return new AppOvertimeDetail(
				cid, 
				appId, 
				new YearMonth(yearMonth), 
				new Time36Agree(
						new AttendanceTimeMonth(applicationTime) , 
						new Time36AgreeMonth(
								new AttendanceTimeMonth(actualTime), 
								new AgreementOneMonthTime(limitAlarmTime), 
								new AgreementOneMonthTime(limitErrorTime), 
								new NumberOfMonth(numOfYear36Over), 
								year36OverMonth.stream().map(x -> new YearMonth(x)).collect(Collectors.toList()), 
								exceptionLimitAlarmTime == null ? Optional.empty() : Optional.of(new AgreementOneMonthTime(exceptionLimitAlarmTime)), 
								exceptionLimitErrorTime == null ? Optional.empty() : Optional.of(new AgreementOneMonthTime(exceptionLimitErrorTime))), 
						new Time36AgreeAnnual(
								new AttendanceTimeYear(actualTimeAnnual), 
								new AgreementOneYearTime(limitTime))), 
				new Time36AgreeUpperLimit(
						new AttendanceTimeMonth(appTimeAgreeUpperLimit), 
						new Time36AgreeUpperLimitMonth(
								new AttendanceTimeMonth(overTime), 
								new AgreementOneMonthTime(upperLimitTimeMonth)), 
						new Time36AgreeUpperLimitAverage(
								averageTimeLst.stream().map(x -> new Time36AgreeUpperLimitPerMonth(x.periodYearStart, x.periodYearEnd, x.averageTime, x.totalTime)).collect(Collectors.toList()), 
								new AgreementOneMonthTime(upperLimitTimeAverage))));
	}
}
