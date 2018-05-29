package nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceper;

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
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantPeriodic;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_YEAR_SERVICE_PER")
public class KshstYearServicePer extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId 
	public KshstYearServicePerPK kshstYearServicePerPK;
	/**名称**/
	@Column(name = "YEAR_SERVICE_NAME")
	public String yearServiceName;
	@Column(name = "PROVISION")
	public int provision;
	@Column(name = "YEAR_SERVICE_CLS")
	public Integer yearServiceCls;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kshstYearServicePer", orphanRemoval = true)
	public List<KshstYearServicePerSet> listYearServicePerSet;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
	})
public KshstGrantPeriodic grantPeriodic;
	@Override
	protected Object getKey() {
		return kshstYearServicePerPK;
	}
	
}
