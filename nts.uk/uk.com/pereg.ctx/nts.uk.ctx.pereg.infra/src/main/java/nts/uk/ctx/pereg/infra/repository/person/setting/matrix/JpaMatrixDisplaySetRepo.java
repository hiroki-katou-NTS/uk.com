/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.setting.matrix;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.setting.matrix.MatrixDisplaySetting;
import nts.uk.ctx.pereg.dom.person.setting.matrix.MatrixDisplaySettingRepo;
import nts.uk.ctx.pereg.infra.entity.person.setting.matrix.PpestMatrixDisplaySet;

/**
 * @author hieult
 *
 */
@Stateless
public class JpaMatrixDisplaySetRepo extends JpaRepository implements MatrixDisplaySettingRepo{

	private static final String SELECT_BY_KEY = "SELECT  c FROM PpestMatrixDisplaySet c WHERE c.ppestMatrixDisplaySetPK.companyID = :companyID"  
					+ " WHERE c.ppestMatrixDisplaySetPK.userID = :userID";
			@Override
	public Optional<MatrixDisplaySetting> find(String companyID, String userID) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SELECT_BY_KEY , PpestMatrixDisplaySet.class)
			.setParameter("companyID", companyID)
			.setParameter("userID", userID)
			.getSingle(c -> c.toDomain());
	}
	
	@Override
	public void update(MatrixDisplaySetting newSetting) {
			PpestMatrixDisplaySet newEntity = PpestMatrixDisplaySet.toEntity(newSetting);
			PpestMatrixDisplaySet updateEntity = this.queryProxy().find(newEntity.ppestMatrixDisplaySetPK, PpestMatrixDisplaySet.class).get();
			updateEntity.cursonDirection = newEntity.cursonDirection;
			updateEntity.classification  = newEntity.classification;
			updateEntity.position        = newEntity.position;
			updateEntity.workPlace       = newEntity.workPlace;
			updateEntity.department      = newEntity.department;
			updateEntity.employment      = newEntity.employment;
			this.commandProxy().update(updateEntity);
		
	}

}
