package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.SpecialLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber.SpecialLeaveUndigestNumber;

/**
 * 特休情報残数
 * @author shuichu_ishida
 */
@Getter
public class SpecialLeaveRemainingNumber implements Cloneable {

	/** 特休（マイナスなし） */
	private SpecialLeave specialLeaveNoMinus;
	/** 特休（マイナスあり） */
	private SpecialLeave specialLeaveWithMinus;
	/** 特休未消化数 */
	private Optional<SpecialLeaveUndigestNumber> specialLeaveUndigestNumber;

	/**
	 * コンストラクタ
	 */
	public SpecialLeaveRemainingNumber(){
		
		this.specialLeaveNoMinus = new SpecialLeave();
		this.specialLeaveWithMinus = new SpecialLeave();
		this.specialLeaveUndigestNumber = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param specialLeaveNoMinus 特休（マイナスなし）
	 * @param specialLeaveWithMinus 特休（マイナスあり）
	 * @param specialLeaveUndigestNumber 特休未消化数
	 * @return 特休情報残数
	 */
	public static SpecialLeaveRemainingNumber of(
			SpecialLeave specialLeaveNoMinus,
			SpecialLeave specialLeaveWithMinus,
			Optional<SpecialLeaveUndigestNumber> specialLeaveUndigestNumber){
		
		SpecialLeaveRemainingNumber domain = new SpecialLeaveRemainingNumber();
		domain.specialLeaveNoMinus = specialLeaveNoMinus;
		domain.specialLeaveWithMinus = specialLeaveWithMinus;
		domain.specialLeaveUndigestNumber = specialLeaveUndigestNumber;
		return domain;
	}
	
	@Override
	public SpecialLeaveRemainingNumber clone() {
		SpecialLeaveRemainingNumber cloned = new SpecialLeaveRemainingNumber();
		try {
			cloned.specialLeaveNoMinus = this.specialLeaveNoMinus.clone();
			cloned.specialLeaveWithMinus = this.specialLeaveWithMinus.clone();
//			if (this.halfDaySpecialLeaveNoMinus.isPresent()){
//				cloned.halfDaySpecialLeaveNoMinus = Optional.of(this.halfDaySpecialLeaveNoMinus.get().clone());
//			}
//			if (this.halfDaySpecialLeaveWithMinus.isPresent()){
//				cloned.halfDaySpecialLeaveWithMinus = Optional.of(this.halfDaySpecialLeaveWithMinus.get().clone());
//			}
//			if (this.timeSpecialLeaveNoMinus.isPresent()){
//				cloned.timeSpecialLeaveNoMinus = Optional.of(this.timeSpecialLeaveNoMinus.get().clone());
//			}
//			if (this.timeSpecialLeaveWithMinus.isPresent()){
//				cloned.timeSpecialLeaveWithMinus = Optional.of(this.timeSpecialLeaveWithMinus.get().clone());
//			}
			if (this.specialLeaveUndigestNumber.isPresent()){
				cloned.specialLeaveUndigestNumber = Optional.of(this.specialLeaveUndigestNumber.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeaveRemainingNumber clone error.");
		}
		return cloned;
	}
	
	/**
	 * 特休付与情報を更新
	 * @param remainingDataList 特休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void updateRemainingNumber(
			List<SpecialLeaveGrantRemaining> remainingDataList, boolean afterGrantAtr){
		
		// 特休付与残数データから特休（マイナスあり）を作成
		this.specialLeaveWithMinus.createRemainingNumberFromGrantRemaining(remainingDataList, afterGrantAtr);
		
		// 特休（マイナスなし）を特休（マイナスあり）で上書き　＆　特休からマイナスを削除
		//this.specialLeaveNoMinus.setValueFromSpecialLeave(this.specialLeaveWithMinus);
		this.specialLeaveNoMinus = this.specialLeaveWithMinus.clone();
	}
}
