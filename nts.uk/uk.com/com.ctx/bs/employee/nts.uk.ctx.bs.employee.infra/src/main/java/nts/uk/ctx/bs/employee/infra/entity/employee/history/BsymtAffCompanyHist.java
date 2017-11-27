package nts.uk.ctx.bs.employee.infra.entity.employee.history;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BSYMT_AFF_COM_INFO")
public class BsymtAffCompanyHist extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public BsymtAffCompanyHistPk bsymtAffCompanyHistPk;

	@Column(name = "DESTINATION_DATA")
	public int destinationData;

	@Column(name = "START_DATE")
	public GeneralDate startDate;

	@Column(name = "END_DATE")
	public GeneralDate endDate;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = BsymtAffCompanyInfo.class, optional = false, cascade = CascadeType.REMOVE)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID") })
	public BsymtAffCompanyInfo bsymtAffCompanyInfo;

	@Override
	protected Object getKey() {
		return bsymtAffCompanyHistPk;
	}
}
