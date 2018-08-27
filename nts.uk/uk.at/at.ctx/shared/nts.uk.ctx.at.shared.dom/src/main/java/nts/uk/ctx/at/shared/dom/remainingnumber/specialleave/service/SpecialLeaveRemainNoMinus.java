package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.Optional;

import lombok.Getter;
import lombok.val;

/**
 * 特別休暇の残数マイナスなし
 * @author shuichu_ishida
 */
@Getter
public class SpecialLeaveRemainNoMinus {

	/** 残数付与前 */
	private double remainDaysBeforeGrant;
	/** 使用数付与前 */
	private double useDaysBeforeGrant;
	/** 残数付与後 */
	private Optional<Double> remainDaysAfterGrant;
	/** 使用数付与後 */
	private Optional<Double> useDaysAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public SpecialLeaveRemainNoMinus(){
		this.init();
	}
	
	private void init(){
		this.remainDaysBeforeGrant = 0.0;
		this.useDaysBeforeGrant = 0.0;
		this.remainDaysAfterGrant = Optional.empty();
		this.useDaysAfterGrant = Optional.empty();
	}
	
	/**
	 * マイナスなしの残数・使用数を計算
	 * @param remainDays 特別休暇の残数
	 */
	public SpecialLeaveRemainNoMinus(RemainDaysOfSpecialHoliday remainDays){
		
		this.init();
		
		// マイナス分を削除した残数・使用数を計算（付与前）
		val beforeGrant = remainDays.getGrantDetailBefore();
		if (beforeGrant.getRemainDays() < 0){
			double minusRemain = -beforeGrant.getRemainDays();
			double useDays = 0.0;
			if (minusRemain <= beforeGrant.getUseDays()){
				useDays = beforeGrant.getUseDays() + beforeGrant.getRemainDays();
			}
			this.remainDaysBeforeGrant = 0.0;
			this.useDaysBeforeGrant = useDays;
		}
		else {
			this.remainDaysBeforeGrant = beforeGrant.getRemainDays();
			this.useDaysBeforeGrant = beforeGrant.getUseDays();
		}
		
		// マイナス分を削除した残数・使用数を計算（付与後）
		if (remainDays.getGrantDetailAfter().isPresent()){
			val afterGrant = remainDays.getGrantDetailAfter().get();
			if (afterGrant.getRemainDays() < 0){
				double minusRemain = -afterGrant.getRemainDays();
				double useDays = 0.0;
				if (minusRemain <= afterGrant.getUseDays()){
					useDays = afterGrant.getUseDays() + afterGrant.getRemainDays();
				}
				this.remainDaysAfterGrant = Optional.of(0.0);
				this.useDaysAfterGrant = Optional.of(useDays);
			}
			else {
				this.remainDaysAfterGrant = Optional.of(afterGrant.getRemainDays());
				this.useDaysAfterGrant = Optional.of(afterGrant.getUseDays());
			}
		}
	}
}
