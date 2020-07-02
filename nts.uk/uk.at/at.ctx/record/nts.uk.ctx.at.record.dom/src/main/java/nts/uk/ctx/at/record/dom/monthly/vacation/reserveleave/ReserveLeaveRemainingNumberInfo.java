package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * 積立年休残数情報
 * @author masaaki_jinno
 *
 */
@Getter
public class ReserveLeaveRemainingNumberInfo implements Cloneable {

	/** 合計残日数 */
	@Setter
	private ReserveLeaveRemainingDayNumber totalRemainingDays;
	/** 付与前 */
	@Setter
	private ReserveLeaveRemainingDayNumber beforeGrant;
	/** 付与後 */
	@Setter
	private Optional<ReserveLeaveRemainingDayNumber> afterGrant;
	
//	/** 明細 */
//	private List<ReserveLeaveRemainingDetail> details;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveRemainingNumberInfo(){
		
		this.totalRemainingDays = new ReserveLeaveRemainingDayNumber(0.0);
		this.beforeGrant = new ReserveLeaveRemainingDayNumber(0.0);
		this.afterGrant = Optional.empty();
		//this.details = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param totalRemainingDays 合計残日数
	 * @param details 明細
	 * @return 積立年休残数
	 */
	public static ReserveLeaveRemainingNumberInfo of(
			ReserveLeaveRemainingDayNumber totalRemainingDays,
			ReserveLeaveRemainingDayNumber beforeGrant,
			Optional<ReserveLeaveRemainingDayNumber> afterGrant
			){
		
		ReserveLeaveRemainingNumberInfo domain = new ReserveLeaveRemainingNumberInfo();
		domain.totalRemainingDays = totalRemainingDays;
		//domain.details = details;
		domain.beforeGrant = beforeGrant;
		domain.afterGrant = afterGrant;
		return domain;
	}
	
	@Override
	public ReserveLeaveRemainingNumberInfo clone() {
		ReserveLeaveRemainingNumberInfo cloned = new ReserveLeaveRemainingNumberInfo();
		try {
			cloned.totalRemainingDays = new ReserveLeaveRemainingDayNumber(this.totalRemainingDays.v());
			// for (val detail : this.details) cloned.details.add(detail.clone());
		}
		catch (Exception e){
			throw new RuntimeException("ReserveLeaveRemainingNumberInfo clone error.");
		}
		return cloned;
	}
	
//	/**
//	 * 積立年休付与残数データから積立年休残数を作成
//	 * @param remainingDataList 積立年休付与残数データリスト
//	 */
//	public void createRemainingNumberFromGrantRemaining(List<ReserveLeaveGrantRemaining> remainingDataList){
//
//		// 明細、合計残日数をクリア
//		this.details = new ArrayList<>();
//		this.totalRemainingDays = new ReserveLeaveRemainingDayNumber(0.0);
//		
//		// 「積立年休付与残数データ」を取得
//		remainingDataList.sort((a, b) -> a.getGrantDate().compareTo(b.getGrantDate()));
//		
//		for (val remainingData : remainingDataList){
//			if (remainingData.getExpirationStatus() == LeaveExpirationStatus.EXPIRED) continue;
//			val remainingNumber = remainingData.getDetails().getRemainingNumber();
//			
//			// 「積立年休不足ダミーフラグ」をチェック
//			if (remainingData.isDummyAtr() == false){
//				
//				// 明細に積立年休付与残数データ．明細を追加
//				this.details.add(ReserveLeaveRemainingDetail.of(
//						remainingData.getGrantDate(),
//						new ReserveLeaveRemainingDayNumber(remainingNumber.v())));
//			}
//			
//			// 合計残日数　←　「明細．日数」の合計
//			this.totalRemainingDays = new ReserveLeaveRemainingDayNumber(
//					this.totalRemainingDays.v() + remainingNumber.v());
//		}
//	}
//	
//	/**
//	 * 全ての明細に日数を設定
//	 * @param days 日数
//	 */
//	public void setDaysOfAllDetail(Double days){
//		for (val detail : this.details) detail.setDays(new ReserveLeaveRemainingDayNumber(days));
//	}
}
