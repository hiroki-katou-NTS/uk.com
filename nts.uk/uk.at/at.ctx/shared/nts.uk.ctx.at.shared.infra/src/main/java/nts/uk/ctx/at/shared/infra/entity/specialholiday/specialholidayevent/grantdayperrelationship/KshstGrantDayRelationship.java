package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.grantdayperrelationship;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationship;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_DAY_RELP")
// 続柄に対する上限日数
public class KshstGrantDayRelationship extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KshstGrantDayRelationshipPK pk;

	/* 付与日数 */
	@Column(name = "GRANTED_DAY")
	public int grantedDay;

	/* 喪主時加算日数 */
	@Column(name = "MORNING_HOUR")
	public Integer morningHour;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KshstGrantDayRelationship updateData(GrantDayRelationship domain) {
		this.grantedDay = domain.getGrantedDay().v();
		this.morningHour = domain.getMorningHour().v();
		return this;
	}

}
