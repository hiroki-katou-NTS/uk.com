package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * 積立年休残情報
 * @author masaaki_jinno
 *
 */
@Getter
public class ReserveLeaveRemainingNumberInfo implements Cloneable {

	/** 合計残日数 */
	@Setter
	private ReserveLeaveRemainingNumber totalRemaining;
	/** 付与前 */
	@Setter
	private ReserveLeaveRemainingNumber beforeGrant;
	/** 付与後 */
	@Setter
	private Optional<ReserveLeaveRemainingNumber> afterGrant;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveRemainingNumberInfo(){
		this.totalRemaining = new ReserveLeaveRemainingNumber();
		this.beforeGrant = new ReserveLeaveRemainingNumber();
		this.afterGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param totalRemaining 合計残日数
	 * @param details 明細
	 * @return 積立年休残数
	 */
	public static ReserveLeaveRemainingNumberInfo of(
			ReserveLeaveRemainingNumber totalRemaining,
			ReserveLeaveRemainingNumber beforeGrant,
			Optional<ReserveLeaveRemainingNumber> afterGrant
			){
		
		ReserveLeaveRemainingNumberInfo domain = new ReserveLeaveRemainingNumberInfo();
		domain.totalRemaining = totalRemaining;
		domain.beforeGrant = beforeGrant;
		domain.afterGrant = afterGrant;
		return domain;
	}
	
	@Override
	public ReserveLeaveRemainingNumberInfo clone() {
		ReserveLeaveRemainingNumberInfo cloned = new ReserveLeaveRemainingNumberInfo();
		try {
			cloned.totalRemaining = this.totalRemaining.clone();
			cloned.beforeGrant = this.beforeGrant.clone();
			if ( this.afterGrant.isPresent() ){
				cloned.afterGrant = Optional.of(this.afterGrant.get().clone());
			} else {
				cloned.afterGrant = Optional.empty();
			}
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
	
	/**
	 * 付与前退避処理
	 */
	public void saveStateBeforeGrant(){
		// 合計残数を付与前に退避する
		beforeGrant = totalRemaining.clone();
	}
	
	/**
	 * 付与後退避処理
	 */
	public void saveStateAfterGrant(){
		// 合計残数を付与後に退避する
		afterGrant = Optional.of(totalRemaining.clone());
	}
	
}
