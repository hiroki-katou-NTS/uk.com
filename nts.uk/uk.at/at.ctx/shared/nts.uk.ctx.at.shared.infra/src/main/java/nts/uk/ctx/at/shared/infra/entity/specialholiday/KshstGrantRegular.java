package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegular;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_REGULAR")
public class KshstGrantRegular extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstGrantRegularPK kshstGrantRegularPK;

	/* 付与開始日 */
	@Column(name = "GRANT_START_DATE")
	public GeneralDate grantStartDate;

	/* 月数 */
	@Column(name = "MONTHS")
	public Integer months;

	/* 年数 */
	@Column(name = "YEARS")
	public Integer years;

	/* 付与日定期方法 */
	@Column(name = "GRANT_REGULAR_METHOD")
	public int grantRegularMethod;

	@OneToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
	})
	public KshstSpecialHoliday specialHoliday;
	
	@OneToOne(cascade = CascadeType.REMOVE, mappedBy="grantRegularCom", orphanRemoval = false)
	public KshstGrantDateCom grantDateCom;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy="grantRegularPer", orphanRemoval = false)
	public List<KshstGrantDatePer> grantDatePer;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kshstGrantRegularPK;
	}

	public KshstGrantRegular(KshstGrantRegularPK kshstGrantRegularPK, GeneralDate grantStartDate, Integer months, Integer years,
			int grantRegularMethod) {
		super();
		this.kshstGrantRegularPK = kshstGrantRegularPK;
		this.grantStartDate = grantStartDate;
		this.months = months;
		this.years = years;
		this.grantRegularMethod = grantRegularMethod;
	}
	
	public static KshstGrantRegular toEntity(GrantRegular domain){
		return new KshstGrantRegular(new KshstGrantRegularPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v()), domain.getGrantStartDate(), 
				domain.getMonths() != null ? domain.getMonths().v() : null, 
				domain.getYears() != null ? domain.getYears().v() : null, 
				domain.getGrantRegularMethod().value);
	}
}
