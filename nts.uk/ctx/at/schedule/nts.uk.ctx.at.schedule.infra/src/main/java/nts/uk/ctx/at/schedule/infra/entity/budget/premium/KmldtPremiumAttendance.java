package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
	
	/*@ManyToOne(targetEntity = KmlstPremiumSet.class)
	@JoinColumns(value = {
		    @JoinColumn(name="CID",referencedColumnName="CID"),
		    @JoinColumn(name="HID",referencedColumnName="HID"),
		    @JoinColumn(name="PREMIUM_CD",referencedColumnName="PREMIUM_CD"),
		})
	public KmlstPremiumSet kmlstPremiumSet;*/
	
	@Override
	protected Object getKey() {
		return premiumAttendancePK;
	}
}
