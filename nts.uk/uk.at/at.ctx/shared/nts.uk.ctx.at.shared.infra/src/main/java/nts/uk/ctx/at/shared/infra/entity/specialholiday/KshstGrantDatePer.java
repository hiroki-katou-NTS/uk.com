package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_DATE_PER")
public class KshstGrantDatePer extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KshstGrantDatePerPK kshstGrantDatePerPK;
	
	/* 名称 */
	@Column(name = "PERSONAL_GRANT_DATE_NAME")
	public String personalGrantDateName;
	
	@Column(name = "PROVISION")
	public int provision;
	
	/* 一律基準日 */
	@Column(name = "GRANT_DATE")
	public GeneralDate grantDate;
	
	/* 付与基準日 */
	@Column(name = "GRANT_DATE_ATR")
	public int grantDateAtr;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="grantDatePer", orphanRemoval = true)
	public List<KshstGrantDatePerSet> grantDatePerSet;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
	})
public KshstGrantRegular grantRegularPer;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kshstGrantDatePerPK;
	}
}