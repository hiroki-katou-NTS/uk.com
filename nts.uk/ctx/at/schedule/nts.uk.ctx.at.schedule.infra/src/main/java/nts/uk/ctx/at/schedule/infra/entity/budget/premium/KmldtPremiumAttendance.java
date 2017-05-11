package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KMLDT_PREMIUM_ATTENDANCE")
public class KmldtPremiumAttendance extends UkJpaEntity{
	@EmbeddedId
	public KmldpPremiumAttendancePK premiumAttendancePK;
	
	@Override
	protected Object getKey() {
		return premiumAttendancePK;
	}
}
