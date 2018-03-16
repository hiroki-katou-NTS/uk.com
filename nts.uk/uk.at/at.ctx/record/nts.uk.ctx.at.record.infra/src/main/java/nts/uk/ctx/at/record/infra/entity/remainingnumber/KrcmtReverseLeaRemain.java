package nts.uk.ctx.at.record.infra.entity.remainingnumber;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Table(name = "KRCMT_RVSLEA_REMAIN")
public class KrcmtReverseLeaRemain extends UkJpaEntity {
	
	@Embedded
	public KrcmtReverseLeaRemainPK key;

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
	public Double overLimitDays;
	
	@Column(name = "REMAINING_DAYS")
	public double remainingDays;

	@Override
	protected Object getKey() {
		return key;
	}

}
