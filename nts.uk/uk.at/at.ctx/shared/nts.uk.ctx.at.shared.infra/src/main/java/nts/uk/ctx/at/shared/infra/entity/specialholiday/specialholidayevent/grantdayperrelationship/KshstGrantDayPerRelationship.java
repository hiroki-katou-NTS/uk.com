package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.grantdayperrelationship;

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
@Table(name = "KSHST_GRANT_DAY_PER_RELP")
// 続柄毎の上限日数
public class KshstGrantDayPerRelationship extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KshstGrantDayPerRelationshipPK pk;

	/* 忌引とする */
	@Column(name = "MAKE_INVITATION")
	public int makeInvitation;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
