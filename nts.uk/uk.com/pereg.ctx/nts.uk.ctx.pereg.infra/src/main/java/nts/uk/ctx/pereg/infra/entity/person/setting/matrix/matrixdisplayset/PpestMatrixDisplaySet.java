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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hieult
 *
 */
@Entity
@Table(name = "PPEST_MATRIX_DISPLAY_SET")
@NoArgsConstructor
@AllArgsConstructor
public class PpestMatrixDisplaySet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public PpestMatrixDisplaySetPK ppestMatrixDisplaySetPK;
	
		 @Column(name = "CURSOR_DIRECTION")
		    public int cursonDirection;
		 
		 @Column(name = "CLS_ATR")
		    public int clsATR;
		 
		 @Column(name = "POSITION_ATR")
		    public int positionATR;
		 
		 @Column(name = "WORK_PLACE_ATR")
		    public int workPlaceATR;
		 
		 @Column(name = "DEPARTMENT_ATR")
		    public int departmentATR;
		 
		 @Column(name = "EMPLOYMENT_ATR")
		    public int employmentATR;
	 

	@Override
	protected Object getKey() {
		return ppestMatrixDisplaySetPK;
	}

	public MatrixDisplaySetting toDomain(){
		return MatrixDisplaySetting.createFromJavaType(
				this.ppestMatrixDisplaySetPK.companyID, 
				this.ppestMatrixDisplaySetPK.userID, 
				cursonDirection, 
				clsATR, 
				positionATR, 
				workPlaceATR, 
				departmentATR, 
				employmentATR);
	}
	
	public static PpestMatrixDisplaySet toEntity(MatrixDisplaySetting setting){
		return new PpestMatrixDisplaySet(
				new PpestMatrixDisplaySetPK(setting.getCompanyID() , setting.getUserID()),
				setting.getCursorDirection().value,
				setting.getClsATR().value,
				setting.getPositionATR().value,
				setting.getWorkPlaceATR().value,
				setting.getDepartmentATR().value,
				setting.getEmploymentATR().value);
	}

}
