package nts.uk.ctx.at.request.infra.repository.application.appabsence;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.KrqdtAppForLeave;
import nts.uk.ctx.at.request.infra.entity.application.appabsence.KrqdtAppForLeavePK;
@Stateless
public class JpaAppAbsenceRepository extends JpaRepository implements AppAbsenceRepository{

	@Override
	public Optional<AppAbsence> getAbsenceById(String companyID, String appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertAbsence(AppAbsence appAbsence) {
		this.commandProxy().insert(toEntity(appAbsence));
	}
	
	private KrqdtAppForLeave toEntity(AppAbsence domain) {

		return new KrqdtAppForLeave(new KrqdtAppForLeavePK(domain.getCompanyID(), domain.getAppID()),
				domain.getVersion(),
				domain.getHolidayAppType().value,
				domain.getWorkTypeCode().toString(),
				domain.getWorkTimeCode().toString(),
				domain.getStartTime1().v(),
				domain.getEndTime1().v(),
				domain.getStartTime2().v(),
				domain.getEndTime2().v(),
				domain.isHalfDayFlg(),
				domain.isChangeWorkHour(),
				domain.getAllDayHalfDayLeaveAtr().value
				);
	}

}
