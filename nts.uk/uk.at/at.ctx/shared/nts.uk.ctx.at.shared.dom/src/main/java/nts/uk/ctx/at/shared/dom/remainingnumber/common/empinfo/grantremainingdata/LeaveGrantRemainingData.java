package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RemNumShiftListWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.*;

/**
 * 休暇付与残数データ　 
 * @author masaaki_jinno
 *
 */
@Getter
@NoArgsConstructor 
@AllArgsConstructor
public abstract class LeaveGrantRemainingData extends AggregateRoot {

	protected String annLeavID;
	
	protected String cid;
	
	/**
	 * 社員ID
	 */
	protected String employeeId;

	/**
	 * 付与日
	 */
	protected GeneralDate grantDate;

	/**
	 * 期限日
	 */
	protected GeneralDate deadline;

	/**
	 * 期限切れ状態
	 */
	@Setter
	protected LeaveExpirationStatus expirationStatus;

	/**
	 * 登録種別
	 */
	protected GrantRemainRegisterType registerType;

	/**
	 * 明細
	 */
	protected LeaveNumberInfo details;

// 一時的にコメントアウト　神野
//	/** 
//	 * 休暇残数を指定使用数消化する
//	 * @param targetRemainingDatas 付与残数
//	 * @param repositoriesRequiredByRemNum 残数処理 キャッシュクラス
//	 * @param remNumShiftListWork 複数の付与残数の消化処理を行う一時変数
//	 * @param leaveUsedNumber 休暇使用数
//	 * @param employeeId 社員ID
//	 * @param baseDate　基準日
//	 * @param isForcibly　強制的に消化するか
//	 */
//	public static void digest(
//			List<LeaveGrantRemainingData> targetRemainingDatas,
//			LeaveRemainingNumber.RequireM3 require,
//			RemNumShiftListWork remNumShiftListWork,
//			LeaveUsedNumber leaveUsedNumber,
//			String employeeId,
//			GeneralDate baseDate,
//			boolean isForcibly){
//		
//	
//		// 取得した「付与残数」でループ
//		for (val targetRemainingData : targetRemainingDatas){
//			
//			// 休暇付与残数を追加する
//			remNumShiftListWork.AddLeaveGrantRemainingData(targetRemainingData);
//			
//			// 休暇使用数を消化できるかチェック
//			if ( !remNumShiftListWork.canDigest(
//					require, leaveUsedNumber, employeeId, baseDate) ){
//				// 消化できないときはループ
//				continue;
//			}
//		}
//		
//		// 休暇使用数を消化する
//		remNumShiftListWork.digest(
//				require, leaveUsedNumber, employeeId, baseDate);
//		
////		// 残数不足で一部消化できなかったとき
////		if ( !remNumShiftListWork.getUnusedNumber().isZero() ){
////			
////			// 消化できなかった休暇使用数をもとに、付与残数ダミーデータを作成する
////			// 「年休付与残数データ」を作成する
////			val dummyRemainData = new AnnualLeaveGrantRemaining(
////					AnnualLeaveGrantRemainingData.createFromJavaType(
////					"",
////					companyId, employeeId, tempAnnualLeaveMng.getYmd(), tempAnnualLeaveMng.getYmd(),
////					LeaveExpirationStatus.AVAILABLE.value, GrantRemainRegisterType.MONTH_CLOSE.value,
////					0.0, null,
////					0.0, null, null,
////					0.0, null,
////					0.0,
////					null, null, null));
////			dummyRemainData.setDummyAtr(true);
////			
////			// 年休を指定日数消化する
////			remainDaysWork = new ManagementDays(dummyRemainData.digest(remainDaysWork.v(), true));
////			
////			// 付与残数データに追加
////			this.grantRemainingList.add(dummyRemainData);
////			
////		}
//	}
	
}
