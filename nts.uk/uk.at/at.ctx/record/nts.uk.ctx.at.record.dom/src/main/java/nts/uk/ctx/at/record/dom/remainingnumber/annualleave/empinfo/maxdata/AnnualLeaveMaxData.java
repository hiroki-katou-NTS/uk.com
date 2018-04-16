package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.context.AppContexts;

@Getter
// domain name: 年休上限データ
public class AnnualLeaveMaxData extends AggregateRoot {

	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 半日年休上限
	 */
	private Optional<HalfdayAnnualLeaveMax> halfdayAnnualLeaveMax;

	/**
	 * 時間年休上限
	 */
	private Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax;

	public static AnnualLeaveMaxData createFromJavaType(String employeeId, Integer maxTimes, Integer usedTimes,
			Integer maxMinutes, Integer usedMinutes) {
		AnnualLeaveMaxData domain = new AnnualLeaveMaxData();
		domain.employeeId = employeeId;
		domain.companyId = AppContexts.user().companyId();

		// 半日年休上限
		if (maxTimes == null) {
			maxTimes = 0;
		}
		if (usedTimes == null) {
			usedTimes = 0;
		}
		MaxTimes maxTimesObject = new MaxTimes(maxTimes);
		UsedTimes usedTimesObject = new UsedTimes(usedTimes);
		RemainingTimes remainingTimesObject = new RemainingTimes(maxTimes - usedTimes);
		domain.halfdayAnnualLeaveMax = Optional
				.of(new HalfdayAnnualLeaveMax(maxTimesObject, usedTimesObject, remainingTimesObject));

		// 時間年休上限
		if (maxMinutes == null) {
			maxMinutes = 0;
		}
		if (usedMinutes == null) {
			usedMinutes = 0;
		}
		MaxMinutes maxMinutesObject = new MaxMinutes(maxMinutes);
		UsedMinutes usedMinutesObject = new UsedMinutes(usedMinutes);
		RemainingMinutes remainMinutesObject = new RemainingMinutes(maxMinutes - usedMinutes);
		domain.timeAnnualLeaveMax = Optional
				.of(new TimeAnnualLeaveMax(maxMinutesObject, usedMinutesObject, remainMinutesObject));
		return domain;
	}
	
	public static AnnualLeaveMaxData createFromJavaType(String employeeId, BigDecimal maxTimes, BigDecimal usedTimes,
			BigDecimal maxMinutes, BigDecimal usedMinutes) {
		return createFromJavaType(employeeId, toInteger(maxTimes), toInteger(usedTimes), toInteger(maxMinutes),
				toInteger(usedMinutes));
	}

	private static Integer toInteger(BigDecimal bigNumber) {
		return bigNumber != null ? bigNumber.intValue() : null;
	}

}
