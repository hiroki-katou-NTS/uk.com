package nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author phongtq
 * 積立年休付付与時点残数履歴データ
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_HDSTK_REM_HIST_GRA")
public class KrcdtReserveLeaveTimeRemainHist extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtReserveLeaveTimeRemainHistPK krcdtReserveLeaveTimeRemainHist;
	
	@Column(name = "CID")
	public String cid;

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

	public static KrcdtReserveLeaveTimeRemainHist fromDomain(ReserveLeaveGrantTimeRemainHistoryData domain,
			String cid) {
		return new KrcdtReserveLeaveTimeRemainHist(domain.getEmployeeId(), cid, domain.getGrantProcessDate(),
				domain.getGrantDate(), domain.getDeadline(), domain.getExpirationStatus().value,
				domain.getRegisterType().value, domain.getDetails().getGrantNumber().v(),
				domain.getDetails().getUsedNumber().getDays().v(),
				domain.getDetails().getUsedNumber().getOverLimitDays().isPresent()
						? domain.getDetails().getUsedNumber().getOverLimitDays().get().v() : null,
				domain.getDetails().getRemainingNumber().v());
	}

	public ReserveLeaveGrantTimeRemainHistoryData toDomain() {
		return new ReserveLeaveGrantTimeRemainHistoryData(this.krcdtReserveLeaveTimeRemainHist.sid,
				this.krcdtReserveLeaveTimeRemainHist.grantProcessDate, this.krcdtReserveLeaveTimeRemainHist.grantDate,
				this.deadline, this.expStatus, this.registerType, this.grantDays, this.usedDays, this.overLimitDays,
				this.remainingDays);
	}

	public KrcdtReserveLeaveTimeRemainHist(String sid, String cid, GeneralDate grantProcessDate, GeneralDate grantDate,
			GeneralDate deadline, int expStatus, int registerType, double grantDays, double usedDays,
			Double overLimitDays, double remainingDays) {
		super();
		this.krcdtReserveLeaveTimeRemainHist = new KrcdtReserveLeaveTimeRemainHistPK(sid, grantProcessDate, grantDate);
		this.cid = cid;
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
