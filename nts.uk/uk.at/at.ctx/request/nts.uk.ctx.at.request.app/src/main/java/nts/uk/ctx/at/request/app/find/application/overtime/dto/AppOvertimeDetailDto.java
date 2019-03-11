package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;

/**
 * 時間外時間の詳細
 */
@Data
@AllArgsConstructor
public class AppOvertimeDetailDto {
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

	public static AppOvertimeDetailDto fromDomain(Optional<AppOvertimeDetail> domainOtp) {
		if (!domainOtp.isPresent()) {
			return null;
		}
		AppOvertimeDetail domain = domainOtp.get();
		return new AppOvertimeDetailDto(domain.getTime36Agree().getApplicationTime().v(), domain.getYearMonth().v(),
				domain.getTime36Agree().getAgreeMonth().getActualTime().v(), 
				domain.getTime36Agree().getAgreeMonth().getLimitErrorTime().v(), 
				domain.getTime36Agree().getAgreeMonth().getLimitAlarmTime().v(),
				domain.getTime36Agree().getAgreeMonth().getExceptionLimitErrorTime().isPresent() ? domain.getTime36Agree().getAgreeMonth().getExceptionLimitErrorTime().get().v() : null,
				domain.getTime36Agree().getAgreeMonth().getExceptionLimitAlarmTime().isPresent() ? domain.getTime36Agree().getAgreeMonth().getExceptionLimitAlarmTime().get().v() : null,
				domain.getTime36Agree().getAgreeMonth().getYear36OverMonth().stream().map(x -> x.v()).collect(Collectors.toList()),
				domain.getTime36Agree().getAgreeMonth().getNumOfYear36Over().v(),
				domain.getTime36Agree().getAgreeAnnual().getActualTime().v(),
				domain.getTime36Agree().getAgreeAnnual().getLimitTime().v(),
				domain.getTime36AgreeUpperLimit().getApplicationTime().v(),
				domain.getTime36AgreeUpperLimit().getAgreeUpperLimitMonth().getOverTime().v(),
				domain.getTime36AgreeUpperLimit().getAgreeUpperLimitMonth().getUpperLimitTime().v(),
				domain.getTime36AgreeUpperLimit().getAgreeUpperLimitAverage().getAverageTimeLst()
					.stream().map(x -> new Time36UpLimitMonthDto(x.getPeriod().start().v(), x.getPeriod().end().v(), x.getAverageTime().v(), x.getTotalTime().v()))
					.collect(Collectors.toList()),
				domain.getTime36AgreeUpperLimit().getAgreeUpperLimitAverage().getUpperLimitTime().v());
	}
}
