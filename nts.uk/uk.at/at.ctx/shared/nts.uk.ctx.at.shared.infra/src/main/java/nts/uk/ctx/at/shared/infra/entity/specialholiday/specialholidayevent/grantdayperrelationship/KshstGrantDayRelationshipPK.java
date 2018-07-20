package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.grantdayperrelationship;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshstGrantDayRelationshipPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 続柄毎の上限日数ID */
	@Column(name = "GRANT_DAY_PER_RELP_ID")
	private String grantDayPerRelpId;

	/* 続柄コード */
	@Column(name = "RELATIONSHIP_CD")
	public String relationshipCd;

}
