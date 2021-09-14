package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.GrantPeriodAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;

/**
 * 付与情報WORK
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class ReserveLeaveGrantWork {

	/** 期間の開始日に付与があるか */
	private boolean grantAtr;
	/** 付与回数 */
	private int grantNumber = 0;
	/** 上限日数 */
	private MaxDaysRetention maxDays;
	/** 付与前か付与後か */
	private GrantPeriodAtr grantPeriodAtr;
	/** 積立年休付与 */
	private Optional<NextReserveLeaveGrant> reserveLeaveGrant;

	/**
	 * コンストラクタ
	 */
	public ReserveLeaveGrantWork(){
		grantAtr = false;
		reserveLeaveGrant = Optional.empty();
		maxDays = new MaxDaysRetention(0);
		grantPeriodAtr = GrantPeriodAtr.BEFORE_GRANT;
		reserveLeaveGrant = Optional.empty();
	}
	
	static public ReserveLeaveGrantWork of(
			boolean grantAtr,
			int grantNumber,
			MaxDaysRetention maxDays,
			GrantPeriodAtr grantPeriodAtr,
			Optional<NextReserveLeaveGrant> reserveLeaveGrant){
		
		ReserveLeaveGrantWork domain = new ReserveLeaveGrantWork();
		domain.grantAtr = grantAtr;
		domain.grantNumber = grantNumber;
		domain.maxDays = maxDays;
		domain.grantPeriodAtr = grantPeriodAtr;
		domain.reserveLeaveGrant = reserveLeaveGrant;
		
		return domain;
	}

	/**
	 * 初回付与かどうか
	 * @return 初回付与のときはTrueを返す
	 */
	public boolean isFirstGrant() {

		// 期間の開始日に付与があるか
		if(this.grantAtr && reserveLeaveGrant.isPresent()) {
			return grantNumber==1;
		}
		return false;
	}
	
	/**
	 * 付与前か付与後を判断する
	 * @return
	 */
	public GrantPeriodAtr judgeGrantPeriodAtr(){
		return grantPeriodAtr;
	}
}
