package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;

/**
 * 年休使用数
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveUsedNumber implements Cloneable, SerializableWithOptional {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** 使用日数 */
	private Optional<AnnualLeaveUsedDayNumber> usedDays;

	/** 使用時間 */
	private Optional<UsedMinutes> usedTime;

	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUsedNumber(){

		this.usedDays = Optional.empty();
		this.usedTime = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDays 使用日数
	 * @param usedTime 使用時間
	 * @return 年休使用数
	 */
	public static AnnualLeaveUsedNumber of(
			Optional<AnnualLeaveUsedDayNumber> usedDays,
			Optional<UsedMinutes> usedTime){

		AnnualLeaveUsedNumber domain = new AnnualLeaveUsedNumber();
		domain.usedDays = usedDays;
		domain.usedTime = usedTime;
		return domain;
	}

	@Override
	public AnnualLeaveUsedNumber clone() {
		AnnualLeaveUsedNumber cloned = new AnnualLeaveUsedNumber();
		try {
			cloned.usedDays = this.usedDays.map(c -> new AnnualLeaveUsedDayNumber(c.v()));
			cloned.usedTime = this.usedTime.map(c -> new UsedMinutes(c.v()));
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveUsedNumber clone error.");
		}
		return cloned;
	}

	/**
	 * 日数を使用日数に加算する
	 */
	public void addUsedNumber(AnnualLeaveUsedNumber usedNumber) {
		addDays(usedNumber.getUsedDays().map(x -> x.v()).orElse(0d));
		addTime(usedNumber.getUsedTime().map(x -> x.v()).orElse(0));
	}

	public void addDays(double days) {
		if ( !this.usedDays.isPresent() ) {
			this.usedDays = Optional.of(new AnnualLeaveUsedDayNumber(0.0));
		}

		this.usedDays = this.usedDays.map(c -> new AnnualLeaveUsedDayNumber(c.v() + days));
	}

	public void addTime(int time) {
		if ( !this.usedTime.isPresent() ) {
			this.usedTime = Optional.of(new UsedMinutes(0));
		}

		this.usedTime = this.usedTime.map(c -> new UsedMinutes(c.v() + time));
	}

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}

}

