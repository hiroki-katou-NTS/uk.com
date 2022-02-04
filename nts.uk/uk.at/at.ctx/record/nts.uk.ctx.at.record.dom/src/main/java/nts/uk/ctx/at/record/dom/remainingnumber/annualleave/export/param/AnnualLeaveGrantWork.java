package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import lombok.Getter;
import lombok.Setter;

/**
 * 付与情報WORK
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class AnnualLeaveGrantWork {

	/** 付与回数 */
	private int grantNumber = 0;

	/** 期間の開始日に付与があるか */
	private boolean grantAtr;

	/**付与前か付与後か */
	private GrantBeforeAfterAtr grantPeriodAtr;

	/** 年休付与 */
	private Optional<NextAnnualLeaveGrant> annualLeaveGrant;

	/**
	 * コンストラクタ
	 */
	public AnnualLeaveGrantWork(){
		grantAtr = false;
		grantPeriodAtr = GrantBeforeAfterAtr.BEFORE_GRANT;
		annualLeaveGrant = Optional.empty();
	}

	/**
	 * 初回付与かどうか
	 * @return 初回付与のときはTrueを返す
	 */
	public boolean isFirstGrant() {

		// 期間の開始日に付与があるか
		if(this.grantAtr) {
			return grantNumber==1;
		}

		return false;
	}
	
	/**
	 * 付与前か付与後を判断する
	 * @return
	 */
	public GrantBeforeAfterAtr judgeGrantPeriodAtr(){
		return grantPeriodAtr;
	}

}
