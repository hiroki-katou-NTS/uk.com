package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveMaxRemainingTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingDetail;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedTime;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnualLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUndigestNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;

/**
 * 年休情報残数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveRemaining implements Cloneable {

	/** 年休（マイナスなし） */
	private AnnualLeave annualLeaveNoMinus;
	/** 年休（マイナスあり） */
	private AnnualLeave annualLeaveWithMinus;
	/** 半日年休（マイナスなし） */
	private Optional<HalfDayAnnualLeave> halfDayAnnualLeaveNoMinus;
	/** 半日年休（マイナスあり） */
	private Optional<HalfDayAnnualLeave> halfDayAnnualLeaveWithMinus;
	/** 時間年休（マイナスなし） */
	private Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeaveNoMinus;
	/** 時間年休（マイナスあり） */
	private Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeaveWithMinus;
	/** 年休未消化数 */
	private Optional<AnnualLeaveUndigestNumber> annualLeaveUndigestNumber;

	/**
	 * コンストラクタ
	 */
	public AnnualLeaveRemaining(){
		
		this.annualLeaveNoMinus = new AnnualLeave();
		this.annualLeaveWithMinus = new AnnualLeave();
		this.halfDayAnnualLeaveNoMinus = Optional.empty();
		this.halfDayAnnualLeaveWithMinus = Optional.empty();
		this.timeAnnualLeaveNoMinus = Optional.empty();
		this.timeAnnualLeaveWithMinus = Optional.empty();
		this.annualLeaveUndigestNumber = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param annualLeaveNoMinus 年休（マイナスなし）
	 * @param annualLeaveWithMinus 年休（マイナスあり）
	 * @param halfDayAnnualLeaveNoMinus 半日年休（マイナスなし）
	 * @param halfDayAnnualLeaveWithMinus 半日年休（マイナスあり）
	 * @param timeAnnualLeaveNoMinus 時間年休（マイナスなし）
	 * @param timeAnnualLeaveWithMinus 時間年休（マイナスあり）
	 * @param annualLeaveUndigestNumber 年休未消化数
	 * @return 年休情報残数
	 */
	public static AnnualLeaveRemaining of(
			AnnualLeave annualLeaveNoMinus,
			Optional<HalfDayAnnualLeave> halfDayAnnualLeaveNoMinus,
			Optional<HalfDayAnnualLeave> halfDayAnnualLeaveWithMinus,
			Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeaveNoMinus,
			Optional<AnnualLeaveMaxRemainingTime> timeAnnualLeaveWithMinus,
			Optional<AnnualLeaveUndigestNumber> annualLeaveUndigestNumber){
		
		AnnualLeaveRemaining domain = new AnnualLeaveRemaining();
		domain.annualLeaveNoMinus = annualLeaveNoMinus;
		domain.halfDayAnnualLeaveNoMinus = halfDayAnnualLeaveNoMinus;
		domain.halfDayAnnualLeaveWithMinus = halfDayAnnualLeaveWithMinus;
		domain.timeAnnualLeaveNoMinus = timeAnnualLeaveNoMinus;
		domain.timeAnnualLeaveWithMinus = timeAnnualLeaveWithMinus;
		domain.annualLeaveUndigestNumber = annualLeaveUndigestNumber;
		return domain;
	}
	
	@Override
	public AnnualLeaveRemaining clone() {
		AnnualLeaveRemaining cloned = new AnnualLeaveRemaining();
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
			if (this.annualLeaveUndigestNumber.isPresent()){
				cloned.annualLeaveUndigestNumber = Optional.of(this.annualLeaveUndigestNumber.get().clone());
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
		this.annualLeaveNoMinus = updateRemainingNumberNoMinus(this.annualLeaveWithMinus);
		
	}
	
	/**
	 * 特別休暇付与残数データから特別休暇の特別休暇残数を作成
	 * @param annualLeaveWithMinus 特休（マイナスあり）
	 */
	private AnnualLeave updateRemainingNumberNoMinus(AnnualLeave annualLeaveWithMinus){
		
		// 特休（マイナスなし）を特休（マイナスあり）で上書き
		AnnualLeave annualLeaveNoMinus = annualLeaveWithMinus.clone();
		
		// 特休からマイナスを削除
		// 「特別休暇．残数」「特別休暇．残数付与前」「特別休暇．残数付与後」をそれぞれ処理
		
		// 残数
		updateRemainingNumberWithMinusToNoMinus(
				annualLeaveNoMinus.getRemainingNumberInfo().getRemainingNumber(),
				annualLeaveNoMinus.getUsedNumberInfo().getUsedNumber());
		
		// 残数付与前
		updateRemainingNumberWithMinusToNoMinus(
				annualLeaveNoMinus.getRemainingNumberInfo().getRemainingNumberBeforeGrant(),
				annualLeaveNoMinus.getUsedNumberInfo().getUsedNumberBeforeGrant());
		
		// 残数付与後
		if ( annualLeaveNoMinus.getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
				&& annualLeaveNoMinus.getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent() ){
			updateRemainingNumberWithMinusToNoMinus(
				annualLeaveNoMinus.getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get(),
				annualLeaveNoMinus.getUsedNumberInfo().getUsedNumberAfterGrantOpt().get());
		}
		
		return annualLeaveNoMinus;
	}
	
	/**
	 * 特休（マイナスあり）を特休（マイナスなし）に変換
	 * @param annualLeaveRemainingNumber　特別休暇残数
	 * @param AnnualLeaveUseNumber　特別休暇使用数
	 */
	private void updateRemainingNumberWithMinusToNoMinus(
			AnnualLeaveRemainingNumber annualLeaveRemainingNumber,
			AnnualLeaveUsedNumber annualLeaveUsedNumber){
			
		// パラメータ「特別休暇残数．合計残日数」と「特別休暇残数．合計残時間」をチェック
		
		// 合計残日数<0　or 合計残時間 < 0
		double remainDays = annualLeaveUsedNumber.getUsedDays().getUsedDayNumber().v();
		int remainTimes = 0;
		if ( annualLeaveRemainingNumber.getTotalRemainingTime().isPresent() ){
			remainTimes = annualLeaveRemainingNumber.getTotalRemainingTime().get().v();
		}
		
		if ( remainDays < 0 || remainTimes < 0 ){
			
			// 特別休暇．使用数からマイナス分を引く
			if ( remainDays < 0 ){
				double useDays = annualLeaveUsedNumber.getUsedDays().getUsedDayNumber().v() + remainDays;
				annualLeaveUsedNumber.getUsedDays().setUsedDayNumber(new AnnualLeaveUsedDayNumber(useDays));
			}
			if ( remainTimes < 0 ){
				if ( annualLeaveUsedNumber.getUsedTime().isPresent() ){
					int useTimes = annualLeaveUsedNumber.getUsedTime().get().getUsedTime().v() + remainTimes;
					annualLeaveUsedNumber.setUsedTime(Optional.of(AnnualLeaveUsedTime.of(new UsedMinutes(useTimes))));
				}
			}
			
			// 特別休暇残数．明細を取得
			List<AnnualLeaveRemainingDetail> detailList
				= annualLeaveRemainingNumber.getDetails();
			
			// 取得した特別休暇残明細でループ
			detailList.forEach(c->{
				// 特別休暇残明細．日数←0
				c.setDays(new AnnualLeaveRemainingDayNumber(0.0));
				// 特別休暇残明細.時間 ← 0
				c.setTime(Optional.of(new AnnualLeaveRemainingTime(0)));
			});
			
			// 特別休暇．残数．合計残日数←0
			annualLeaveRemainingNumber.setTotalRemainingDays(new AnnualLeaveRemainingDayNumber(0.0));
			
			// 特別休暇．残数．合計残時間←0
			annualLeaveRemainingNumber.setTotalRemainingTime(Optional.of(new AnnualLeaveRemainingTime(0)));
		}
	}

}
