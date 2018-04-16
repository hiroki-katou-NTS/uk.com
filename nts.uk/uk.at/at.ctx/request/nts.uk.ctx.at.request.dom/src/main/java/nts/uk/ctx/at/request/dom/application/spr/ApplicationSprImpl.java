/*package nts.uk.ctx.at.request.dom.application.spr;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.pub.spr.appstatus.ApplicationSprService;
import nts.uk.pub.spr.appstatus.output.AppOverTimeSpr;
*//**
 * 
 * @author Doan Duy Hung
 *
 *//*
@Stateless
public class ApplicationSprImpl implements ApplicationSprService {
	
	@Inject
	private OvertimeRepository overtimeRepository;

	@Override
	public Optional<AppOverTimeSpr> getAppOvertimeByDate(GeneralDate appDate, String employeeID, Integer overTimeAtr) {
		return overtimeRepository.getAppOvertimeByDate(
				appDate, 
				employeeID, 
				EnumAdaptor.valueOf(overTimeAtr, OverTimeAtr.class)).
				map(x -> new AppOverTimeSpr(
						x.getAppID(), 
						x.getApplication().getReflectionInformation().getStateReflectionReal().value, 
						overTimeAtr));
	}

}
*/