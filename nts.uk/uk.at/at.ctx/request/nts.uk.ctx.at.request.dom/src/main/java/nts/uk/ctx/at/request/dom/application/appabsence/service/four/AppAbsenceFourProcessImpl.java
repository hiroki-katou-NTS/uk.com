package nts.uk.ctx.at.request.dom.application.appabsence.service.four;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.WorkUse;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class AppAbsenceFourProcessImpl implements AppAbsenceFourProcess{
	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	public boolean getDisplayControlWorkingHours(String workTypeCd, Optional<HdAppSet> hdAppSet, String companyID) {
		boolean changeWorkHourFlg = false;
		if(workTypeCd != null && !workTypeCd.equals("-1")){
			if(hdAppSet.isPresent()){
				if(hdAppSet.get().getWrkHours().equals(WorkUse.NOT_USE)){
					return false;
				}else if(hdAppSet.get().getWrkHours().equals(WorkUse.USE)){
					return true;
				}else if(hdAppSet.get().getWrkHours().equals(WorkUse.USE_ONLY_HALF_HD)){
					//TODO
				}
			}
		}
		
		return changeWorkHourFlg;
	}

}
