package nts.uk.ctx.at.function.ac.holidaysremaining;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.ChildNursingLeaveCurrentSituationImported;
import nts.uk.ctx.at.function.dom.adapter.holidaysremaining.ChildNursingLeaveRemainingAdapter;
import nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata.ChildNursingRemainExport;
import nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata.ChildNursingRemainInforExport;
import nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata.NursingMode;
import nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata.ShNursingLeaveSettingPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ChildNursingLeaveRemainingFinder implements ChildNursingLeaveRemainingAdapter {

	@Inject
	private ShNursingLeaveSettingPub shNursingLeaveSettingPub;

	@Override
	public ChildNursingLeaveCurrentSituationImported getChildNursingLeaveCurrentSituation(String companyId,
			String employeeId, DatePeriod period) {
		ChildNursingRemainExport remainExport = shNursingLeaveSettingPub.aggrChildNursingRemainPeriod(companyId,
				employeeId, period, NursingMode.Other);

		ChildNursingRemainInforExport preGrant = remainExport.getPreGrantStatement();

		if (remainExport.getGrantPeriodFlag()) {
			return new ChildNursingLeaveCurrentSituationImported(preGrant.getNumberOfUse().toString(),
					preGrant.getResidual().toString());
		} else {
			Optional<ChildNursingRemainInforExport> afterGrantOpt = remainExport.getAfterGrantStatement();
			String afterNumberOfUse = afterGrantOpt.isPresent() ? afterGrantOpt.get().getNumberOfUse().toString() : "0";
			String afterResidual = afterGrantOpt.isPresent() ? afterGrantOpt.get().getResidual().toString() : "0";
			return new ChildNursingLeaveCurrentSituationImported(
					preGrant.getNumberOfUse().toString() + "/" + afterNumberOfUse,
					preGrant.getResidual().toString() + "/" + afterResidual);
		}
	}
}
