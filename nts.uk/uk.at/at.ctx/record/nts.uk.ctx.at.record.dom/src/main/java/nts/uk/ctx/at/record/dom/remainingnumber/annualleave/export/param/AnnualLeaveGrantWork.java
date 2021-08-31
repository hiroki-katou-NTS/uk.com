package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.GrantPeriodAtr;
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
	private GrantPeriodAtr grantPeriodAtr;

	/** 年休付与 */
	private Optional<NextAnnualLeaveGrant> annualLeaveGrant;

	/**
	 * コンストラクタ
	 */
	public AnnualLeaveGrantWork(){
		grantAtr = false;
		grantPeriodAtr = GrantPeriodAtr.BEFORE_GRANT;
		annualLeaveGrant = Optional.empty();
	}

}
