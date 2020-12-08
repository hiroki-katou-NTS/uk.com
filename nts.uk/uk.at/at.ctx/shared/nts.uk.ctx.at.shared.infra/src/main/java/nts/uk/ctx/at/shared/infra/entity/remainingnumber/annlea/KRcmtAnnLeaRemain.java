package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_ANNLEA_REMAIN")
public class KRcmtAnnLeaRemain extends ContractUkJpaEntity{

	@Id
	@Column(name = "ANNLEAV_ID")
    public String annLeavID;

	@Column(name = "CID")
    public String cid;
	
	@Column(name = "SID")
    public String sid;
	
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
	
	@Column(name = "GRANT_MINUTES")
    public Integer grantMinutes;
	
	@Column(name = "USED_DAYS")
    public double usedDays;
	
	@Column(name = "USED_MINUTES")
    public Integer usedMinutes;
	
	@Column(name = "STOWAGE_DAYS")
    public Double stowageDays;

	@Column(name = "REMAINING_DAYS")
    public double remainingDays;
	
	@Column(name = "REMAINING_MINUTES")
    public Integer remaningMinutes;
	
	@Column(name = "USED_PERCENT")
    public double usedPercent;

	@Column(name = "PRESCRIBED_DAYS")
    public Double perscribedDays;
	
	@Column(name = "DEDUCTED_DAYS")
    public Double deductedDays;
	
	@Column(name = "WORKING_DAYS")
    public Double workingDays;
	
	@Override
	protected Object getKey() {
		return annLeavID;
	}

}
