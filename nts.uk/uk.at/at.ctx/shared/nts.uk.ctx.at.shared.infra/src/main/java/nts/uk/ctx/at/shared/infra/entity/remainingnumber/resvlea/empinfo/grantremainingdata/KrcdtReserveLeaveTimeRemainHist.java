package nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT - 積立年休付付与時点残数履歴データ
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_RVSLEA_TIME_RM_HIST")
public class KrcdtReserveLeaveTimeRemainHist extends UkJpaEntity {

	@Id
	@Column(name = "RVSLEA_ID")
	public String rvsLeaId;

	@Column(name = "SID")
	public String sid;

	@Column(name = "CID")
	public String cid;

	@Column(name = "GRANT_DATE")
	public GeneralDate grantDate;

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

	// 付与処理日
	@Column(name = "GRANT_PROC_DATE")
	public GeneralDate grantProcessDate;

	public static KrcdtReserveLeaveTimeRemainHist fromDomain(ReserveLeaveGrantTimeRemainHistoryData domain,
			String cid) {
		return new KrcdtReserveLeaveTimeRemainHist(domain.getRsvLeaID(), domain.getEmployeeId(), cid,
				domain.getGrantDate(), domain.getDeadline(), domain.getExpirationStatus().value,
				domain.getRegisterType().value, domain.getDetails().getGrantNumber().v(),
				domain.getDetails().getUsedNumber().getDays().v(),
				domain.getDetails().getUsedNumber().getOverLimitDays().isPresent()
						? domain.getDetails().getUsedNumber().getOverLimitDays().get().v() : null,
				domain.getDetails().getRemainingNumber().v(), domain.getGrantProcessDate());
	}

	public ReserveLeaveGrantTimeRemainHistoryData toDomain() {
		return new ReserveLeaveGrantTimeRemainHistoryData(this.rvsLeaId, this.sid, this.grantDate, this.deadline,
				this.expStatus, this.registerType, this.grantDays, this.usedDays, this.overLimitDays,
				this.remainingDays, this.grantProcessDate);
	}

	public KrcdtReserveLeaveTimeRemainHist(String rvsLeaId, String sid, String cid, GeneralDate grantDate,
			GeneralDate deadline, int expStatus, int registerType, double grantDays, double usedDays,
			Double overLimitDays, double remainingDays, GeneralDate grantProcessDate) {
		super();
		this.rvsLeaId = rvsLeaId;
		this.sid = sid;
		this.cid = cid;
		this.grantDate = grantDate;
		this.deadline = deadline;
		this.expStatus = expStatus;
		this.registerType = registerType;
		this.grantDays = grantDays;
		this.usedDays = usedDays;
		this.overLimitDays = overLimitDays;
		this.remainingDays = remainingDays;
		this.grantProcessDate = grantProcessDate;
	}

	@Override
	protected Object getKey() {
		return this.rvsLeaId;
	}
}
