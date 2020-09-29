package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remain.AnnualLeaveGrantRemaining;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveMaxRemainingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.HalfDayAnnualLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.RealAnnualLeave;

/**
 * 年休情報残数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveRemainingNumber implements Cloneable {

	/** 年休（マイナスなし） */
	private AnnualLeave annualLeaveNoMinus;
	/** 年休（マイナスあり） */
	private RealAnnualLeave annualLeaveWithMinus;
	/** 半日年休（マイナスなし） */
	private Optional<HalfDayAnnualLeave> halfDayAnnualLeaveNoMinus;
	/** 半日年休（マイナスあり） */
	private Optional<HalfDayAnnualLeave> halfDayAnnualLeaveWithMinus;
	/** 時間年休（マイナスなし） */
	private Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeaveNoMinus;
	/** 時間年休（マイナスあり） */
	private Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeaveWithMinus;

	/**
	 * コンストラクタ
	 */
	public AnnualLeaveRemainingNumber(){
		
		this.annualLeaveNoMinus = new AnnualLeave();
		this.annualLeaveWithMinus = new RealAnnualLeave();
		this.halfDayAnnualLeaveNoMinus = Optional.empty();
		this.halfDayAnnualLeaveWithMinus = Optional.empty();
		this.timeAnnualLeaveNoMinus = Optional.empty();
		this.timeAnnualLeaveWithMinus = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param annualLeaveNoMinus 年休（マイナスなし）
	 * @param annualLeaveWithMinus 年休（マイナスあり）
	 * @param halfDayAnnualLeaveNoMinus 半日年休（マイナスなし）
	 * @param halfDayAnnualLeaveWithMinus 半日年休（マイナスあり）
	 * @param timeAnnualLeaveNoMinus 時間年休（マイナスなし）
	 * @param timeAnnualLeaveWithMinus 時間年休（マイナスあり）
	 * @return 年休情報残数
	 */
	public static AnnualLeaveRemainingNumber of(
			AnnualLeave annualLeaveNoMinus,
			RealAnnualLeave annualLeaveWithMinus,
			Optional<HalfDayAnnualLeave> halfDayAnnualLeaveNoMinus,
			Optional<HalfDayAnnualLeave> halfDayAnnualLeaveWithMinus,
			Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeaveNoMinus,
			Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeaveWithMinus){
		
		AnnualLeaveRemainingNumber domain = new AnnualLeaveRemainingNumber();
		domain.annualLeaveNoMinus = annualLeaveNoMinus;
		domain.annualLeaveWithMinus = annualLeaveWithMinus;
		domain.halfDayAnnualLeaveNoMinus = halfDayAnnualLeaveNoMinus;
		domain.halfDayAnnualLeaveWithMinus = halfDayAnnualLeaveWithMinus;
		domain.timeAnnualLeaveNoMinus = timeAnnualLeaveNoMinus;
		domain.timeAnnualLeaveWithMinus = timeAnnualLeaveWithMinus;
		return domain;
	}
	
	@Override
	public AnnualLeaveRemainingNumber clone() {
		AnnualLeaveRemainingNumber cloned = new AnnualLeaveRemainingNumber();
		try {
			cloned.annualLeaveNoMinus = this.annualLeaveNoMinus.clone();
			cloned.annualLeaveWithMinus = this.annualLeaveWithMinus.clone();
			if (this.halfDayAnnualLeaveNoMinus.isPresent()){
				cloned.halfDayAnnualLeaveNoMinus = Optional.of(this.halfDayAnnualLeaveNoMinus.get().clone());
			}
			if (this.halfDayAnnualLeaveWithMinus.isPresent()){
				cloned.halfDayAnnualLeaveWithMinus = Optional.of(this.halfDayAnnualLeaveWithMinus.get().clone());
			}
			if (this.timeAnnualLeaveNoMinus.isPresent()){
				cloned.timeAnnualLeaveNoMinus = Optional.of(this.timeAnnualLeaveNoMinus.get().clone());
			}
			if (this.timeAnnualLeaveWithMinus.isPresent()){
				cloned.timeAnnualLeaveWithMinus = Optional.of(this.timeAnnualLeaveWithMinus.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveRemainingNumber clone error.");
		}
		return cloned;
	}
	
	/**
	 * 年休付与情報を更新
	 * @param remainingDataList 年休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void updateRemainingNumber(
			List<AnnualLeaveGrantRemaining> remainingDataList, boolean afterGrantAtr){
		
		// 年休付与残数データから年休（マイナスあり）を作成
		this.annualLeaveWithMinus.createRemainingNumberFromGrantRemaining(remainingDataList, afterGrantAtr);
		
		// 年休（マイナスなし）を年休（マイナスあり）で上書き　＆　年休からマイナスを削除
		this.annualLeaveNoMinus.setValueFromRealAnnualLeave(this.annualLeaveWithMinus);
	}
}
