package nts.uk.ctx.at.record.app.find.divergencetime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapterDto;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceItemSetFinder {
	@Inject
	private DivergenceTimeRepository divTimeRepo;
	
	@Inject 
	private AttendanceTypeDivergenceAdapter atType;
	//user contexts
	@Inject
	private AttendanceNameDivergenceAdapter atName;
	
	@Inject
	private AttendanceItemNameAdapter attendanceItemNameAdapter;
	
	public List<DivergenceItemSetDto> getAllDivReasonByCode(String divTimeId){
		String companyId = AppContexts.user().companyId();
		List<DivergenceItemSetDto> lst = this.divTimeRepo.getallItembyCode(companyId,Integer.valueOf(divTimeId))
				.stream()
				.map(c->DivergenceItemSetDto.fromDomain(c))
				.collect(Collectors.toList());
		return lst;
	}
	
	public List<AttendanceTypeDivergenceAdapterDto> getAllAtType(int screenUseAtr){
		String companyId = AppContexts.user().companyId();
		List<AttendanceTypeDivergenceAdapterDto> data = atType.getItemByScreenUseAtr(companyId, screenUseAtr);
		return data;
	}
	
	public List<AttendanceNameDivergenceDto> getAtName(List<Integer> dailyAttendanceItemIds){
		List<AttendanceNameDivergenceDto> data = atName.getDailyAttendanceItemName(dailyAttendanceItemIds);
		return data;
	}
	
	public List<AttendanceNameDivergenceDto> getMonthlyAtName(
			List<Integer> monthlyAttendanceItemIds) {
		List<AttendanceNameDivergenceDto> data = attendanceItemNameAdapter
				.getMonthlyAttendanceItemName(monthlyAttendanceItemIds).stream()
				.map(item -> new AttendanceNameDivergenceDto(item.getAttendanceItemId(),
						item.getAttendanceItemName(), item.getAttendanceItemDisplayNumber()))
				.collect(Collectors.toList());
		return data;
	}

}
