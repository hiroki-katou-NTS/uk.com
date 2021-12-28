/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.ProcAtrAddMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 労働時間の加算設定
 */
@NoArgsConstructor
@Getter
public class AddSettingOfWorkingTime extends AggregateRoot implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 割増計算方法を設定する */
	private NotUseAtr useAtr = NotUseAtr.NOT_USE;
	/** 割増時間の加算設定 */
	private AddSettingOfPremiumTime addSetOfPremium = new AddSettingOfPremiumTime();
	/** 就業時間の加算設定 */
	private AddSettingOfWorkTime addSetOfWorkTime = new AddSettingOfWorkTime();

	/**
	 * Constructor
	 * @param addSetOfPremium 割増時間の加算設定
	 * @param addSetOfWorkTime 就業時間の加算設定
	 * @param useAtr 割増計算方法を設定する
	 */
	public AddSettingOfWorkingTime(
			AddSettingOfPremiumTime addSetOfPremium,
			AddSettingOfWorkTime addSetOfWorkTime,
			NotUseAtr useAtr) {
		super();
		this.useAtr = useAtr;
		this.addSetOfPremium = addSetOfPremium;
		this.addSetOfWorkTime = addSetOfWorkTime;
	}
	
	/**
	 * aggregateが取得できなかった場合の仮作成
	 * 就業、割増共に中身が「実働のみで計算する=実働時間のみで計算する　、詳細設定=null」で作成
	 * @return 労働時間の加算設定
	 */
	public static AddSettingOfWorkingTime emptyHolidayCalcMethodSet() {
		return new AddSettingOfWorkingTime();
	}
	
	/**
	 * 休暇加算するかどうか判断
	 * @param premiumAtr 割増区分（通常、割増）
	 * @return USE:加算する,NOT_USE:加算しない
	 */
	public NotUseAtr isAddVacation(PremiumAtr premiumAtr) {
		// 割増区分をチェックする
		if (premiumAtr.isPremium()){
			// 割増
			// 割増計算方法を設定するか確認する
			if (this.useAtr.isUse()){
				// 「実動のみで計算する」時、加算しない
				if (this.addSetOfPremium.getCalculateActualOperation().isCalclationByActualTime()){
					return NotUseAtr.NOT_USE;
				}
				// 「割増時間の加算設定.休暇時間の扱い」を確認する　→　「加算する」を返す　（なければ、加算しない）
				return this.addSetOfPremium.getTreatVacation().isPresent() ?
						this.addSetOfPremium.getTreatVacation().get().getAddition() :
							NotUseAtr.NOT_USE;
			}
		}
		// 通常
		// 「実動のみで計算する」時、加算しない
		if (this.addSetOfWorkTime.getCalculateActualOperation().isCalclationByActualTime()){
			return NotUseAtr.NOT_USE;
		}
		// 「就業時間の加算設定.休暇時間の扱い」を確認する　→　「加算する」を返す　（なければ、加算しない）
		return this.addSetOfWorkTime.getTreatVacation().isPresent() ?
				this.addSetOfWorkTime.getTreatVacation().get().getAddition() :
					NotUseAtr.NOT_USE;
	}
	
	/**
	 * 遅刻・早退を就業時間に含めるか判断する
	 * @param premiumAtr 割増区分
	 * @param lateEarlySet 就業時間帯の遅刻早退設定
	 * @return true:含める,false:含めない
	 */
	public boolean isIncludeLateEarlyInWorkTime(
			PremiumAtr premiumAtr,
			Optional<WorkTimezoneLateEarlySet> lateEarlySet) {
		
		if (premiumAtr == PremiumAtr.RegularWork){
			return this.addSetOfWorkTime.isIncludeLateEarlyInWorkTime(lateEarlySet);
		}
		if (this.useAtr.isUse()){
			return this.addSetOfPremium.isIncludeLateEarlyInWorkTime(lateEarlySet);
		}
		return this.addSetOfWorkTime.isIncludeLateEarlyInWorkTime(lateEarlySet);
	}
	
	/**
	 * 育児介護時間を含めて計算するか判断する
	 * @param premiumAtr 割増区分
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludCareTime(PremiumAtr premiumAtr) {
		
		if (premiumAtr == PremiumAtr.RegularWork){
			return this.addSetOfWorkTime.isCalculateIncludCareTime();
		}
		if (this.useAtr.isUse()){
			return this.addSetOfPremium.isCalculateIncludCareTime();
		}
		return this.addSetOfWorkTime.isCalculateIncludCareTime();
	}
	
	/** [5] 休暇分の加算方法を取得する */
	public ProcAtrAddMethod getAddMethodForVacation() {
		
		/** if 割増計算方法を設定する = しない　 */
		if (this.useAtr == NotUseAtr.NOT_USE) {
			
			/** if @就業時間の加算設定．休暇分を就業時間に含めるか判断する（） */
			return this.addSetOfWorkTime.isCalculateIncludVacation() 
					? ProcAtrAddMethod.ADD : ProcAtrAddMethod.NOT_ADD;
		}
		
		/** if @就業時間の加算設定．休暇分を就業時間に含めるか判断する（）　
		 * AND　not @割増時間の加算設定．休暇分を就業時間に含めるか判断する（） */
		if (this.addSetOfWorkTime.isCalculateIncludVacation() 
				&& !this.addSetOfPremium.isCalculateIncludVacation()) 
			return ProcAtrAddMethod.ADD_FOR_SHORTAGE;

		if (!this.addSetOfWorkTime.isCalculateIncludVacation() 
				&& !this.addSetOfPremium.isCalculateIncludVacation()) 
			return ProcAtrAddMethod.NOT_ADD;
		
		return ProcAtrAddMethod.ADD;
	}
	
	/**
	 * インターバル免除時間を含めて計算するか判断する
	 * @param premiumAtr 割増区分
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludIntervalExemptionTime(PremiumAtr premiumAtr) {
		
		if (premiumAtr == PremiumAtr.RegularWork){
			return this.addSetOfWorkTime.isCalculateIncludIntervalExemptionTime();
		}
		if (this.useAtr.isUse()){
			return this.addSetOfPremium.isCalculateIncludIntervalExemptionTime();
		}
		return this.addSetOfWorkTime.isCalculateIncludIntervalExemptionTime();
	}
	
	/**
	 * 「実働時間のみで計算する」に変更して作成する
	 * @return 「実働時間のみで計算する」に変更したインスタンス
	 */
	public AddSettingOfWorkingTime createCalculationByActualTime() {
		return new AddSettingOfWorkingTime(
				this.addSetOfPremium.createCalculationByActualTime(),
				this.addSetOfWorkTime.createCalculationByActualTime(),
				this.useAtr);
	}
	
	/**
	 * 割増時間の計算方法を就業時間の計算方法と同じにする
	 * （就業時間から控除するフレックス時間を求めるときの加算設定を取得する）
	 * 
	 * ■この処理が必要な理由
	 * 		不足時加算（割増：実働のみ、就業時間：実働以外も含める）、
	 * 		フレックス 1ヵ月 法定177h 実働171h 年休1日(8h) の場合
	 * 
	 * 		①フレックス控除前の就業時間を計算する（179h）←年休分が加算されている
	 * 		②フレックス時間を計算する（0h）←年休分が加算されていない
	 * 		③就業時間を計算する 179h - 0h = 179h ← NG（就業時間は177h）
	 * 
	 * 		上記の場合に、②の計算で「割増時間の加算設定」を「就業時間の加算設定」で
	 * 		上書きすることによって（割増：実働以外も含める、就業時間：実働以外も含める にしたい）
	 * 		正しい計算を実現している為、この処理が必要。
	 * 		この処理を使うと下記の計算となる。
	 * 
	 * 		①フレックス控除前の就業時間を計算する（179h）←年休分が加算されている
	 * 		②フレックス時間を計算する（2h）←年休分が加算されている
	 * 		③就業時間を計算する 179h - 2h = 177h ← OK
	 * 
	 * @return 割増計算方法を設定する＝「しない」に変更した「労働時間の加算設定」
	 */
	public AddSettingOfWorkingTime getWorkTimeDeductFlexTime() {
		return new AddSettingOfWorkingTime(
				this.addSetOfPremium,
				this.addSetOfWorkTime,
				NotUseAtr.NOT_USE);
	}
	
	/**
	 * 遅刻、早退の控除設定を「控除する」に変更して作成する
	 * @return 遅刻、早退の控除設定を「控除する」に変更したインスタンス
	 */
	public AddSettingOfWorkingTime createNewDeductLateEarly(){
		return new AddSettingOfWorkingTime(
				this.addSetOfPremium,
				this.addSetOfWorkTime.changeNotInclude(),
				this.useAtr);
	}
}

