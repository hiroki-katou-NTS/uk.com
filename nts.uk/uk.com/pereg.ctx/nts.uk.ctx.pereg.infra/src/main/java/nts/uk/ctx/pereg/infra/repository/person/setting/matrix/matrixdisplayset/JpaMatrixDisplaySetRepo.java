/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.setting.matrix.matrixdisplayset;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySetting;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySettingRepo;
import nts.uk.ctx.pereg.infra.entity.person.setting.matrix.matrixdisplayset.PpestMatrixDisplaySet;
import nts.uk.ctx.pereg.infra.entity.person.setting.matrix.matrixdisplayset.PpestMatrixDisplaySetPK;

/**
 * @author hieult
 *
 */
@Stateless
public class JpaMatrixDisplaySetRepo extends JpaRepository implements MatrixDisplaySettingRepo {

	private static final String SELECT_BY_KEY = "SELECT c FROM PpestMatrixDisplaySet c WHERE c.ppestMatrixDisplaySetPK.companyID = :companyID"
			+ " AND c.ppestMatrixDisplaySetPK.userID = :userID";

	@Override
	public Optional<MatrixDisplaySetting> find(String companyID, String userID) {
		// TODO Auto-generated method stub
		return this.queryProxy().query(SELECT_BY_KEY, PpestMatrixDisplaySet.class).setParameter("companyID", companyID)
				.setParameter("userID", userID).getSingle(c -> c.toDomain());
	}

	@Override
	public void update(MatrixDisplaySetting newSetting) {
		PpestMatrixDisplaySet newEntity = PpestMatrixDisplaySet.toEntity(newSetting);
		
		PpestMatrixDisplaySet updateEntity = this.queryProxy()
				.find(newEntity.ppestMatrixDisplaySetPK, PpestMatrixDisplaySet.class).get();
		
		updateEntity.cursonDirection = newEntity.cursonDirection;
		updateEntity.clsATR = newEntity.clsATR;
		updateEntity.jobATR = newEntity.jobATR;
		updateEntity.workPlaceATR = newEntity.workPlaceATR;
		updateEntity.departmentATR = newEntity.departmentATR;
		updateEntity.employmentATR = newEntity.employmentATR;
		
		this.commandProxy().update(updateEntity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.
	 * MatrixDisplaySettingRepo#insert(nts.uk.ctx.pereg.dom.person.setting.matrix.
	 * matrixdisplayset.MatrixDisplaySetting)
	 */
	@Override
	public void insert(MatrixDisplaySetting newSetting) {
		PpestMatrixDisplaySet newEntity = PpestMatrixDisplaySet.toEntity(newSetting);
		
		Optional<PpestMatrixDisplaySet> updateEntity = this.queryProxy().find(newEntity.ppestMatrixDisplaySetPK,
				PpestMatrixDisplaySet.class);
		
		if (updateEntity.isPresent()) {
			this.update(newSetting);
		} else {
			this.commandProxy().insert(PpestMatrixDisplaySet.toEntity(newSetting));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.
	 * MatrixDisplaySettingRepo#insertAll(java.util.List)
	 */
	@Override
	public void insertAll(List<MatrixDisplaySetting> listSetting) {
		List<PpestMatrixDisplaySet> listEntity = listSetting.stream().map(c -> toEntity(c))
				.collect(Collectors.toList());
		
		commandProxy().insertAll(listEntity);
	}

	private PpestMatrixDisplaySet toEntity(MatrixDisplaySetting domain) {
		PpestMatrixDisplaySetPK pk = new PpestMatrixDisplaySetPK(domain.getCompanyID(), domain.getUserID());
		
		return new PpestMatrixDisplaySet(pk, domain.getCursorDirection().value, domain.getClsATR().value,
				domain.getJobATR().value, domain.getWorkPlaceATR().value, domain.getDepartmentATR().value,
				domain.getEmploymentATR().value);
	}
}
