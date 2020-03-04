package nts.uk.ctx.hr.shared.infra.entity.humanresourceevaluation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author anhdt
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PPEDT_PER_ASSESSMENT_ITEM")
public class PpedtPerAssessmentItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpedtPerAssessmentItemPk ppedtPerAssessmentItemPk;

	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyID;

	/**
	 * 社員ID
	 */
	@Column(name = "SID")
	public String employeeID;

	/**
	 * 総合評価
	 */
	@Column(name = "VAL")
	public String comprehensiveEvaluation;

	@Override
	protected Object getKey() {
		return ppedtPerAssessmentItemPk;
	}
}
