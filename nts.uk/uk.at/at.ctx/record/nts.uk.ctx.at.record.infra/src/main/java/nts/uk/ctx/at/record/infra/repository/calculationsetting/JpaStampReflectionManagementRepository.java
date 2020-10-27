package nts.uk.ctx.at.record.infra.repository.calculationsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.calculationsetting.KrcmtStampMng;
import nts.uk.ctx.at.record.infra.entity.calculationsetting.KrcmtStampMngPK;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.calculationsetting.repository.StampReflectionManagementRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaStampReflectionManagementRepository.
 *
 * @author phongtq
 */

@Stateless
public class JpaStampReflectionManagementRepository extends JpaRepository
		implements StampReflectionManagementRepository {

	/** The Constant FIND. */
	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcmtStampMng a ");
		builderString.append("WHERE a.krcdtStampReflectPK.companyId = :companyId ");
		FIND = builderString.toString();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository#findByCid(java.lang.String)
	 */
	@Override
	public Optional<StampReflectionManagement> findByCid(String companyId) {
		return this.queryProxy().query(FIND, KrcmtStampMng.class).setParameter("companyId", companyId)
				.getSingle(f -> toDomain(f));
	}

	/**
	 * To domain.
	 *
	 * @param krcdtStampReflect the krcdt stamp reflect
	 * @return the stamp reflection management
	 */
	private static StampReflectionManagement toDomain(KrcmtStampMng krcdtStampReflect) {
		StampReflectionManagement stampReflectionManagement = StampReflectionManagement.createJavaType(
				krcdtStampReflect.krcdtStampReflectPK.companyId, krcdtStampReflect.breakSwitchClass,
				krcdtStampReflect.autoStampReflectionClass,
				krcdtStampReflect.actualStampOfPriorityClass,
				krcdtStampReflect.reflectWorkingTimeClass,
				krcdtStampReflect.goBackOutCorrectionClass,
				krcdtStampReflect.autoStampForFutureDayClass
				);
		return stampReflectionManagement;
	}
	

	/* (non-Javadoc)
 * @see nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository#update(nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement)
 */
@Override
	public void update(StampReflectionManagement reflectionManagement) {
		KrcmtStampMngPK stampImprintPK = new KrcmtStampMngPK(reflectionManagement.getCompanyId());
		KrcmtStampMng imprint = this.queryProxy().find(stampImprintPK, KrcmtStampMng.class).get();
		imprint.breakSwitchClass = reflectionManagement.getBreakSwitchClass().value;
		imprint.autoStampReflectionClass = reflectionManagement.getAutoStampReflectionClass().value;
		imprint.actualStampOfPriorityClass= reflectionManagement.getActualStampOfPriorityClass().value;
		imprint.reflectWorkingTimeClass= reflectionManagement.getReflectWorkingTimeClass().value;
		imprint.goBackOutCorrectionClass= reflectionManagement.getGoBackOutCorrectionClass().value;
		imprint.autoStampForFutureDayClass= reflectionManagement.getAutoStampForFutureDayClass().value;
		this.commandProxy().update(imprint);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository#add(nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement)
	 */
	@Override
	public void add(StampReflectionManagement reflectionManagement) {
		this.commandProxy().insert(convertToDbType(reflectionManagement));
		
	}
		
		/**
		 * Convert to db type.
		 *
		 * @param atten the atten
		 * @return the krcmt stamp imprint
		 */
		private KrcmtStampMng convertToDbType(StampReflectionManagement atten) {
		KrcmtStampMng newEntity = KrcmtStampMng.toEntity(atten);
		KrcmtStampMngPK attSetPK = new KrcmtStampMngPK(atten.getCompanyId());
		Optional<KrcmtStampMng> optUpdateEntity = this.queryProxy().find(attSetPK, KrcmtStampMng.class);
		if (optUpdateEntity.isPresent()) {
			KrcmtStampMng updateEntity = optUpdateEntity.get();
			updateEntity.breakSwitchClass = atten.getBreakSwitchClass().value;
			updateEntity.autoStampReflectionClass = atten.getAutoStampReflectionClass().value;
			updateEntity.actualStampOfPriorityClass = atten.getActualStampOfPriorityClass().value;
			updateEntity.reflectWorkingTimeClass = atten.getReflectWorkingTimeClass().value;
			updateEntity.goBackOutCorrectionClass = atten.getGoBackOutCorrectionClass().value;
			updateEntity.autoStampForFutureDayClass = atten.getAutoStampForFutureDayClass().value;
			
			updateEntity.krcdtStampReflectPK = attSetPK;
			return updateEntity;
		}
		return  newEntity;
	}
}
