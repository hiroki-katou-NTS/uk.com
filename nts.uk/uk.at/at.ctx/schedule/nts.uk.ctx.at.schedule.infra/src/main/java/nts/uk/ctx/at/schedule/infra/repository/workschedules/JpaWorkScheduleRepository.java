package nts.uk.ctx.at.schedule.infra.repository.workschedules;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaWorkScheduleRepository extends JpaRepository implements WorkScheduleRepository{
	
	private static final String SELECT_BY_KEY  = "SELECT c FROM KscdtSchBasicInfo c WHERE c.pk.CID = :CID AND c.pk.YMD = :YMD";

	@Override
	public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
		Optional<WorkSchedule> workSchedule =  this.queryProxy().query(SELECT_BY_KEY , KscdtSchBasicInfo.class)
				.setParameter("SID", "employeeID")
				.setParameter("YMD", "ymd")
				.getSingle(c->c.toDomain(employeeID, ymd));
		return workSchedule;
	}

	@Override
	public void insert(WorkSchedule workSchedule) {
		String cID = AppContexts.user().companyId();
		this.commandProxy().insert(this.toEntity(workSchedule, cID));
	}
	
	public KscdtSchBasicInfo toEntity(WorkSchedule workSchedule, String cID) {
		return KscdtSchBasicInfo.toEntity(workSchedule, cID);
	}

	@Override
	public void update(WorkSchedule workSchedule) {
		String cID = AppContexts.user().companyId();
		Optional<KscdtSchBasicInfo> oldData = this.queryProxy().query(SELECT_BY_KEY , KscdtSchBasicInfo.class)
				.setParameter("SID", "employeeID")
				.setParameter("YMD", "ymd")
				.getSingle(c->c);
		
		if(oldData.isPresent()) {
			KscdtSchBasicInfo newData = KscdtSchBasicInfo.toEntity(workSchedule, cID);
			KscdtSchBasicInfo dataUpdate = oldData.get(); 
			dataUpdate.confirmedATR = newData.confirmedATR;
			dataUpdate.empCd = newData.empCd;
			dataUpdate.jobId = newData.jobId;
			dataUpdate.wkpId = newData.wkpId;
			dataUpdate.clsCd = newData.clsCd;
			dataUpdate.busTypeCd = newData.busTypeCd;
			dataUpdate.nurseLicense = newData.nurseLicense;
			dataUpdate.wktpCd = newData.wktpCd;
			dataUpdate.wktmCd = newData.wktmCd;
			dataUpdate.goStraightAtr = newData.goStraightAtr;
			dataUpdate.backStraightAtr = newData.backStraightAtr;
			dataUpdate.kscdtSchTime = newData.kscdtSchTime;
			
			this.commandProxy().update(KscdtSchBasicInfo.toEntity(workSchedule, cID));
		}
	}
}
