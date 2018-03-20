package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.DivergenceAttendanceNameAdapter;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.DivergenceAttendanceNameDto;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.DivergenceAttendanceTypeAdapter;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.DivergenceAttendanceTypeDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceAttendanceItemFinder {
	
	@Inject 
	private DivergenceAttendanceTypeAdapter atType;
	//user contexts
	@Inject
	private DivergenceAttendanceNameAdapter atName;

	public List<DivergenceAttendanceTypeDto> getAllAtType(int screenUseAtr){
		String companyId = AppContexts.user().companyId();
		List<DivergenceAttendanceTypeDto> data = atType.getItemByScreenUseAtr(companyId, screenUseAtr);
		return data;
	}
	
	public List<DivergenceAttendanceNameDto> getAtName(List<Integer> dailyAttendanceItemIds){
		List<DivergenceAttendanceNameDto> data = atName.getDailyAttendanceItemName(dailyAttendanceItemIds);
		return data;
	}
}
