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
	@Column(name = "CID")
	public String companyId;

	/* 続柄コード */
	@Column(name = "RELATIONSHIP_CD")
	public String relationshipCd;

	/* 特別休暇枠NO */
	@Column(name = "S_HOLIDAY_EVENT_NO")
	public int sHolidayEventNo;

}
