package nts.uk.ctx.at.shared.dom.remainingnumber.common;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;

/**
 * 休暇残数シフトWORK
 * @author masaaki_jinno
 */
@Getter
public class RemNumShiftWork {

	/**
	 * 休暇付与残数データへの参照
	 */
	@Setter
	private LeaveGrantRemainingData refLeaveGrantRemainingData;

	/**
	 * コンストラクタ
	 * @param aLeaveGrantRemainingData
	 */
	public RemNumShiftWork(LeaveGrantRemainingData aLeaveGrantRemainingData)
	{
		refLeaveGrantRemainingData = aLeaveGrantRemainingData;
	}

	/**
	 * 休暇残数を取得する
	 */
	public Optional<LeaveRemainingNumber> getLeaveRemainingNumber(){
		if ( refLeaveGrantRemainingData.getDetails() == null ){
			return Optional.empty();
		}

		return Optional.of(
				refLeaveGrantRemainingData.getDetails().getRemainingNumber());
	}


	public void calcUsedNumber(LeaveRemainingNumber.RequireM3 require,
			String companyId,
			String employeeId,
			GeneralDate baseDate) {
		LeaveGrantNumber grant = this.getRefLeaveGrantRemainingData().getDetails().getGrantNumber();
		LeaveRemainingNumber remain = this.getRefLeaveGrantRemainingData().getDetails().getRemainingNumber();

		//付与数から残数を減算したいので、残数←付与数、使用数←残数を入れて、減算メソッドを呼ぶ。
		LeaveRemainingNumber grantForCalc =
				new LeaveRemainingNumber(grant.getDays().v(), grant.getMinutesOrZero().v());
		LeaveUsedNumber remainForCalc =
				new LeaveUsedNumber(remain.getDays().v(), remain.getMinutesOrZero().v());

		//残数を計算する。
		grantForCalc.digestLeaveUsedNumber(require, remainForCalc, companyId, employeeId, baseDate);
		//使用数を取得
		LeaveUsedNumber userdNumber = new LeaveUsedNumber(grantForCalc.getDays().v(), grantForCalc.getMinutesOrZero().v());

		this.getRefLeaveGrantRemainingData().getDetails().setUsedNumber(userdNumber);

	}
	
	/**
	 * 休暇残数を全て消化する
	 */
	public void digestAll() {
		// 休暇残数をすべて消化する
		this.refLeaveGrantRemainingData.getDetails().digestAll();
	}
}
