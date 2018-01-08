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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHolidayAdditionSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_DATE_COM")
public class KshstGrantDateCom extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstGrantDateComPK kshstGrantDateComPK;

	/* 付与基準日 */
	@Column(name = "GRANT_DATE_ATR")
	public int grantDateAtr;

	/* 一律基準日 */
	@Column(name = "GIANT_STANDARD_DATE")
	public GeneralDate grantDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="grantDateCom", orphanRemoval = true)
	public List<KshstGrantDateSet> grantDateSets;
	
	@OneToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
	})
public KshstGrantRegular grantRegularCom;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kshstGrantDateComPK;
	}
}
