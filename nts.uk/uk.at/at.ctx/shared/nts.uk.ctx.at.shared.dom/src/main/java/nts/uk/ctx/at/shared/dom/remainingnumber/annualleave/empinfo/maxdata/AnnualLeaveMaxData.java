package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualNumberDay;
import nts.uk.shr.com.context.AppContexts;

@Getter
/**
 * 年休上限データ
 * @author masaaki_jinno
 *
 */
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
	 * 	半日年休上限
	 */
	private Optional<HalfdayAnnualLeaveMax> halfdayAnnualLeaveMax;

	/**
	 * 	時間年休上限
	 */
	private Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax;

	/**
	 * コンストラクタ
	 * @param employeeIdIn　社員ID
	 */
	public AnnualLeaveMaxData() {
		halfdayAnnualLeaveMax = Optional.empty();
		timeAnnualLeaveMax = Optional.empty();
	}

	/**
	 * コンストラクタ
	 * [C-1] 年休付与時に作成する
	 * @param employeeIdIn 社員ID
	 * @param halfdayAnnualLeaveMaxIn 上限回数
	 * @param timeAnnualLeaveMax 上限時間
	 */
	public AnnualLeaveMaxData(
			String employeeIdIn,
			Optional<HalfdayAnnualLeaveMax> halfdayAnnualLeaveMaxIn,
			Optional<TimeAnnualLeaveMax> timeAnnualLeaveMaxIn) {
		
		// 社員ID
		employeeId = employeeIdIn;

		// if	上限回数isPresent()
		if ( halfdayAnnualLeaveMaxIn.isPresent() ) {
			halfdayAnnualLeaveMax = Optional.of(
					new HalfdayAnnualLeaveMax(halfdayAnnualLeaveMaxIn.get().getMaxTimes()) );
		} else {
			halfdayAnnualLeaveMax = Optional.empty();
		}

		// if	上限時間isPresent()
		if ( timeAnnualLeaveMaxIn.isPresent() ) {
			timeAnnualLeaveMax 
				= Optional.of(new TimeAnnualLeaveMax(
					timeAnnualLeaveMaxIn.get().getMaxMinutes(), 
					timeAnnualLeaveMaxIn.get().getUsedMinutes()));
		} else {
			timeAnnualLeaveMax = Optional.empty();
		}
	}

	/**
	 * 消化する
	 * @param tempAnnualLeaveMngs 暫定データ
	 * @return 年休上限データ
	 */
	public AnnualLeaveMaxData digest(TempAnnualLeaveMngs tempAnnualLeaveMngs) {

		// ＄半日年休上限
		Optional<HalfdayAnnualLeaveMax> valHalfdayAnnualLeaveMax = Optional.empty();

		// if	＠半日年休上限isPresent()
		if ( halfdayAnnualLeaveMax.isPresent() ) {
			// ＄半日年休上限 =	 ＠半日年休上限.[1]使用回数と残回数を更新(暫定データ)
			valHalfdayAnnualLeaveMax = Optional.of(halfdayAnnualLeaveMax.get().updateUsedTimesRemainingTimes(tempAnnualLeaveMngs));
		}

		// $時間年休上限
		Optional<TimeAnnualLeaveMax> valTimeAnnualLeaveMax = Optional.empty();

		// if	＠時間年休上限isPresent()
		if ( timeAnnualLeaveMax.isPresent() ) {
			// $時間年休上限 =	＠時間年休上限.[1]使用時間と残時間を更新(暫定データ)
			valTimeAnnualLeaveMax = Optional.of(timeAnnualLeaveMax.get().updateUsedTimesRemainingTimes(tempAnnualLeaveMngs));
		}

		return new AnnualLeaveMaxData(
				tempAnnualLeaveMngs.getSID(),
				valHalfdayAnnualLeaveMax, valTimeAnnualLeaveMax);

	}


	public static AnnualLeaveMaxData createFromJavaType(String employeeId, Integer maxTimes, Integer usedTimes,
			Integer maxMinutes, Integer usedMinutes) {

		AnnualLeaveMaxData domain = new AnnualLeaveMaxData();
		domain.employeeId = employeeId;

		// 半日年休上限
		if (maxTimes == null) {
			maxTimes = 0;
		}
		if (usedTimes == null) {
			usedTimes = 0;
		}
		AnnualNumberDay maxTimesObject = new AnnualNumberDay(maxTimes);
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
		LeaveUsedTime usedMinutesObject = new LeaveUsedTime(usedMinutes);
		LeaveRemainingTime remainMinutesObject = new LeaveRemainingTime(maxMinutes - usedMinutes);
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
				this.halfdayAnnualLeaveMax.get().updateMaxTimes(new AnnualNumberDay(maxTimes));
			} else if (usedTimes != null && maxTimes == null) {
				this.halfdayAnnualLeaveMax.get().updateUsedTimes(new UsedTimes(usedTimes));
			} else {
				this.halfdayAnnualLeaveMax.get().update(new AnnualNumberDay(maxTimes), new UsedTimes(usedTimes));
			}
		}

		if (this.timeAnnualLeaveMax.isPresent()) {
			if (maxMinutes != null && usedMinutes == null) {
				this.timeAnnualLeaveMax = Optional.of(new TimeAnnualLeaveMax(new MaxMinutes(maxMinutes)));
			} else if (usedMinutes != null && maxMinutes == null) {
				this.timeAnnualLeaveMax = Optional.of(
						new TimeAnnualLeaveMax(new MaxMinutes(0), new LeaveUsedTime(usedMinutes)));
			} else {
				this.timeAnnualLeaveMax= Optional.of(
						new TimeAnnualLeaveMax(new MaxMinutes(maxMinutes), new LeaveUsedTime(usedMinutes)));
			}
		}
	}

	private static Integer toInteger(BigDecimal bigNumber) {
		return bigNumber != null ? bigNumber.intValue() : new Integer(0);
	}

}
