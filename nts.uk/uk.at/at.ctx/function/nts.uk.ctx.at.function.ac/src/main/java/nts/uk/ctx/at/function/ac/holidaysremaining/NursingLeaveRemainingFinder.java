package nts.uk.ctx.at.function.ac.holidaysremaining;

//import java.util.Objects;
//import java.util.Optional;

import javax.ejb.Stateless;
//import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.NursingLeaveCurrentSituationImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.NursingLeaveRemainingAdapter;
//import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagementRepository;
//import nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata.ChildNursingRemainExport;
//import nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata.ChildNursingRemainInforExport;
//import nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata.NursingMode;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class NursingLeaveRemainingFinder implements NursingLeaveRemainingAdapter {

	//ShNursingLeaveSettingPub：製品版は未対応
	//@Inject
	//private ShNursingLeaveSettingPub shNursingLeaveSettingPub;
	//private TempCareManagementRepository tempCareManagementRepository;

	@Override
	public NursingLeaveCurrentSituationImported getNursingLeaveCurrentSituation(String companyId, String employeeId,
			DatePeriod period) {
		NursingLeaveCurrentSituationImported result = new NursingLeaveCurrentSituationImported("","");
//		ChildNursingRemainExport remainExport = shNursingLeaveSettingPub.aggrNursingRemainPeriod(companyId, employeeId,
//				period.start(), period.end(), NursingMode.Other);

//		if (Objects.isNull(remainExport)) {
			return result;
//		}
//
//		ChildNursingRemainInforExport preGrant = remainExport.getPreGrantStatement();
//
//		if (Objects.isNull(preGrant)) {
//			return result;
//		}
//
//		if (remainExport.getGrantPeriodFlag()) {
//			return new NursingLeaveCurrentSituationImported(preGrant.getNumberOfUse().toString(),
//					preGrant.getResidual().toString());
//		} else {
//			Optional<ChildNursingRemainInforExport> afterGrantOpt = remainExport.getAfterGrantStatement();
//			String afterNumberOfUse = afterGrantOpt.isPresent() ? afterGrantOpt.get().getNumberOfUse().toString() : "0";
//			String afterResidual = afterGrantOpt.isPresent() ? afterGrantOpt.get().getResidual().toString() : "0";
//			return new NursingLeaveCurrentSituationImported(
//					preGrant.getNumberOfUse().toString() + "/" + afterNumberOfUse,
//					preGrant.getResidual().toString() + "/" + afterResidual);
//		}
	}
}
