package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_GRANT_PERIOD")
public class KshmtHdspGrantPeriod extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshmtHdspGrantPeriodPK pk;

	/* 付与日数 */
	@Column(name = "GRANTED_DAYS")
	public int grantDays;

	/*期間開始日*/
	@Column(name = "PERIOD_START")
	public GeneralDate periodStart;

	/* 期間終了日 */
	@Column(name = "PERIOD_END")
	public GeneralDate periodEnd;


	@Override
	protected Object getKey() {
		return pk;
	}
	
	public KshmtHdspGrantPeriod(
				KshmtHdspGrantPeriodPK pk,
				int grantDays,
				GeneralDate periodStart,
				GeneralDate periodEnd ) {
		this.pk = pk;
		this.grantDays = grantDays;
		this.periodStart = periodStart;
		this.periodEnd = periodEnd;
	}
}
