package nts.uk.ctx.at.shared.infra.entity.remainingnumber.resvlea.empinfo.grantremainingdata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainHistoryData;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author HungTT - 積立年休付与残数履歴データ
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_HDSTK_REM_HIST")
public class KrcdtReserveLeaveRemainHist extends ContractUkJpaEntity implements Serializable{

	@EmbeddedId
	public KrcdtReserveLeaveRemainHistPK  krcdtReserveLeaveRemainHistPK;

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

	public static KrcdtReserveLeaveRemainHist fromDomain(ReserveLeaveGrantRemainHistoryData domain, String cid) {
		return new KrcdtReserveLeaveRemainHist( cid,domain.getEmployeeId(), domain.getYearMonth().v(), domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(), domain.getClosureDate().getLastDayOfMonth() ? 1 : 0,  domain.getGrantDate(),
				domain.getDeadline(), domain.getExpirationStatus().value, domain.getRegisterType().value,
				domain.getDetails().getGrantNumber().v(), domain.getDetails().getUsedNumber().getDays().v(),
				domain.getDetails().getUsedNumber().getOverLimitDays().isPresent()
						? domain.getDetails().getUsedNumber().getOverLimitDays().get().v() : null,
				domain.getDetails().getRemainingNumber().v());
	}

	public ReserveLeaveGrantRemainHistoryData toDomain() {
		return new ReserveLeaveGrantRemainHistoryData(
				this.krcdtReserveLeaveRemainHistPK.sid, new YearMonth(this.krcdtReserveLeaveRemainHistPK.yearMonth), this.krcdtReserveLeaveRemainHistPK.closureId,
				new ClosureDate(this.krcdtReserveLeaveRemainHistPK.closeDay, this.krcdtReserveLeaveRemainHistPK.isLastDay == 1), this.krcdtReserveLeaveRemainHistPK.grantDate, this.deadline,
				this.expStatus, this.registerType, this.grantDays, this.usedDays, this.overLimitDays,
				this.remainingDays);
	}

	public KrcdtReserveLeaveRemainHist(String cid, String sid, Integer yearMonth, Integer closureId, Integer closeDay,
			int isLastDay, GeneralDate grantDate,
			GeneralDate deadline, int expStatus, int registerType, double grantDays, double usedDays,
			Double overLimitDays, double remainingDays) {
		super();
		this.cid = cid;
		this.krcdtReserveLeaveRemainHistPK = new KrcdtReserveLeaveRemainHistPK(sid, yearMonth, closureId, closeDay, isLastDay, grantDate);
		this.deadline = deadline;
		this.expStatus = expStatus;
		this.registerType = registerType;
		this.grantDays = grantDays;
		this.usedDays = usedDays;
		this.overLimitDays = overLimitDays;
		this.remainingDays = remainingDays;

	}
	
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		return this.krcdtReserveLeaveRemainHistPK;
	}

}
