package nts.uk.ctx.at.schedule.infra.entity.shift.businesscalendar.businesscalendar;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KCCMT_COMPANY_START_DAY")
public class KccmtCompanyStartDay extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KccmtCompanyStartDayPK kccmtCompanyStartDayPK;

	@Column(name = "START_DAY")
	public int startDay;

	@Override
	protected Object getKey() {
		return kccmtCompanyStartDayPK;
	}
}
