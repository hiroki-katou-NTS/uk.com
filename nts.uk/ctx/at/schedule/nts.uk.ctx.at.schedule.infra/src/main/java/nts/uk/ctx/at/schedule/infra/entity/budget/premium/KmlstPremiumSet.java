package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	public BigDecimal premiumRate;
	

	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"), 
		@PrimaryKeyJoinColumn(name="HIS_ID",referencedColumnName="HIS_ID")
	})
	private KmlmtPersonCostCalculation personCost;
	
	@ManyToOne
	@PrimaryKeyJoinColumns(value = {
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"),
		@PrimaryKeyJoinColumn(name="PREMIUM_CD",referencedColumnName="EXTRA_TIME_ID")
    })
	public KmlstExtraTime extraTime;
	
	@OneToMany(targetEntity = KmldtPremiumAttendance.class, cascade = CascadeType.ALL, mappedBy = "premiumSet")
	@JoinTable(name = "KMLDT_PREMIUM_ATTENDANCE")
	/*@JoinTable(
	        name="CUST_PHONE",
	        joinColumns=
	            @JoinColumn(name="CUST_ID", referencedColumnName="ID"),
	        inverseJoinColumns=
	            @JoinColumn(name="PHONE_ID", referencedColumnName="ID")
	    )*/
	public List<KmldtPremiumAttendance> premiumAttendances;
	
	@Override
	protected Object getKey() {
		return kmlspPremiumSet;
	}
}
