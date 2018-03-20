package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
// domain name: 年休上限データ
public class AnnualLeaveMaxData extends AggregateRoot {

	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 半日年休上限
	 */
	private Optional<HalfdayAnnualLeaveMax> halfdayAnnualLeaveMax;

	/**
	 * 時間年休上限
	 */
	private Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax;

	public static AnnualLeaveMaxData createFromJavaType(String employeeId, Integer maxTimes, Integer usedTimes,
			Integer remainingTimes, Integer maxMinutes, Integer usedMinutes, Integer remainingMinutes) {
		AnnualLeaveMaxData domain = new AnnualLeaveMaxData();
		domain.employeeId = employeeId;
		if (maxTimes != null && usedTimes != null && remainingTimes != null) {
			domain.halfdayAnnualLeaveMax = Optional.of(new HalfdayAnnualLeaveMax(new MaxTimes(maxTimes),
					new UsedTimes(usedTimes), new RemainingTimes(remainingTimes)));
		} else {
			domain.halfdayAnnualLeaveMax = Optional.empty();
		}
		if (maxMinutes != null && usedMinutes != null && remainingMinutes != null) {
			domain.timeAnnualLeaveMax = Optional.of(new TimeAnnualLeaveMax(new MaxMinutes(maxMinutes),
					new UsedMinutes(usedMinutes), new RemainingMinutes(remainingMinutes)));
		} else {
			domain.timeAnnualLeaveMax = Optional.empty();
		}
		return domain;
	}

}
