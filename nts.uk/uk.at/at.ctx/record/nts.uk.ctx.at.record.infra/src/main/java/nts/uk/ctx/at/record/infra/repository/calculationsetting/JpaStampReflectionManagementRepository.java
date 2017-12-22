package nts.uk.ctx.at.record.infra.repository.calculationsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.record.infra.entity.calculationsetting.KrcdtStampImprint;

@Stateless
public class JpaStampReflectionManagementRepository extends JpaRepository
		implements StampReflectionManagementRepository {

	private static final String FIND;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtStampReflect a ");
		builderString.append("WHERE a.krcdtStampReflectPK.companyId = :companyId ");
		FIND = builderString.toString();
	}

	@Override
	public Optional<StampReflectionManagement> findByCid(String companyId) {
		return this.queryProxy().query(FIND, KrcdtStampImprint.class).setParameter("companyId", companyId)
				.getSingle(f -> toDomain(f));
	}

	private static StampReflectionManagement toDomain(KrcdtStampImprint krcdtStampReflect) {
		StampReflectionManagement stampReflectionManagement = StampReflectionManagement.createJavaType(
				krcdtStampReflect.krcdtStampReflectPK.companyId, krcdtStampReflect.breakSwitchClass.intValue(),
				krcdtStampReflect.autoStampReflectionClass.intValue(),
				krcdtStampReflect.actualStampOfPriorityClass.intValue(),
				krcdtStampReflect.reflectWorkingTimeClass.intValue(),
				krcdtStampReflect.goBackOutCorrectionClass.intValue(),
				krcdtStampReflect.managementOfEntrance.intValue(),
				krcdtStampReflect.autoStampForFutureDayClass.intValue());
		return stampReflectionManagement;
	}
}
