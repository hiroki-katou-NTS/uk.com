package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;

/**
 * 特休残明細
 */
@Getter
@Setter
public class SpecialLeaveRemainingDetail implements Cloneable {

	/** 付与日 */
	private GeneralDate grantDate;

	/** 日数 */
	private DayNumberOfRemain days;
	/** 時間 */
	private Optional<TimeOfRemain> time;

	/**
	 * コンストラクタ
	 * @param grantDate 付与日
	 */
	public SpecialLeaveRemainingDetail(GeneralDate grantDate){

		this.grantDate = grantDate;

		this.days = new DayNumberOfRemain(0.0);
		this.time = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param grantDate 付与日
	 * @param days 日数
	 * @param time 時間
	 * @return 年休残明細
	 */
	public static SpecialLeaveRemainingDetail of(
			GeneralDate grantDate,
			DayNumberOfRemain days,
			Optional<TimeOfRemain> time){

		SpecialLeaveRemainingDetail domain = new SpecialLeaveRemainingDetail(grantDate);
		domain.days = days;
		domain.time = time;
		return domain;
	}

	@Override
	protected SpecialLeaveRemainingDetail clone() {
		SpecialLeaveRemainingDetail cloned = new SpecialLeaveRemainingDetail(this.grantDate);
		try {
			cloned = (SpecialLeaveRemainingDetail)super.clone();
			cloned.days = new DayNumberOfRemain(this.days.v());
			if (this.time.isPresent()){
				cloned.time = Optional.of(new TimeOfRemain(this.time.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeaveRemainingDetail clone error.");
		}
		return cloned;
	}

	public boolean isMinus() {
		return this.getDays().lessThan(0.0) || (this.getTime().isPresent()?this.getTime().get().lessThan(0):false);
	}
}
