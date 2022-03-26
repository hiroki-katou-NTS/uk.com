package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.TypeTime;


/**
 * 付与情報WORK
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveGrantWork {

	/** 期間の開始日に付与があるか */
	private boolean grantAtr;

	/** 付与するタイミングの種類 */
	private Optional<TypeTime> typeTime;

	/** 次回特別休暇付与 */
	private Optional<NextSpecialLeaveGrant> specialLeaveGrant;

	/**
	 * コンストラクタ
	 */
	public SpecialLeaveGrantWork(){
		grantAtr = false;
		typeTime = Optional.empty();
		specialLeaveGrant = Optional.empty();
	}

	/**
	 * 初回付与か判断
	 * @return 初回付与のときはTrueを返す
	 */
	public boolean isFirstGrant() {

		// 期間の開始日に付与があるか
		if(this.grantAtr==false) {
			return false;
		}

		if(!this.specialLeaveGrant.isPresent()) {
			return false;
		}

		// 初回付与のときはTrueを返す
		if ( specialLeaveGrant.isPresent() ) {
			return specialLeaveGrant.get().getTimes().v() == 1;
		}

		return false;
	}

}
