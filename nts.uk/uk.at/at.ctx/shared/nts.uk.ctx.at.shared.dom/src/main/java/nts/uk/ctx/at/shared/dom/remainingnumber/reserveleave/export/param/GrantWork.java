package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.export.param;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

/**
 * 積立年休付与WORK
 * @author shuichu_ishida
 */
@Getter
public class GrantWork {

	/** 付与年月日 */
	private GeneralDate grantYmd;
	/** 付与日数 */
	private ReserveLeaveGrantDayNumber grantDays;
	
	/**
	 * コンストラクタ
	 */
	public GrantWork(){
		
		this.grantYmd = GeneralDate.today();
		this.grantDays = new ReserveLeaveGrantDayNumber(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param grantYmd 付与年月日
	 * @param grantDays 付与日数
	 * @return 積立年休付与WORK
	 */
	public static GrantWork of(
			GeneralDate grantYmd, ReserveLeaveGrantDayNumber grantDays){
		
		GrantWork domain = new GrantWork();
		domain.grantYmd = grantYmd;
		domain.grantDays = grantDays;
		return domain;
	}
	
	/**
	 * 端数処理
	 * @param annualLeaveSet 年休設定
	 */
	public void roundGrantDays(AnnualPaidLeaveSetting annualLeaveSet){

		// 設定事前チェック
		val manageAnnualSet = annualLeaveSet.getManageAnnualSetting();
		if (manageAnnualSet == null) return;
		val halfDayManage = manageAnnualSet.getHalfDayManage();
		if (halfDayManage == null) return;
		
		Double grantDays = this.grantDays.v();
		Integer intGrantDays = grantDays.intValue();
		
		// 半日年休管理．端数処理区分をチェック
		switch(halfDayManage.roundProcesCla){
		case TruncateOnDay0:
			if (grantDays != intGrantDays.doubleValue()) grantDays = intGrantDays.doubleValue();
			break;
		case RoundUpToTheDay:
			if (grantDays != intGrantDays.doubleValue()){
				if (grantDays > 0.0) grantDays = intGrantDays.doubleValue() + 1.0;
				if (grantDays < 0.0) grantDays = intGrantDays.doubleValue() - 1.0;
			}
			break;
		default:
			break;
		}
		
		// 付与日数と付与上限日数を比較
		if (manageAnnualSet.getMaxGrantDay() != null){
			if (grantDays > manageAnnualSet.getMaxGrantDay().v()){
				grantDays = manageAnnualSet.getMaxGrantDay().v();
			}
		}
		
		// 計算した付与日数を設定する
		this.grantDays = new ReserveLeaveGrantDayNumber(grantDays);
	}
}
