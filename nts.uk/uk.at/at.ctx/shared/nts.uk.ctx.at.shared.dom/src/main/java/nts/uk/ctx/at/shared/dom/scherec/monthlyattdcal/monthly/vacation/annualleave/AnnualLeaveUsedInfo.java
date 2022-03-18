package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseNumber;

/**
 * 年休使用情報
 * @author masaaki_jinno
 *　
 */
@Getter
@Setter
@AllArgsConstructor
public class AnnualLeaveUsedInfo implements Cloneable, SerializableWithOptional {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** 合計 */
	private AnnualLeaveUsedNumber usedNumber;

	/** 付与前 */
	private AnnualLeaveUsedNumber usedNumberBeforeGrant;

	/** 時間年休使用回数 （1日2回使用した場合２回でカウント）*/
	private UsedTimes annualLeaveUsedTimes;

	/** 時間年休使用日数 （1日2回使用した場合１回でカウント） */
	private UsedTimes annualLeaveUsedDayTimes;

	/** 付与後 */
	private Optional<AnnualLeaveUsedNumber> usedNumberAfterGrantOpt;

	/**
	 * ファクトリ
	 * @param usedNumber 合計
	 * @param usedNumberBeforeGrant 付与前
	 * @param annualLeaveUsedTimes 時間年休使用回数
	 * @param annualLeaveUsedDayTimes 時間年休使用日数
	 * @param usedNumberAfterGrant 付与後
	 * @return
	 */
	public static AnnualLeaveUsedInfo of(
			AnnualLeaveUsedNumber usedNumber,
			AnnualLeaveUsedNumber usedNumberBeforeGrant,
			UsedTimes annualLeaveUsedTimes,
			UsedTimes annualLeaveUsedDayTimes,
			Optional<AnnualLeaveUsedNumber> usedNumberAfterGrantOpt
			){

		AnnualLeaveUsedInfo domain = new AnnualLeaveUsedInfo();
		domain.usedNumber = usedNumber;
		domain.usedNumberBeforeGrant = usedNumberBeforeGrant;
		domain.annualLeaveUsedTimes = annualLeaveUsedTimes;
		domain.annualLeaveUsedDayTimes = annualLeaveUsedDayTimes;
		domain.usedNumberAfterGrantOpt = usedNumberAfterGrantOpt;
		return domain;
	}

	/** コンストラクタ */
	public AnnualLeaveUsedInfo(){
		usedNumber = new AnnualLeaveUsedNumber();
		usedNumberBeforeGrant = new AnnualLeaveUsedNumber();
		annualLeaveUsedTimes = new UsedTimes(0);
		annualLeaveUsedDayTimes = new UsedTimes(0);
		usedNumberAfterGrantOpt = Optional.empty();
	}

	/** 合計をセット */
	public void setTotal() {
		usedNumber = new AnnualLeaveUsedNumber();
		usedNumber.addUsedNumber(usedNumberBeforeGrant);
		if (usedNumberAfterGrantOpt.isPresent()) {
			usedNumber.addUsedNumber(usedNumberAfterGrantOpt.get());
		}
	}

	/**
	 * クローン
	 */
	public AnnualLeaveUsedInfo clone() {
		AnnualLeaveUsedInfo cloned = new AnnualLeaveUsedInfo();
		try {
			if ( usedNumberBeforeGrant != null ){
				cloned.usedNumberBeforeGrant = this.usedNumberBeforeGrant.clone();
			}
			if ( usedNumber != null ){
				cloned.usedNumber = this.usedNumber.clone();
			}

			/** 時間年休使用回数 */
			cloned.annualLeaveUsedTimes = new UsedTimes(this.annualLeaveUsedTimes.v());

			/** 時間年休使用日数 */
			cloned.annualLeaveUsedDayTimes = new UsedTimes(this.annualLeaveUsedDayTimes.v());

			if (this.usedNumberAfterGrantOpt.isPresent()){
				cloned.usedNumberAfterGrantOpt = Optional.of(this.usedNumberAfterGrantOpt.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveUsedInfo clone error.");
		}
		return cloned;
	}

	/**
	 * 使用数を加算する
	 * @param usedNumber 使用数
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void addUsedNumber(AnnualLeaveUsedNumber usedNumber, GrantBeforeAfterAtr grantPeriodAtr){

		// 「付与後フラグ」をチェック
		if (grantPeriodAtr.equals(GrantBeforeAfterAtr.AFTER_GRANT)){

			// 使用日数付与後に加算
			if ( this.usedNumberAfterGrantOpt.isPresent() ){
				this.usedNumberAfterGrantOpt.get().addUsedNumber(usedNumber);
			} else {
				this.usedNumberAfterGrantOpt = Optional.of(usedNumber.clone());
			}
		}
		else {

			// 使用日数付与前に加算
			this.usedNumberBeforeGrant.addUsedNumber(usedNumber);

		}

		//合計使用数を求める
		this.setTotal();
	}

	/**
	 * 付与前退避処理
	 */
	public void saveStateBeforeGrant(){
		// 合計残数を付与前に退避する
		usedNumberBeforeGrant = usedNumber.clone();
	}

	/**
	 * 付与後退避処理
	 */
	public void saveStateAfterGrant(){
		// 合計残数を付与後に退避する
		usedNumberAfterGrantOpt = Optional.of(usedNumber.clone());
	}

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}

}
