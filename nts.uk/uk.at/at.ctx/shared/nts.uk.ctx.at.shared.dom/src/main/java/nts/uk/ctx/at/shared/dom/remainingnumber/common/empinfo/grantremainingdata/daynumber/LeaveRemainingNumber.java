package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;

/**
 * 休暇残数  
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class LeaveRemainingNumber {

	/**
	 * 日数
	 */
	protected LeaveRemainingDayNumber days;

	/**
	 * 時間
	 */
	protected Optional<LeaveRemainingTime> minutes;

	/**
	 * コンストラクタ
	 * @param days
	 * @param minutes
	 */
	public LeaveRemainingNumber() {
		this.days = new LeaveRemainingDayNumber(0.0);
		this.minutes = Optional.of(new LeaveRemainingTime(0));
	}
	
	/**
	 * コンストラクタ
	 * @param days
	 * @param minutes
	 */
	public LeaveRemainingNumber(double days, Integer minutes) {
		this.days = new LeaveRemainingDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new LeaveRemainingTime(minutes)) : Optional.empty();
	}
	
	/**
	 * コンストラクタ
	 * @param days
	 * @param minutes
	 * @return
	 */
	public static LeaveRemainingNumber createFromJavaType(double days, Integer minutes) {
		return new LeaveRemainingNumber(days, minutes);
	}
	
	@Override
	public LeaveRemainingNumber clone() {
		LeaveRemainingNumber cloned = new LeaveRemainingNumber();
		try {
			cloned.days = new LeaveRemainingDayNumber(days.v());
			cloned.minutes = minutes.map(c -> new LeaveRemainingTime(c.v()));
		}
		catch (Exception e){
			throw new RuntimeException("LeaveRemainingNumber clone error.");
		}
		return cloned;
	}
	
	/**
	 * 残数を加算
	 * @param aLeaveRemainingNumber
	 */
	public void add(LeaveRemainingNumber aLeaveRemainingNumber){
		
		// 日付加算
		days = days.add(getDays());
		
		// 時間加算
		if ( aLeaveRemainingNumber.getMinutes().isPresent() ){
			if ( this.getMinutes().isPresent() ){
				this.setMinutes(Optional.of(
					this.getMinutes().get().add(
							aLeaveRemainingNumber.getMinutes().get())));
			}
			else
			{
				this.setMinutes(Optional.of(new LeaveRemainingTime(
						aLeaveRemainingNumber.getMinutes().get().v())));
			}
		}
	}
	
	/**
	 * 休暇使用数を消化する
	 * @param require 残数処理 Requireクラス
	 * @param leaveUsedNumber 休暇使用数
	 * @param employeeId 社員ID
	 * @param baseDate 基準日
	 */
	public LeaveUsedNumber digestLeaveUsedNumber(
			RequireM3 require,
			LeaveUsedNumber leaveUsedNumber,
			String employeeId,
			GeneralDate baseDate){
		
		// 消化できなかった休暇使用数クラスのオブジェクトを作成
		LeaveUsedNumber unusedNumber = new LeaveUsedNumber();
		
		if ( !leaveUsedNumber.getMinutes().isPresent() ){ // 休暇使用数
			return unusedNumber;
		}
			
		while(true){
			if ( 1 <= this.days.v() // 1<=メンバ変数.休暇残数.日数 
				&& 1 <= leaveUsedNumber.getMinutes().get().v() // input.休暇使用数.時間
			){
				boolean needStacking = false; // 積み崩しが必要かどうか
				
				if (!minutes.isPresent()){
					// メンバ変数.時間が存在しないときは、積み崩しが必要
					needStacking = true;
					
				} else if ( this.getMinutes().get().v() < leaveUsedNumber.getMinutes().get().v() ){
					// メンバ変数.時間 < input.休暇使用数.時間のときは、積み崩しが必要
					needStacking = true;
				}

				if (needStacking){ // 積み崩しが必要なとき
					
					// 年休１日に相当する時間年休時間を取得する
					Optional<LimitedTimeHdTime> contractTimeOpt
						= getContractTime(require, employeeId, baseDate);
					
					// 積み崩し処理を行う
					if (contractTimeOpt.isPresent()){
						
						// メンバ変数.日数から１をマイナス。
						days = new LeaveRemainingDayNumber(days.v() - 1); 
						
						// メンバ変数.時間に、「年休１日に相当する時間年休時間」を加算。
						if (!minutes.isPresent()){
							minutes = Optional.of(new LeaveRemainingTime(
								contractTimeOpt.get().v()));
						} else {
							minutes = Optional.of(new LeaveRemainingTime(
								minutes.get().v() +	contractTimeOpt.get().v()));
						}
					} else {
						break;
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}
		
		// 残数が足りているか？
		boolean surfaceRemNum = false;
		
		// Input.休暇使用数.日数<=メンバ変数.日数
		if ( leaveUsedNumber.getDays().v() <= days.v() ){ 
			if ( leaveUsedNumber.getMinutes().isPresent() ){
				if ( leaveUsedNumber.getMinutes().get().v() == 0 ){
					// Input.休暇使用数.時間が０のときは足りている
					surfaceRemNum = true;
					
				} else if ( this.getMinutes().isPresent() ){
					// Input.休暇使用数.時間<=メンバ変数.時間
					if ( leaveUsedNumber.getMinutes().isPresent() ){
						if ( leaveUsedNumber.getMinutes().get().v() 
								<= this.getMinutes().get().v() ){
							surfaceRemNum = true;
						}
					}
				}
			}
		}
		
		// 一時変数（日付）
		double this_day = this.getDays().v();
		double input_day = leaveUsedNumber.getDays().v();
		
		// 一時変数（時間）
		int this_time = 0;
		int input_time = 0;
		
		if ( this.getMinutes().isPresent() ){
			this_time = this.getMinutes().get().v();
		}
		if ( leaveUsedNumber.getMinutes().isPresent() ){
			input_time = leaveUsedNumber.getMinutes().get().v();
		}
		
		// 残数が足りている場合
		if ( surfaceRemNum ){
			this.days = new LeaveRemainingDayNumber(this_day-input_day);
			this.minutes = Optional.of(new LeaveRemainingTime(this_time-input_time));
			
		} else { // 残数が足りていない場合
			
			// Input.休暇使用数.時間 <= メンバ変数.時間 のとき
			// ・メンバ変数.時間 ← メンバ変数.時間 ーInput.休暇使用数.時間
			if (input_time <= this_time){
				this.minutes = Optional.of(new LeaveRemainingTime(this_time-input_time));
			}

			// メンバ変数.時間 ＜ Input.休暇使用数.時間のとき
			// ・消化できなかった休暇使用数.時間 ← Input.休暇使用数.時間ーメンバ変数.時間
			// ・メンバ変数.時間←０
			if (this_time < input_time){
				unusedNumber.minutes = Optional.of(new LeaveUsedTime(input_time-this_time));
				this.minutes = Optional.of(new LeaveRemainingTime(0));
			}

			// Input.休暇使用数.日数 <= 休暇残数.日数 のとき
			// ・メンバ変数.日数 ← メンバ変数.日数 ーInput.休暇使用数.日数
			if (input_day <= this_day){
				this.days = new LeaveRemainingDayNumber(this_day-input_day);
			}
			
			// メンバ変数.日数 ＜ Input.休暇使用数.日数のとき
			// ・消化できなかった休暇使用数.日数 ← Input.休暇使用数.日数ーメンバ変数.日数
			// ・メンバ変数.日数 ←０
			if (this_day < input_day){
				unusedNumber.days = new LeaveUsedDayNumber(input_day - this_day);
				this.days = new LeaveRemainingDayNumber(0.0);
			}
		}
		
		return unusedNumber;
	}
	
	/**
	 * 年休１日に相当する時間年休時間を取得する
	 * @param repositoriesRequiredByRemNum ロードデータ（キャッシュ）
	 * @param employeeId 社員ID
	 * @param baseDate 基準日
	 * @return
	 */
	static public Optional<LimitedTimeHdTime> getContractTime(
		RequireM3 require,
		String companyID,
		String employeeId,
		GeneralDate baseDate){
		
		// 契約時間
		Optional<LimitedTimeHdTime> contractTime = Optional.empty();
		
//		WorkingConditionItemRepository workingConditionItemRepository
//			= repositoriesRequiredByRemNum.getWorkingConditionItemRepository();
		
		// ドメインモデル「年休設定」を取得する
		AnnualPaidLeaveSetting annualPaidLeave = require.annualPaidLeaveSetting(companyID);
		if(annualPaidLeave == null){
			return Optional.empty();
		}
		
		// 契約時間が会社一律で設定されているか
		if ( annualPaidLeave.getTimeSetting().getTimeManageType().equals(ManageDistinct.YES)){
			// 会社の設定情報を取得する
			
			
			
		} else {
			// アルゴリズム「社員の労働条件を取得する」を実行し、契約時間を取得する
			Optional<WorkingConditionItem> workCond
				= require.workingConditionItem(employeeId, baseDate);
	
			if (workCond.isPresent()) {
				contractTime = workCond.get().getContractTime().v() == null ? Optional.empty()
						: Optional.ofNullable(new LimitedTimeHdTime(workCond.get().getContractTime().v()));
				
				// 丸め処理 　ooooo
			}
		}
		
		return contractTime;
	}
	
	public static interface RequireM3 extends AbsenceTenProcess.RequireM1 {
		
		// 労働条件取得
		Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate);
	}

}
