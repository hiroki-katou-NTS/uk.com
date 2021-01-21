package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remain.ReserveLeaveGrantRemaining;

/**
 * 積立年休残数
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveRemainingNumber implements Cloneable {

	/** 合計残日数 */
	@Setter
	private ReserveLeaveRemainingDayNumber totalRemainingDays;
	/** 明細 */
	private List<ReserveLeaveRemainingDetail> details;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveRemainingNumber(){
		
		this.totalRemainingDays = new ReserveLeaveRemainingDayNumber(0.0);
		this.details = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param totalRemainingDays 合計残日数
	 * @param details 明細
	 * @return 積立年休残数
	 */
	public static ReserveLeaveRemainingNumber of(
			ReserveLeaveRemainingDayNumber totalRemainingDays,
			List<ReserveLeaveRemainingDetail> details){
		
		ReserveLeaveRemainingNumber domain = new ReserveLeaveRemainingNumber();
		domain.totalRemainingDays = totalRemainingDays;
		domain.details = details;
		return domain;
	}
	
	@Override
	public ReserveLeaveRemainingNumber clone() {
		ReserveLeaveRemainingNumber cloned = new ReserveLeaveRemainingNumber();
		try {
			cloned.totalRemainingDays = new ReserveLeaveRemainingDayNumber(this.totalRemainingDays.v());
			for (val detail : this.details) cloned.details.add(detail.clone());
		}
		catch (Exception e){
			throw new RuntimeException("ReserveLeaveRemainingNumber clone error.");
		}
		return cloned;
	}
	
	/**
	 * 積立年休付与残数データから積立年休残数を作成
	 * @param remainingDataList 積立年休付与残数データリスト
	 */
	public void createRemainingNumberFromGrantRemaining(List<ReserveLeaveGrantRemaining> remainingDataList){

		// 明細、合計残日数をクリア
		this.details = new ArrayList<>();
		this.totalRemainingDays = new ReserveLeaveRemainingDayNumber(0.0);
		
		// 「積立年休付与残数データ」を取得
		remainingDataList.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
		
		for (val remainingData : remainingDataList){
			if (remainingData.getExpirationStatus() == LeaveExpirationStatus.EXPIRED) continue;
			val remainingNumber = remainingData.getDetails().getRemainingNumber();
			
			// 「積立年休不足ダミーフラグ」をチェック
			if (remainingData.isDummyAtr() == false){
				
				// 明細に積立年休付与残数データ．明細を追加
				this.details.add(ReserveLeaveRemainingDetail.of(
						remainingData.getGrantDate(),
						new ReserveLeaveRemainingDayNumber(remainingNumber.v())));
			}
			
			// 合計残日数　←　「明細．日数」の合計
			this.totalRemainingDays = new ReserveLeaveRemainingDayNumber(
					this.totalRemainingDays.v() + remainingNumber.v());
		}
	}
	
	/**
	 * 全ての明細に日数を設定
	 * @param days 日数
	 */
	public void setDaysOfAllDetail(Double days){
		for (val detail : this.details) detail.setDays(new ReserveLeaveRemainingDayNumber(days));
	}
}
