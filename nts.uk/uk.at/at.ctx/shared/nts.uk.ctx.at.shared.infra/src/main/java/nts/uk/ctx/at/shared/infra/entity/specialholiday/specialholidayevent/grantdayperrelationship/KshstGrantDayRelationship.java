package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent.grantdayperrelationship;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_DAY_RELP")
// 続柄に対する上限日数
public class KshstGrantDayRelationship extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshstGrantDayRelationshipPK pk;
	
	/* 特別休暇枠NO */
	@Column(name = "S_HOLIDAY_EVENT_NO")
	public int sHolidayEventNo;
	
	/* 付与日数 */
	@Column(name = "GRANTED_DAY")
	public int grantedDay;
	
	/* 喪主時加算日数 */
	@Column(name = "MORNING_HOUR")
	public int morningHour;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
