/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.setting.matrix.matrixdisplayset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hieult
 *
 */
@Entity
@Table(name = "PPEST_MATRIX_DISPLAY_SET")
@NoArgsConstructor
@AllArgsConstructor
public class PpestMatrixDisplaySet extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpestMatrixDisplaySetPK ppestMatrixDisplaySetPK;

	@Column(name = "CURSOR_DIRECTION")
	public int cursonDirection;

	@Column(name = "CLS_ATR")
	public int clsATR;

	@Column(name = "JOB_ATR")
	public int jobATR;

	@Column(name = "WKP_ATR")
	public int workPlaceATR;

	@Column(name = "DEP_ATR")
	public int departmentATR;

	@Column(name = "EMP_ATR")
	public int employmentATR;

	@Override
	protected Object getKey() {
		return ppestMatrixDisplaySetPK;
	}

	public MatrixDisplaySetting toDomain() {
		return MatrixDisplaySetting.createFromJavaType(this.ppestMatrixDisplaySetPK.companyID,
				this.ppestMatrixDisplaySetPK.userID, cursonDirection, clsATR, jobATR, workPlaceATR, departmentATR,
				employmentATR);
	}

	public static PpestMatrixDisplaySet toEntity(MatrixDisplaySetting setting) {
		return new PpestMatrixDisplaySet(new PpestMatrixDisplaySetPK(setting.getCompanyID(), setting.getUserID()),
				setting.getCursorDirection().value, setting.getClsATR().value, setting.getJobATR().value,
				setting.getWorkPlaceATR().value, setting.getDepartmentATR().value, setting.getEmploymentATR().value);
	}

}
