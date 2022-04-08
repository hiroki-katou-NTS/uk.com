package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.DigestionLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RemNumShiftListWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfoAfterDigestion;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;

/**
 * 休暇付与残数データ
 * 
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveGrantRemainingData extends AggregateRoot {

	// ID
	protected String leaveID;

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
	protected LeaveExpirationStatus expirationStatus;

	/**
	 * 登録種別
	 */
	protected GrantRemainRegisterType registerType;

	/**
	 * 明細
	 */
	protected LeaveNumberInfo details;

	public static LeaveGrantRemainingData createDataFromJavaType(String annLeavID, String cID, String employeeId,
			GeneralDate grantDate, GeneralDate deadline, int expirationStatus, int registerType, double grantDays,
			Integer grantMinutes, double usedDays, Integer usedMinutes, Double stowageDays, Double numberOverDays,
			Integer timeOver, double remainDays, Integer remainMinutes, double usedPercent) {

		LeaveGrantRemainingData domain = new LeaveGrantRemainingData();
		domain.leaveID = annLeavID;
		domain.employeeId = employeeId;
		domain.grantDate = grantDate;
		domain.deadline = deadline;
		domain.expirationStatus = EnumAdaptor.valueOf(expirationStatus, LeaveExpirationStatus.class);
		domain.registerType = EnumAdaptor.valueOf(registerType, GrantRemainRegisterType.class);

		domain.details = new LeaveNumberInfo(grantDays, grantMinutes, usedDays, usedMinutes, stowageDays,
				numberOverDays, timeOver, remainDays, remainMinutes, usedPercent);

		// if (prescribedDays != null && deductedDays != null && workingDays !=
		// null) {
		// domain.annualLeaveConditionInfo = Optional
		// .of(AnnualLeaveConditionInfo.createFromJavaType(prescribedDays,
		// deductedDays, workingDays));
		// } else {
		// domain.annualLeaveConditionInfo = Optional.empty();
		// }
		return domain;
	}

	public void setAllValue(LeaveGrantRemainingData a) {
		this.employeeId = a.employeeId;
		this.grantDate = a.grantDate;
		this.deadline = a.deadline;
		this.expirationStatus = a.expirationStatus;
		this.registerType = a.registerType;
		this.details = a.details.clone();
	}

	/**
	 * ファクトリー
	 * 
	 * @param employeeId
	 *            社員ID
	 * @param grantDate
	 *            付与日
	 * @param deadline
	 *            期限日
	 * @param expirationStatus
	 *            期限切れ状態
	 * @param registerType
	 *            登録種別
	 * @param details
	 *            明細
	 * @return 休暇付与残数データ
	 */
	public static LeaveGrantRemainingData of(String employeeId, GeneralDate grantDate, GeneralDate deadline,
			LeaveExpirationStatus expirationStatus, GrantRemainRegisterType registerType, LeaveNumberInfo details) {

		LeaveGrantRemainingData domain = new LeaveGrantRemainingData();
		domain.leaveID = IdentifierUtil.randomUniqueId();
		domain.employeeId = employeeId;
		domain.grantDate = grantDate;
		domain.deadline = deadline;
		domain.expirationStatus = expirationStatus;
		domain.registerType = registerType;
		domain.details = details;
		return domain;
	}

	/**
	 * 休暇残数を指定使用数消化する
	 * 
	 * @param require
	 * @param targetRemainingDatas
	 *            付与残数
	 * @param remNumShiftListWork
	 *            複数の付与残数の消化処理を行う一時変数
	 * @param leaveUsedNumber
	 *            休暇使用数
	 * @param companyId
	 *            会社ID
	 * @param employeeId
	 *            社員ID
	 * @param date
	 *            年月日
	 * @return dummyData ダミーデータ
	 */
	public static Optional<LeaveGrantRemainingData> digest(LeaveRemainingNumber.RequireM3 require,
			List<LeaveGrantRemainingData> targetRemainingDatas, RemNumShiftListWork remNumShiftListWork,
			LeaveUsedNumber leaveUsedNumber, String companyId, String employeeId, GeneralDate baseDate) {

		// 休暇使用数を消化する
		remNumShiftListWork = DigestionLeaveGrantRemainingData.digest(companyId, targetRemainingDatas, leaveUsedNumber, baseDate, require);
		
		//参照の付与残数データの明細を更新
		for(LeaveGrantRemainingData x: targetRemainingDatas){
			val updateValue = remNumShiftListWork.getRemNumShiftWorkListOpt().stream().map(y -> {
				return y.getRefLeaveGrantRemainingData();
			}).filter(y -> y.getGrantDate().equals(x.getGrantDate())).findFirst();
			
			if(updateValue.isPresent()){
				x.updateDetails(updateValue.get().getDetails());
			}
		}
		
		// 残数不足で一部消化できなかったとき
		if (remNumShiftListWork.getUnusedNumber().isLargerThanZero()) {

			// 消化できなかった休暇使用数をもとに、付与残数ダミーデータを作成する
			return Optional.of(LeaveGrantRemainingData.of(employeeId, baseDate, baseDate, LeaveExpirationStatus.AVAILABLE,
					GrantRemainRegisterType.MONTH_CLOSE, remNumShiftListWork.toDetails()));

		}

		return Optional.empty();
	}

	/** 残数不足のときにはtrueを返す */
	public boolean isDummyData() {
		return this.details.isDummyData() && this.isShortageRemain();
	}

	private boolean isShortageRemain() {
		return details.isShortageRemain();
	}

	@Override
	public LeaveGrantRemainingData clone() {
		LeaveGrantRemainingData cloned = new LeaveGrantRemainingData();
		cloned.leaveID = new String(leaveID);
		cloned.employeeId = new String(employeeId);
		cloned.grantDate = grantDate;
		cloned.deadline = deadline;
		cloned.expirationStatus = expirationStatus;
		cloned.registerType = registerType;
		cloned.details = details.clone();

		return cloned;
	}
	
	/**
	 * [1]消化する
	 * @param require
	 * @param companyId
	 * @param baseDate
	 * @param usedNumber
	 * @return
	 */
	public GrantRemainingDataAfterDigestion digest(LeaveRemainingNumber.RequireM3 require, String companyId,
			GeneralDate baseDate, LeaveUsedNumber usedNumber){
		
		LeaveNumberInfoAfterDigestion digestedInfo = this.details.digest(require, companyId, this.employeeId, baseDate, usedNumber);
		
		return new GrantRemainingDataAfterDigestion(new LeaveGrantRemainingData(this.leaveID, this.employeeId, this.grantDate,
				this.deadline, this.expirationStatus, this.registerType, digestedInfo.getLeaveNumberInfo()),
				digestedInfo.getUnUsedNumber());
	}

	/**
	 * [2]消化しきれるか
	 * @param require
	 * @param companyId
	 * @param baseDate
	 * @param usedNumber
	 * @return
	 */
	public boolean canDigest(LeaveRemainingNumber.RequireM3 require, String companyId,
			GeneralDate baseDate, LeaveUsedNumber usedNumber){
		return this.details.canDigest(require, companyId, this.employeeId, baseDate, usedNumber);
	}
	

	
	/**
	 * [3] 消化できず残った数を取得
	 * @param require
	 * @param companyId
	 * @param baseDate
	 * @param usedNumber
	 * @return
	 */
	public LeaveGrantRemainingData getUndigestedNumber(LeaveRemainingNumber.RequireM3 require, String companyId,
			GeneralDate baseDate, LeaveUsedNumber usedNumber){
		return new LeaveGrantRemainingData(this.leaveID, this.employeeId, this.grantDate,
				this.deadline, this.expirationStatus, this.registerType, 
				this.details.getUndigestedNumber(require, companyId, this.employeeId, baseDate, usedNumber));
	}
	
	/**
	 * [4]明細更新
	 * @param updateDetails
	 */
	public void updateDetails(LeaveNumberInfo updateDetails){
		this.details = updateDetails;
	}
}
