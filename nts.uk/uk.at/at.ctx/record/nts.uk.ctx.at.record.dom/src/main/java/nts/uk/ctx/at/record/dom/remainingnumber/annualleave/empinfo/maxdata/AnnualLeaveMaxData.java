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

	/**46
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

	public void updateData(BigDecimal maxTimesBig, BigDecimal usedTimesBig, BigDecimal maxMinutesBig,
			BigDecimal usedMinutesBig) {
		Integer maxTimes = toInteger(maxTimesBig);
		Integer usedTimes = toInteger(usedTimesBig);
		Integer maxMinutes = toInteger(maxMinutesBig);
		Integer usedMinutes = toInteger(usedMinutesBig);

		if (this.halfdayAnnualLeaveMax.isPresent()) {
			if (maxTimes != null && usedTimes == null) {
				this.halfdayAnnualLeaveMax.get().updateMaxTimes(new MaxTimes(maxTimes));
			} else if (usedTimes != null && maxTimes == null) {
				this.halfdayAnnualLeaveMax.get().updateUsedTimes(new UsedTimes(usedTimes));
			} else {
				this.halfdayAnnualLeaveMax.get().update(new MaxTimes(maxTimes), new UsedTimes(usedTimes));
			}
		}

		if (this.timeAnnualLeaveMax.isPresent()) {
			if (maxMinutes != null && usedMinutes == null) {
				this.timeAnnualLeaveMax.get().updateMaxMinutes(new MaxMinutes(maxMinutes));
			} else if (usedMinutes != null && maxMinutes == null) {
				this.timeAnnualLeaveMax.get().updateUsedMinutes(new UsedMinutes(usedMinutes));
			} else {
				this.timeAnnualLeaveMax.get().update(new MaxMinutes(maxMinutes), new UsedMinutes(usedMinutes));
			}
		}

	}

	private static Integer toInteger(BigDecimal bigNumber) {
		return bigNumber != null ? bigNumber.intValue() : new Integer(0);
	}

}
