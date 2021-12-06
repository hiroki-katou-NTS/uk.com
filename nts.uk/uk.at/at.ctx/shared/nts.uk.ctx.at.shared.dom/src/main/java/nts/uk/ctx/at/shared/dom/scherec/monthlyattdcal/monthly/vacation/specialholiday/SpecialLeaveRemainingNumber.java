package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;

/**
 * 特別休暇残数
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveRemainingNumber {

	/** 合計残日数 */
	public DayNumberOfRemain dayNumberOfRemain;

	/** 合計残時間 */
	public Optional<TimeOfRemain> timeOfRemain;

	/** 明細 */
	private List<SpecialLeaveRemainingDetail> details;

	public SpecialLeaveRemainingNumber() {
		this.dayNumberOfRemain=new DayNumberOfRemain(0.0);
		this.timeOfRemain=Optional.empty();
		this.details=new ArrayList<>();
	}

	private SpecialLeaveRemainingNumber(BigDecimal days, Integer minutes) {
		this.dayNumberOfRemain = new DayNumberOfRemain(days== null? 0.0d: days.doubleValue());
		this.timeOfRemain = minutes != null ? Optional.of(new TimeOfRemain(minutes)) : Optional.empty();
		this.details = new ArrayList<SpecialLeaveRemainingDetail>();
	}

	public static SpecialLeaveRemainingNumber createFromJavaType(BigDecimal days, Integer minutes) {
		return new SpecialLeaveRemainingNumber(days, minutes);
	}
	private SpecialLeaveRemainingNumber(Double days, Integer minutes) {
		this.dayNumberOfRemain = new DayNumberOfRemain(days);
		this.timeOfRemain = minutes != null ? Optional.of(new TimeOfRemain(minutes)) : Optional.empty();
		this.details = new ArrayList<SpecialLeaveRemainingDetail>();
	}

	public static SpecialLeaveRemainingNumber createFromJavaType(Double days, Integer minutes) {
		return new SpecialLeaveRemainingNumber(days, minutes);
	}

	/**
	 * 残数がマイナスのときにはTrueを返す
	 * @return
	 */
	public boolean isMinus(){
		if ( this.isMinusOfDay() ){
			return true;
		}
		if ( timeOfRemain.isPresent() ){
			if ( this.isMinusOfTime() ){
				return true;
			}
		}
		return false;
	}
	/**
	 * 残数がマイナスのときにはTrueを返す
	 * @return
	 */
	public boolean isMinusOfDay(){
		if ( dayNumberOfRemain.v() < 0.0 ){
			return true;
		}
		return false;
	}
	/**
	 * 残数がマイナスのときにはTrueを返す
	 * @return
	 */
	public boolean isMinusOfTime(){
		if ( timeOfRemain.isPresent() ){
			if ( timeOfRemain.get().v() < 0 ){
				return true;
			}
		}
		return false;
	}

	/**
	 * クリア
	 */
	public void clear(){
		dayNumberOfRemain = new DayNumberOfRemain(0.0);
		timeOfRemain = Optional.empty();
		clearDetails();
	}

	/**
	 * 明細をクリア（要素数を０にする）
	 */
	public void clearDetails(){
		details = new ArrayList<SpecialLeaveRemainingDetail>();
	}

	public SpecialLeaveRemainingNumber clone(){

		SpecialLeaveRemainingNumber cloned = new SpecialLeaveRemainingNumber();

		/** 合計残日数 */
		cloned.dayNumberOfRemain = new DayNumberOfRemain(this.getDayNumberOfRemain().v());

		/** 合計残時間 */
		if ( this.timeOfRemain.isPresent() ) {
			cloned.timeOfRemain = Optional.of(new TimeOfRemain(timeOfRemain.get().v()));
		} else {
			cloned.timeOfRemain = Optional.empty();
		}

		/** 明細 */
		ArrayList<SpecialLeaveRemainingDetail> details = new ArrayList<SpecialLeaveRemainingDetail>();
		for(SpecialLeaveRemainingDetail c : this.details) {
			details.add(c.clone());
		}
		cloned.details = details;

		return cloned;
	}

	/**
	 * 特休付与残数データから特別休暇残数を作成
	 * @param remainingDataList 特休付与残数データリスト
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<SpecialLeaveGrantRemainingData> remainingDataList){

		// 明細、合計残日数をクリア
		this.details = new ArrayList<>();
		this.dayNumberOfRemain = new DayNumberOfRemain(0.0);
		this.timeOfRemain = Optional.of( new TimeOfRemain(0));

		// パラメータ「List<特別休暇付与残数>」を取得
		// 【ソート】 付与日(ASC)
		remainingDataList.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));

		// 取得した「特別休暇付与残数」でループ
		for (val remainingData : remainingDataList){

			// 【条件】 期限切れ状態　=　使用可能
			if (remainingData.getExpirationStatus() == LeaveExpirationStatus.AVAILABLE){
				val remainingNumber = remainingData.getDetails().getRemainingNumber();

				// 「特休不足ダミーフラグ」をチェック
				if (remainingData.isDummyData() == false){

					// 特別休暇残数．明細に特別休暇残明細を追加
					TimeOfRemain remainingTime = null;
					if (remainingNumber.getMinutes().isPresent()){
						remainingTime = new TimeOfRemain(remainingNumber.getMinutes().get().v());
					}
					this.details.add(SpecialLeaveRemainingDetail.of(
						remainingData.getGrantDate(),
						new DayNumberOfRemain(remainingNumber.getDays().v()),
						Optional.ofNullable(remainingTime)));
				}

				// 日数←「特別休暇付与残数．明細．残数．日数」を合計
				this.dayNumberOfRemain = new DayNumberOfRemain(
						this.dayNumberOfRemain.v() + remainingNumber.getDays().v());

				// 時間←「特別休暇付与残数．明細．残数．時間」を加算
				if ( remainingNumber.getMinutes().isPresent() ){
					this.timeOfRemain = Optional.of( new TimeOfRemain(
						this.timeOfRemain.get().v() + remainingNumber.getMinutes().get().v()));
				}
			}
		}
	}
}
