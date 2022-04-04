package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 休暇数情報（明細）
 * 
 * @author masaaki_jinno
 *
 */
@Getter
@AllArgsConstructor
public class LeaveNumberInfo implements Cloneable {

	/**
	 * 付与数
	 */
	@Setter
	protected LeaveGrantNumber grantNumber;

	/**
	 * 使用数
	 */
	@Setter
	protected LeaveUsedNumber usedNumber;

	/**
	 * 残数
	 */
	protected LeaveRemainingNumber remainingNumber;

	/**
	 * 使用率
	 */
	protected LeaveUsedPercent usedPercent;

	/**
	 * コンストラクタ
	 */
	public LeaveNumberInfo() {
		grantNumber = new LeaveGrantNumber();
		usedNumber = new LeaveUsedNumber();
		remainingNumber = new LeaveRemainingNumber();
		usedPercent = new LeaveUsedPercent(new BigDecimal(0.0));
	}
	
	public LeaveNumberInfo(LeaveNumberInfo info) {
		this.grantNumber = info.getGrantNumber();
		this.usedNumber = info.getUsedNumber();
		this.remainingNumber = info.getRemainingNumber();
		this.usedPercent = info.getUsedPercent();
	}

	/**
	 * [C-1] 作成
	 * @param grantNumber
	 * @param usedNumber
	 * @param remainingNumber
	 */
	public LeaveNumberInfo(LeaveGrantNumber grantNumber, LeaveUsedNumber usedNumber,LeaveRemainingNumber remainingNumber){
		this.grantNumber = grantNumber;
		this.usedNumber = usedNumber;
		this.remainingNumber = remainingNumber; 
		this.usedPercent = findLeaveUsedPercent(grantNumber, usedNumber);
	}
	
	
	/**
	 * ファクトリー
	 * 
	 * @param grantNumber
	 *            付与数
	 * @param usedNumber
	 *            使用数
	 * @param remainingNumber
	 *            残数
	 * @param usedPercent
	 *            使用率
	 * @return 休暇数情報（明細）
	 */
	public static LeaveNumberInfo of(LeaveGrantNumber grantNumber, LeaveUsedNumber usedNumber,
			LeaveRemainingNumber remainingNumber, LeaveUsedPercent usedPercent) {

		LeaveNumberInfo domain = new LeaveNumberInfo();
		domain.grantNumber = grantNumber;
		domain.usedNumber = usedNumber;
		domain.remainingNumber = remainingNumber;
		domain.usedPercent = usedPercent;
		return domain;
	}

	/**
	 * すべて使用する
	 */
	public void digestAll() {
		// 使用数← 付与数

		// 日数
		usedNumber.days = new LeaveUsedDayNumber(grantNumber.getDays().v());

		// 時間
		if (grantNumber.getMinutes().isPresent()) {
			usedNumber.setMinutes(Optional.of(new LeaveUsedTime(grantNumber.getMinutes().get().v())));
		} else {
			usedNumber.setMinutes(Optional.of(new LeaveUsedTime(0)));
		}

		// 残数 ← ０
		remainingNumber.setDays(new LeaveRemainingDayNumber(0.0));
		remainingNumber.setMinutes(Optional.of(new LeaveRemainingTime(0)));
	}

	/**
	 * 残数をセットする
	 * 
	 * @param leaveRemainingNumber
	 *            休暇残数
	 */
	public void setRemainingNumber(LeaveRemainingNumber.RequireM3 require, String companyId, String employeeId, GeneralDate baseDate,
			LeaveRemainingNumber leaveRemainingNumber) {

		this.remainingNumber = leaveRemainingNumber.clone();
		
		LeaveRemainingNumber usedPlusCarryOver = this.remainingNumber.clone();
		if(this.getUsedNumber().isLimitOver()){
			usedPlusCarryOver.add(this.getUsedNumber().getLeaveOverLimitNumber().get().toLeaveRemainingNumber());
		}
		LeaveRemainingNumber usedNumber = this.grantNumber.calcDiff(companyId, employeeId, baseDate, usedPlusCarryOver, require);
		this.usedNumber = usedNumber.toLeaveUsedNumber(this.getUsedNumber().getStowageDays(), this.getUsedNumber().getLeaveOverLimitNumber());
	}
	
	public void setRemainingNumber(LeaveRemainingNumber leaveRemainingNumber) {
		this.remainingNumber = leaveRemainingNumber.clone();
	}

	@Override
	public LeaveNumberInfo clone() {
		LeaveNumberInfo cloned = new LeaveNumberInfo();

		cloned.grantNumber = grantNumber.clone();
		cloned.usedNumber = usedNumber.clone();
		cloned.remainingNumber = remainingNumber.clone();
		cloned.usedPercent = new LeaveUsedPercent(usedPercent.v());

		return cloned;
	}

	public LeaveNumberInfo(double grantDays, Integer grantMinutes, double usedDays, Integer usedMinutes,
			Double stowageDays, Double numberOverDays, Integer timeOver, double remainDays, Integer remainMinutes,
			double usedPercent) {
		this.grantNumber = LeaveGrantNumber.createFromJavaType(grantDays, grantMinutes);
		this.usedNumber = LeaveUsedNumber.createFromJavaType(usedDays, usedMinutes, stowageDays, numberOverDays,
				timeOver);
		this.remainingNumber = LeaveRemainingNumber.createFromJavaType(remainDays, remainMinutes);
		this.usedPercent = new LeaveUsedPercent(new BigDecimal(0));
		if (grantDays != 0) {
			String usedPer = new DecimalFormat("#.#").format(usedDays / grantDays);
			this.usedPercent = new LeaveUsedPercent(new BigDecimal(usedPer));
		}
	}

	/** 残数不足のときにはtrueを返す */
	public boolean isShortageRemain() {
		return this.remainingNumber.isShortageRemain();
	}

	/** 残数を補正する */
	public void correctRemainNumbers() {

		/** 残数がマイナスかを確認する */
		if (this.isShortageRemain()) {

			/** 使用数を補正する */
			this.remainingNumber = LeaveRemainingNumber.of(new LeaveRemainingDayNumber(0d), Optional.empty());
			this.usedNumber = LeaveUsedNumber.of(new LeaveUsedDayNumber(this.grantNumber.days.v()),
					this.grantNumber.minutes.map(c -> new LeaveUsedTime(c.v())), Optional.empty(), Optional.empty());
		}
	}

	public boolean isDummyData() {
		return this.getGrantNumber().isZero();
	}
	
	
	/**
	 * [2] 消化しきれるか
	 * @param usedNumber
	 * @return
	 */
	public boolean canDigest(LeaveRemainingNumber.RequireM3 require, String companyId,
			String employeeId, GeneralDate baseDate, LeaveUsedNumber usedNumber){
		return this.remainingNumber.canDigest(require, companyId, employeeId, baseDate, usedNumber);
	}
	
	
	/**
	 * 	[3] 消化する
	 * @param require
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @param usedNumber
	 * @return
	 */
	public LeaveNumberInfoAfterDigestion digest(LeaveRemainingNumber.RequireM3 require, String companyId,
			String employeeId, GeneralDate baseDate, LeaveUsedNumber leaveusedNumber) {		
		
		LeaveUsedNumber stackedUsedNumber = this.usedNumber.clone();
		if (this.remainingNumber.needStacking(require, companyId, employeeId, baseDate, leaveusedNumber)) {
			stackedUsedNumber = this.usedNumber.addStowageDays(new LeaveUsedDayNumber(1.0));
		}

		LeaveRemainingNumber remainingNumber = this.remainingNumber.digest(require, companyId, employeeId, baseDate,
				leaveusedNumber);

		LeaveUsedNumber digestedUsedNumber = this.remainingNumber.digestUsedNumber(require, leaveusedNumber,
				remainingNumber, companyId, employeeId, baseDate);

		digestedUsedNumber.add(this.usedNumber);
		
		LeaveUsedNumber unDigestedUsedNumber = calculateForUnDigestedNumber(require, companyId, employeeId,
				baseDate, leaveusedNumber, remainingNumber);

		return new LeaveNumberInfoAfterDigestion(new LeaveNumberInfo(this.grantNumber,
				LeaveUsedNumber.of(digestedUsedNumber.days, digestedUsedNumber.minutes, stackedUsedNumber.stowageDays,
						stackedUsedNumber.leaveOverLimitNumber),
				remainingNumber), unDigestedUsedNumber);
	}
	
	/**
	 * [4] 消化できなかった使用数を求める
	 * @param require
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @param leaveusedNumber
	 * @param remainingNumber
	 * @return
	 */
	public LeaveUsedNumber calculateForUnDigestedNumber(LeaveRemainingNumber.RequireM3 require, String companyId,
			String employeeId, GeneralDate baseDate, LeaveUsedNumber leaveusedNumber,
			LeaveRemainingNumber remainingNumber) {
		return this.remainingNumber.calculateForUnDigestedNumber(require, leaveusedNumber, remainingNumber,
				companyId, employeeId, baseDate);
	}
	
	
	
	/**
	 * [5] 消化できず残った数を取得
	 * @param require
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @param usedNumber
	 * @return
	 */
	public LeaveNumberInfo getUndigestedNumber(LeaveRemainingNumber.RequireM3 require, String companyId,
			String employeeId, GeneralDate baseDate, LeaveUsedNumber usedNumber){
		
		LeaveRemainingNumber remainingNumber = this.remainingNumber.getUndigestedNumber(require, companyId, employeeId, baseDate,
				usedNumber);
		
		return new LeaveNumberInfo(this.grantNumber, this.usedNumber, remainingNumber,this.usedPercent);
	}
	
	/**
	 * [prv-1] 使用率を求める
	 * @param grantNumber
	 * @param usedNumber
	 * @return
	 */
	private LeaveUsedPercent findLeaveUsedPercent(LeaveGrantNumber grantNumber, LeaveUsedNumber usedNumber){
		if(grantNumber.days.v().equals(0.0)){
			return new LeaveUsedPercent(new BigDecimal(0));
		}
		return new LeaveUsedPercent(new BigDecimal(new DecimalFormat("#.#").format((usedNumber.days.v() / grantNumber.days.v())*100.0)));
	}

}
