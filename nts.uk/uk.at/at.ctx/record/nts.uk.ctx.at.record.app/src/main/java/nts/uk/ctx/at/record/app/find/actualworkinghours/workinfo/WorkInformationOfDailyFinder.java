package nts.uk.ctx.at.record.app.find.actualworkinghours.workinfo;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.app.find.actualworkinghours.workinfo.dto.WorkInformationOfDailyDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class WorkInformationOfDailyFinder extends FinderFacade{

	@Override
	@SuppressWarnings("unchecked")
	public WorkInformationOfDailyDto find() {
		// TODO Auto-generated method stub
		return new WorkInformationOfDailyDto();
	}

}
