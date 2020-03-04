package nts.uk.ctx.hr.shared.infra.entity.stresscheck;

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
@Table(name = "PPEDT_STRESS_ITEM")
public class PpedtStressItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpedtStressItemPk ppedtStressItemPk;

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
	public String overallResult;

	@Override
	protected Object getKey() {
		return ppedtStressItemPk;
	}
}
