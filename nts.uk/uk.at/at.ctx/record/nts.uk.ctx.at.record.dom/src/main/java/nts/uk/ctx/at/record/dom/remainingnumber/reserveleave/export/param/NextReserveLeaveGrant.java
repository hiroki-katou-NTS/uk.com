package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedPercent;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveGrantDayNumber;

/**
 * 次回積立年休付与
 * @author shuichu_ishida
 */
@Getter
public class NextReserveLeaveGrant {

	/** 付与年月日 */
	private GeneralDate grantYmd;
	/** 付与日数 */
	private LeaveGrantDayNumber grantDays;
	/** 使用期限日 */
	private GeneralDate deadline;
	
	/**
	 * コンストラクタ
	 */
	public NextReserveLeaveGrant(){
		
		this.grantYmd = GeneralDate.today();
		this.grantDays = new LeaveGrantDayNumber(0.0);
		this.deadline = GeneralDate.max();
	}
	
	/**
	 * ファクトリー
	 * @param grantYmd 付与年月日
	 * @param grantDays 付与日数
	 * @param deadline 使用期限日
	 * @return 次回積立年休付与
	 */
	public static NextReserveLeaveGrant of(
			GeneralDate grantYmd, LeaveGrantDayNumber grantDays, GeneralDate deadline){
		
		NextReserveLeaveGrant domain = new NextReserveLeaveGrant();
		domain.grantYmd = grantYmd;
		domain.grantDays = grantDays;
		domain.deadline = deadline;
		return domain;
	}
	
	/**
	 * 積立年休付与残数データを作成
	 * @param employeeId
	 * @return
	 */
	public ReserveLeaveGrantRemainingData toReserveLeaveGrantRemainingData(String employeeId) {
		return new ReserveLeaveGrantRemainingData(employeeId, this.getGrantYmd(),
				this.getDeadline(), LeaveExpirationStatus.AVAILABLE, GrantRemainRegisterType.MONTH_CLOSE,
				this.toLeaveNumberInfo());
	}
	
	/**
	 * 休暇数情報作成
	 * @return
	 */
	private LeaveNumberInfo toLeaveNumberInfo() {
		return new LeaveNumberInfo(this.getGrantDays().toLeaveGrantNumber(), new LeaveUsedNumber(),
				this.getGrantDays().toLeaveRemainingNumber(), new LeaveUsedPercent(new BigDecimal(0)));
	}
	

	/**
	 * 積立年休付与情報を作成
	 * @param grantInfo
	 * @return
	 */
	public Optional<ReserveLeaveGrantInfo> toReserveLeaveGrantInfo(Optional<ReserveLeaveGrantInfo> grantInfo){
		double infoDays = 0.0;
		if (grantInfo.isPresent()) infoDays = grantInfo.get().getGrantDays().v();
		return Optional.of(ReserveLeaveGrantInfo.of(
				new ReserveLeaveGrantDayNumber(infoDays + this.grantDays.v())));
	}

}
