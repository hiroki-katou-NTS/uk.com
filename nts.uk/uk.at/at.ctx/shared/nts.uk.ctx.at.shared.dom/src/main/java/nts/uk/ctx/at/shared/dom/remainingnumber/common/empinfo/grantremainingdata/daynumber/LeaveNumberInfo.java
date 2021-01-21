package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 休暇数情報（明細）
 * @author masaaki_jinno
 *
 */
@Getter
@AllArgsConstructor
public class LeaveNumberInfo {

	/**
	 * 付与数
	 */
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
	public LeaveNumberInfo(){
		grantNumber = new LeaveGrantNumber();
		usedNumber = new LeaveUsedNumber();
		remainingNumber = new LeaveRemainingNumber();
		usedPercent = new LeaveUsedPercent(new BigDecimal(0.0));
	}
	
	/**
	 * すべて使用する
	 */
	public void digestAll()
	{
		// 使用数← 付与数
		
		// 日数
		usedNumber.days = new LeaveUsedDayNumber(grantNumber.getDays().v());
		
		// 時間
		if ( grantNumber.getMinutes().isPresent() ){
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
	 * @param leaveRemainingNumber 休暇残数
	 */
	public void setRemainingNumber(LeaveRemainingNumber leaveRemainingNumber){
		
		// 残数をセット
		remainingNumber.setDays(new LeaveRemainingDayNumber(leaveRemainingNumber.getDays().v()));
		if ( leaveRemainingNumber.getMinutes().isPresent() ){
			remainingNumber.setMinutes(Optional.of(new LeaveRemainingTime(leaveRemainingNumber.getMinutes().get().v())));
		} else {
			remainingNumber.setMinutes(Optional.of(new LeaveRemainingTime(0)));
		}
		
		// 使用数をセット（使用数←付与数-残数）
		usedNumber.days = new LeaveUsedDayNumber(grantNumber.getDays().v() - remainingNumber.getDays().v());
		if ( grantNumber.getMinutes().isPresent() && remainingNumber.getMinutes().isPresent() ){
			int used = grantNumber.getMinutes().get().v() - remainingNumber.getMinutes().get().v();
			usedNumber.setMinutes(Optional.of(new LeaveUsedTime(used)));
		}
	}

//	public LeaveNumberInfo(){
//		this.grantNumber = LeaveGrantNumber.createFromJavaType(0.0, null);
//		this.usedNumber = LeaveUsedNumber.createFromJavaType(0.0, null, null);
//		this.remainingNumber = LeaveRemainingNumber.createFromJavaType(0.0, null);
//		this.usedPercent = new LeaveUsedPercent(new BigDecimal(0));
//	}
	
	public LeaveNumberInfo(double grantDays, Integer grantMinutes, double usedDays, Integer usedMinutes,
			Double stowageDays, double remainDays, Integer remainMinutes, double usedPercent) {
//		this.grantNumber = LeaveGrantNumber.createFromJavaType(grantDays, grantMinutes);
//		this.usedNumber = LeaveUsedNumber.createFromJavaType(usedDays, usedMinutes, stowageDays);
//		this.remainingNumber = LeaveRemainingNumber.createFromJavaType(remainDays, remainMinutes);
//		this.usedPercent = new LeaveUsedPercent(new BigDecimal(0));
//		if (grantDays != 0){
//			String usedPer = new DecimalFormat("#.#").format(usedDays/grantDays);
//			this.usedPercent = new LeaveUsedPercent(new BigDecimal(usedPer));
//		}
	}
}
