package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KMLST_PREMIUM_SET")
public class KmlstPremiumSet extends UkJpaEntity{
	@EmbeddedId
	public KmlspPremiumSetPK kmlspPremiumSet;
	
	@Column(name="PREMIUM_RATE")
	public int premiumRate;
	
	@ManyToOne(targetEntity = KmlstExtraTime.class)
	@PrimaryKeyJoinColumns(value = {
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"),
		@PrimaryKeyJoinColumn(name="PREMIUM_CD",referencedColumnName="EXTRA_TIME_ID")
    })
	public KmlstExtraTime extraTime;
	
	@OneToMany
	public List<KmldtPremiumAttendance> premiumAttendances;
	
	@Override
	protected Object getKey() {
		return kmlspPremiumSet;
	}
}
