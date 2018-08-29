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

	public static AppOvertimeDetailDto fromDomain(Optional<AppOvertimeDetail> domainOtp) {
		if (!domainOtp.isPresent()) {
			return null;
		}
		AppOvertimeDetail domain = domainOtp.get();
		return new AppOvertimeDetailDto(domain.getApplicationTime().v(), domain.getYearMonth().v(),
				domain.getActualTime().v(), domain.getLimitErrorTime().v(), domain.getLimitAlarmTime().v(),
				domain.getExceptionLimitErrorTime().isPresent() ? domain.getExceptionLimitErrorTime().get().v() : null,
				domain.getExceptionLimitAlarmTime().isPresent() ? domain.getExceptionLimitAlarmTime().get().v() : null,
				domain.getYear36OverMonth().stream().map(x -> x.getOverMonth().v()).collect(Collectors.toList()),
				domain.getNumOfYear36Over().v());
	}
}
