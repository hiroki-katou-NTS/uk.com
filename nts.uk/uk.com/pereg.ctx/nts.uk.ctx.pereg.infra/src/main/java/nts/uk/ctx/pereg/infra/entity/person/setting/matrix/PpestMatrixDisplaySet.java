/**
 * 
 */
package nts.uk.ctx.pereg.infra.entity.person.setting.matrix;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.matrix.MatrixDisplaySetting;
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
	 
	 @Column(name = "CLASSIFICATION")
	    public int classification;
	 
	 @Column(name = "POSITION")
	    public int position;
	 
	 @Column(name = "WORK_PLACE")
	    public int workPlace;
	 
	 @Column(name = "DEPARTMENT")
	    public int department;
	 
	 @Column(name = "EMPLOYMENT")
	    public int employment;
	 

	@Override
	protected Object getKey() {
		return ppestMatrixDisplaySetPK;
	}

	public MatrixDisplaySetting toDomain(){
		return MatrixDisplaySetting.createFromJavaType(
				this.ppestMatrixDisplaySetPK.companyID, 
				this.ppestMatrixDisplaySetPK.userID, 
				cursonDirection, 
				classification, 
				position, 
				workPlace, 
				department, 
				employment);
	}
	
	public static PpestMatrixDisplaySet toEntity(MatrixDisplaySetting setting){
		return new PpestMatrixDisplaySet(
				new PpestMatrixDisplaySetPK(setting.getCompanyID(), setting.getUserID()),
				setting.getCursorDirection().value,
				setting.getClassification().value,
				setting.getPosition().value,
				setting.getWorkPlace().value,
				setting.getDepartment().value,
				setting.getEmployment().value
				);
	}
	
}
