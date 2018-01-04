package nts.uk.ctx.at.shared.infra.entity.specialholiday.yearservicecom;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantPeriodic;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantRegular;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceset.KshstYearServiceSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_YEAR_SERVICE_COM")
/**
 * 
 * @author yennth
 *
 */
public class KshstYearServiceCom extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshstYearServiceComPK kshstYearServiceComPK;
	/** 勤続年数 **/
	@Column(name = "LENGTH_SERVICE_YEAR_ATR")
	public int lengthServiceYearAtr;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kshstYearServiceCom", orphanRemoval = true)
	public List<KshstYearServiceSet> listYearServiceSet;
	
	@OneToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
	})
public KshstGrantPeriodic grantPeriodic;
	@Override
	protected Object getKey() {
		return kshstYearServiceComPK;
	}

}
