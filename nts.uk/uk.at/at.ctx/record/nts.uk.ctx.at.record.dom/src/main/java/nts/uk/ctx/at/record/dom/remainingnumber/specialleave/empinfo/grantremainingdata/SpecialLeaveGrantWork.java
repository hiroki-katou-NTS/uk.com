package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;

/**
 * 付与情報WORK
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class SpecialLeaveGrantWork {

	/** 付与回数 */
	private int grantNumber = 0;

	/** 期間の開始日に付与があるか */
	private boolean grantAtr;

	/** 特別休暇付与 */
	private Optional<NextSpecialLeaveGrant> specialLeaveGrant;

	/**
	 * コンストラクタ
	 */
	public SpecialLeaveGrantWork(){
		grantAtr = false;
		specialLeaveGrant = Optional.empty();
	}

}
