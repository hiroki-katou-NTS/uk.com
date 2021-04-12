package nts.uk.ctx.at.record.infra.entity.worklocation;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkplacePossible;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author tutk
 *
 */
@Entity
@Table(name = "KRCMT_POSSIBLE_WKP")
@NoArgsConstructor
public class KrcmtWorkplacePossible extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtWorkplacePossiblePK krcmtWorkplacePossiblePK;

	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CONTRACT_CD", referencedColumnName = "CONTRACT_CD"),
			@PrimaryKeyJoinColumn(name = "WK_LOCATION_CD", referencedColumnName = "WK_LOCATION_CD") })
	public KrcmtWorkLocation krcmtWorkLocation;

	@Override
	protected Object getKey() {
		return krcmtWorkplacePossiblePK;
	}

	public KrcmtWorkplacePossible(KrcmtWorkplacePossiblePK krcmtWorkplacePossiblePK) {
		super();
		this.krcmtWorkplacePossiblePK = krcmtWorkplacePossiblePK;
	}

	public static KrcmtWorkplacePossible toEntiy(String contractCode, String workLocationCD,
			WorkplacePossible workplacePossible) {
		return new KrcmtWorkplacePossible(new KrcmtWorkplacePossiblePK(contractCode, workLocationCD,
				workplacePossible.getCompanyId(), workplacePossible.getWorkpalceId()));
	}

	public WorkplacePossible toDomain() {
		return new WorkplacePossible(this.krcmtWorkplacePossiblePK.cid, this.krcmtWorkplacePossiblePK.workplaceId);
	}

}
