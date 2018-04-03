package nts.uk.ctx.at.request.pubimp.spr;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.pub.spr.ApplicationSprPub;
import nts.uk.ctx.at.request.pub.spr.export.AppOverTimeSprExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApplicationSprPubImpl implements ApplicationSprPub {
	
	@Inject
	private OvertimeRepository overtimeRepository;

	@Override
	public Optional<AppOverTimeSprExport> getAppOvertimeByDate(GeneralDate appDate, String employeeID, Integer overTimeAtr) {
		return overtimeRepository.getAppOvertimeByDate(
				appDate, 
				employeeID, 
				EnumAdaptor.valueOf(overTimeAtr, OverTimeAtr.class)).
				map(x -> new AppOverTimeSprExport(
						x.getAppID(), 
						x.getApplication().getReflectionInformation().getStateReflectionReal().value, 
						overTimeAtr));
	}

}
