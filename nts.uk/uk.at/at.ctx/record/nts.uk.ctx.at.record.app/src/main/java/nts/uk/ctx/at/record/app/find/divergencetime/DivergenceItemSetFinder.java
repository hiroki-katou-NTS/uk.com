package nts.uk.ctx.at.record.app.find.divergencetime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceTypeDivergenceAdapterDto;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
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
	
	@Inject 
	private CompanyDailyItemService companyDailyItemService;

	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;
	
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
		String companyId = AppContexts.user().companyId();
		List<AttendanceNameDivergenceDto> data = companyDailyItemService
				.getDailyItems(companyId, Optional.empty(), dailyAttendanceItemIds, Collections.emptyList())
				.stream()
				.map(x -> {
					AttendanceNameDivergenceDto dto = new AttendanceNameDivergenceDto(x.getAttendanceItemId(),
							x.getAttendanceItemName(), x.getAttendanceItemDisplayNumber());
					return dto;
				}).collect(Collectors.toList());
		// List<AttendanceNameDivergenceDto> data = atName.getDailyAttendanceItemName(dailyAttendanceItemIds);
		return data;
	}
	
	public List<AttendanceNameDivergenceDto> getMonthlyAtName(
			List<Integer> monthlyAttendanceItemIds) {
		String companyId = AppContexts.user().companyId();
		List<AttendanceNameDivergenceDto> data = companyMonthlyItemService
				.getMonthlyItems(companyId, Optional.empty(), monthlyAttendanceItemIds, Collections.emptyList())
				.stream().map(x -> {
					AttendanceNameDivergenceDto dto = new AttendanceNameDivergenceDto(x.getAttendanceItemId(),
							x.getAttendanceItemName(), x.getAttendanceItemDisplayNumber());
					return dto;
				}).collect(Collectors.toList());
		/*List<AttendanceNameDivergenceDto> data = attendanceItemNameAdapter
				.getMonthlyAttendanceItemName(monthlyAttendanceItemIds).stream()
				.map(item -> new AttendanceNameDivergenceDto(item.getAttendanceItemId(),
						item.getAttendanceItemName(), item.getAttendanceItemDisplayNumber()))
				.collect(Collectors.toList());*/
		return data;
	}

}
