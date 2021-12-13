package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

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

/**
 * 特別休暇使用情報
 * @author masaaki_jinno
 *
 */
@Getter
@AllArgsConstructor
public class SpecialLeaveUsedInfo implements Cloneable, SerializableWithOptional {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** 合計 */
	private SpecialLeaveUseNumber usedNumber;

	/** 付与前 */
	@Setter
	private SpecialLeaveUseNumber usedNumberBeforeGrant;

	/** 特休使用回数 （1日2回使用した場合２回でカウント）*/
	@Setter
	private UsedTimes specialLeaveUsedTimes;

	/** 特休使用日数 （1日2回使用した場合１回でカウント） */
	@Setter
	private UsedTimes specialLeaveUsedDayTimes;

	/** 付与後 */
	@Setter
	private Optional<SpecialLeaveUseNumber> usedNumberAfterGrantOpt;

	/** 合計をセット */
	public void setTotal() {
		usedNumber = new SpecialLeaveUseNumber();
		usedNumber.addUsedNumber(usedNumberBeforeGrant);
		if (usedNumberAfterGrantOpt.isPresent()) {
			usedNumber.addUsedNumber(usedNumberAfterGrantOpt.get());
		}
	}

	/**
	 * ファクトリ
	 * @param usedNumber 合計
	 * @param usedNumberBeforeGrant 付与前
	 * @param specialLeaveUsedTimes 時間特休使用回数
	 * @param specialLeaveUsedDayTimes 時間特休使用日数
	 * @param usedNumberAfterGrant 付与後
	 * @return
	 */
	public static SpecialLeaveUsedInfo of(
			SpecialLeaveUseNumber usedNumber,
			SpecialLeaveUseNumber usedNumberBeforeGrant,
			UsedTimes specialLeaveUsedTimes,
			UsedTimes specialLeaveUsedDayTimes,
			Optional<SpecialLeaveUseNumber> usedNumberAfterGrantOpt
			){

		SpecialLeaveUsedInfo domain = new SpecialLeaveUsedInfo();
		domain.usedNumber = usedNumber;
		domain.usedNumberBeforeGrant = usedNumberBeforeGrant;
		domain.specialLeaveUsedTimes = specialLeaveUsedTimes;
		domain.specialLeaveUsedDayTimes = specialLeaveUsedDayTimes;
		domain.usedNumberAfterGrantOpt = usedNumberAfterGrantOpt;
		return domain;
	}

	/** コンストラクタ */
	public SpecialLeaveUsedInfo(){
		usedNumber = new SpecialLeaveUseNumber();
		usedNumberBeforeGrant = new SpecialLeaveUseNumber();
		specialLeaveUsedTimes = new UsedTimes(0);
		specialLeaveUsedDayTimes = new UsedTimes(0);
		usedNumberAfterGrantOpt = Optional.empty();
	}


	/**
	 * クローン
	 */
	public SpecialLeaveUsedInfo clone() {
		SpecialLeaveUsedInfo cloned = new SpecialLeaveUsedInfo();
//		try {
			if ( usedNumberBeforeGrant != null ){
				cloned.usedNumberBeforeGrant = this.usedNumberBeforeGrant.clone();
			}
			if ( usedNumber != null ){
				cloned.usedNumber = this.usedNumber.clone();
			}

			/** 時間特休使用回数 */
			cloned.specialLeaveUsedTimes = new UsedTimes(this.specialLeaveUsedTimes.v());

			/** 時間特休使用日数 */
			cloned.specialLeaveUsedDayTimes = new UsedTimes(this.specialLeaveUsedDayTimes.v());

			if (this.usedNumberAfterGrantOpt.isPresent()){
				cloned.usedNumberAfterGrantOpt = Optional.of(this.usedNumberAfterGrantOpt.get().clone());
			}
//		}
//		catch (Exception e){
//			throw new RuntimeException("SpecialLeaveUsedInfo clone error.");
//		}
		return cloned;
	}

	/**
	 * 使用数を加算する
	 * @param usedNumber 使用数
	 * @param grantPeriodAtr 付与前付与後
	 */
	public void addUsedNumber(SpecialLeaveUseNumber usedNumber, GrantBeforeAfterAtr grantPeriodAtr){

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

		// 合計を求める
		this.setTotal();
	}

	/**
	 * クリア
	 */
	public void clear(){
		usedNumber = new SpecialLeaveUseNumber();
		usedNumberBeforeGrant = new SpecialLeaveUseNumber();
		specialLeaveUsedTimes = new UsedTimes(0);
		specialLeaveUsedDayTimes = new UsedTimes(0);
		usedNumberAfterGrantOpt = Optional.empty();
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
