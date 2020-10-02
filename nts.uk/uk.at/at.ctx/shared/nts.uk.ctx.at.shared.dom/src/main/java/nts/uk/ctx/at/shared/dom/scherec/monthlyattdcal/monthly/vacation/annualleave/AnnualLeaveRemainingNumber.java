package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remain.AnnualLeaveGrantRemaining;

/**
 * 年休残数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveRemainingNumber implements Cloneable {

	/** 合計残日数 */
	@Setter
	private AnnualLeaveRemainingDayNumber totalRemainingDays;
	/** 合計残時間 */
	private Optional<RemainingMinutes> totalRemainingTime;
	/** 明細 */
	private List<AnnualLeaveRemainingDetail> details;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveRemainingNumber(){
		
		this.totalRemainingDays = new AnnualLeaveRemainingDayNumber(0.0);
		this.totalRemainingTime = Optional.empty();
		this.details = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param totalRemainingDays 合計残日数
	 * @param totalRemainingTime 合計残時間
	 * @param details 明細
	 * @return 年休残数
	 */
	public static AnnualLeaveRemainingNumber of(
			AnnualLeaveRemainingDayNumber totalRemainingDays,
			Optional<RemainingMinutes> totalRemainingTime,
			List<AnnualLeaveRemainingDetail> details){
		
		AnnualLeaveRemainingNumber domain = new AnnualLeaveRemainingNumber();
		domain.totalRemainingDays = totalRemainingDays;
		domain.totalRemainingTime = totalRemainingTime;
		domain.details = details;
		return domain;
	}
	
	@Override
	public AnnualLeaveRemainingNumber clone() {
		AnnualLeaveRemainingNumber cloned = new AnnualLeaveRemainingNumber();
		try {
			cloned.totalRemainingDays = new AnnualLeaveRemainingDayNumber(this.totalRemainingDays.v());
			if (this.totalRemainingTime.isPresent()){
				cloned.totalRemainingTime = Optional.of(new RemainingMinutes(this.totalRemainingTime.get().v()));
			}
			for (val detail : this.details) cloned.details.add(detail.clone());
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveRemainingNumber clone error.");
		}
		return cloned;
	}
	
	/**
	 * 年休付与残数データから年休残数を作成
	 * @param remainingDataList 年休付与残数データリスト
	 */
	public void createRemainingNumberFromGrantRemaining(List<AnnualLeaveGrantRemaining> remainingDataList){

		// 明細、合計残日数をクリア
		this.details = new ArrayList<>();
		this.totalRemainingDays = new AnnualLeaveRemainingDayNumber(0.0);
		
		// 「年休付与残数データ」を取得
		remainingDataList.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
		
		for (val remainingData : remainingDataList){
			if (remainingData.getExpirationStatus() == LeaveExpirationStatus.EXPIRED) continue;
			val remainingNumber = remainingData.getDetails().getRemainingNumber();
			
			// 「年休不足ダミーフラグ」をチェック
			if (remainingData.isDummyAtr() == false){
				
				// 明細に年休付与残数データ．明細．残数を追加
				AnnualLeaveRemainingTime remainingTime = null;
				if (remainingNumber.getMinutes().isPresent()){
					remainingTime = new AnnualLeaveRemainingTime(remainingNumber.getMinutes().get().v());
				}
				this.details.add(AnnualLeaveRemainingDetail.of(
						remainingData.getGrantDate(),
						new AnnualLeaveRemainingDayNumber(remainingNumber.getDays().v()),
						Optional.ofNullable(remainingTime)));
			}
			
			// 合計残日数　←　「明細．日数」の合計
			this.totalRemainingDays = new AnnualLeaveRemainingDayNumber(
					this.totalRemainingDays.v() + remainingNumber.getDays().v());
		}
	}
	
	/**
	 * 全ての明細に日数を設定
	 * @param days 日数
	 */
	public void setDaysOfAllDetail(Double days){
		for (val detail : this.details) detail.setDays(new AnnualLeaveRemainingDayNumber(days));
	}
}
