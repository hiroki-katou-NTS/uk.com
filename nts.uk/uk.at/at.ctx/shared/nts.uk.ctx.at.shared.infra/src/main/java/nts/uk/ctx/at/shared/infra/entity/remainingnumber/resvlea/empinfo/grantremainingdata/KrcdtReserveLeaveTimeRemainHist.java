package nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 *
 * @author phongtq
 * 積立年休付付与時点残数履歴データ
 */

@NoArgsConstructor
@Entity

@Table(name = "KRCDT_HDSTK_REM_HIST_GRA")
public class KrcdtReserveLeaveTimeRemainHist extends ContractCompanyUkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtReserveLeaveTimeRemainHistPK krcdtReserveLeaveTimeRemainHist;

	@Column(name = "DEADLINE")
	public GeneralDate deadline;

	@Column(name = "EXP_STATUS")
	public int expStatus;

	@Column(name = "REGISTER_TYPE")
	public int registerType;

	@Column(name = "GRANT_DAYS")
	public double grantDays;

	@Column(name = "USED_DAYS")
	public double usedDays;

	@Column(name = "OVER_LIMIT_DAYS")
	@Basic(optional = true)
	public Double overLimitDays;

	@Column(name = "REMAINING_DAYS")
	public double remainingDays;

	public static KrcdtReserveLeaveTimeRemainHist fromDomain(ReserveLeaveGrantTimeRemainHistoryData domain) {
		
		return new KrcdtReserveLeaveTimeRemainHist(domain.getEmployeeId(), domain.getGrantProcessDate(),
				domain.getGrantDate(), domain.getDeadline(), domain.getExpirationStatus().value,
				domain.getRegisterType().value, domain.getDetails().getGrantNumber().getDays().v(),
				domain.getDetails().getUsedNumber().getDays().v(),
				domain.getDetails().getUsedNumber().getLeaveOverLimitNumber().isPresent()
						? domain.getDetails().getUsedNumber().getLeaveOverLimitNumber().get().numberOverDays.v() : null,
				domain.getDetails().getRemainingNumber().getDays().v());
	}

	public ReserveLeaveGrantTimeRemainHistoryData toDomain() {
		
		val data = ReserveLeaveGrantRemainingData.createFromJavaType("", krcdtReserveLeaveTimeRemainHist.sid, krcdtReserveLeaveTimeRemainHist.grantDate,
				deadline, expStatus, registerType, grantDays, usedDays, overLimitDays, remainingDays);
		
		return new ReserveLeaveGrantTimeRemainHistoryData(data, this.krcdtReserveLeaveTimeRemainHist.grantProcessDate);
	}

	public KrcdtReserveLeaveTimeRemainHist(String sid, GeneralDate grantProcessDate, GeneralDate grantDate,
			GeneralDate deadline, int expStatus, int registerType, double grantDays, double usedDays,
			Double overLimitDays, double remainingDays) {
		super();
		this.krcdtReserveLeaveTimeRemainHist = new KrcdtReserveLeaveTimeRemainHistPK(sid, grantProcessDate, grantDate);
		this.deadline = deadline;
		this.expStatus = expStatus;
		this.registerType = registerType;
		this.grantDays = grantDays;
		this.usedDays = usedDays;
		this.overLimitDays = overLimitDays;
		this.remainingDays = remainingDays;
	}

	@Override
	protected Object getKey() {
		return this.krcdtReserveLeaveTimeRemainHist;
	}
}
