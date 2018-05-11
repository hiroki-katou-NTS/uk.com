package nts.uk.ctx.at.schedule.infra.repository.schedule.schedulemaster;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfoRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster.KscdtScheMasterInfo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster.KscdtScheMasterInfoPK;

@Stateless
public class JpaScheduleMasterInfoRepository extends JpaRepository implements ScheMasterInfoRepository {

	@Override
	public Optional<ScheMasterInfo> getScheMasterInfo(String sId, GeneralDate generalDate) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(sId, generalDate);

		return this.queryProxy().find(primaryKey, KscdtScheMasterInfo.class).map(x -> toDomain(x));
	}

	private static ScheMasterInfo toDomain(KscdtScheMasterInfo entity) {
		if (entity == null) {
			return null;
		}
		
		ScheMasterInfo domain = ScheMasterInfo.createFromJavaType(entity.kscdtScheMasterInfoPk.sId, entity.kscdtScheMasterInfoPk.generalDate,
				entity.employmentCd, entity.classificationCd, entity.businessTypeCd, entity.jobId, entity.workplaceId);
		
		return domain;
	}

	@Override
	public void addScheMasterInfo(ScheMasterInfo scheMasterInfo) {
		this.commandProxy().insert(convertToDbType(scheMasterInfo));
	}

	private KscdtScheMasterInfo convertToDbType(ScheMasterInfo scheMasterInfo) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(scheMasterInfo.getSId(), scheMasterInfo.getGeneralDate());
		
		return new KscdtScheMasterInfo(
				primaryKey,
				scheMasterInfo.getEmploymentCd(),
				scheMasterInfo.getClassificationCd(),
				scheMasterInfo.getBusinessTypeCd(),
				scheMasterInfo.getJobId(),
				scheMasterInfo.getWorkplaceId());
	}

	@Override
	public void updateScheMasterInfo(ScheMasterInfo scheMasterInfo) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(scheMasterInfo.getSId(), scheMasterInfo.getGeneralDate());		
		KscdtScheMasterInfo kscdtScheMasterInfo = this.queryProxy().find(primaryKey, KscdtScheMasterInfo.class).get();
		
		kscdtScheMasterInfo.employmentCd = scheMasterInfo.getEmploymentCd();
		kscdtScheMasterInfo.classificationCd = scheMasterInfo.getClassificationCd();
		kscdtScheMasterInfo.businessTypeCd = scheMasterInfo.getBusinessTypeCd();
		kscdtScheMasterInfo.jobId = scheMasterInfo.getJobId();
		kscdtScheMasterInfo.workplaceId = scheMasterInfo.getWorkplaceId();
		
		this.commandProxy().update(kscdtScheMasterInfo);
	}

	@Override
	public void deleteScheMasterInfo(String sId, GeneralDate generalDate) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(sId, generalDate);		
		this.commandProxy().remove(KscdtScheMasterInfo.class, primaryKey);
	}

}
