package nts.uk.ctx.at.record.app.find.divergence.time;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceTypeDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceTypeDivergenceAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DivergenceAttendanceItemFinder.
 */
@Stateless
public class DivergenceAttendanceItemFinder {

	/** The at type. */
	@Inject
	private AttendanceTypeDivergenceAdapter atType;

	/** The at name. */
	@Inject
	private AttendanceNameDivergenceAdapter atName;

	/** The div time repo. */
	@Inject
	DivergenceTimeRepository divTimeRepo;
	
	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;

	/**
	 * Gets the all at type.
	 *
	 * @param screenUseAtr
	 *            the screen use atr
	 * @return the all at type
	 */
	public List<AttendanceTypeDivergenceAdapterDto> getAllAtType(int divTimeNo) {
		//get companyId
		String companyId = AppContexts.user().companyId();
		
		//define screenUseAtr
		Integer screenUseAtr = 1;
		
		//define divType
		Integer divType = 0;
		
		//get option<domain>
		Optional<DivergenceTimeRoot> optionalDivTime = divTimeRepo.getDivTimeInfo(companyId, divTimeNo);

		//if present
		if (optionalDivTime.isPresent()) {
			//get divergence type
			divType = optionalDivTime.get().getDivType().value;
		}

		//cases for screenUseAtr
		switch (divType) {
		case 0:
			screenUseAtr = ScreenUseAtr.ARBITRARY_DIVERGENCE_TIME.value;
			break;
		case 1:
			screenUseAtr = ScreenUseAtr.HOLIDAYS_DEPARTURE_TIME.value;
			break;
		case 2:
			screenUseAtr = ScreenUseAtr.ENTRY_DIVERGENCE_TIME.value;
			break;
		case 3:
			screenUseAtr = ScreenUseAtr.EVACUATION_DEPARTURE_TIMR.value;
			break;
		case 4:
			screenUseAtr = ScreenUseAtr.PCLOGON_DIVERGENCE_TIME.value;
			break;
		case 5:
			screenUseAtr = ScreenUseAtr.PCLOGOFF_DIVERGENCE_TIME.value;
			break;
		case 6:
			screenUseAtr = ScreenUseAtr.PREDETERMINED_BREAK_TIME_DIVERGENCE.value;
			break;
		case 7:
			screenUseAtr = ScreenUseAtr.NON_SCHEDULED_DIVERGENCE_TIME.value;
			break;
		case 8:
			screenUseAtr = ScreenUseAtr.PREMATURE_OVERTIME_DEPARTURE_TIME.value;
			break;
		case 9:
			screenUseAtr = ScreenUseAtr.NORMAL_OVERTIME_DEVIATION_TIME.value;
			break;
		default:
			break;

		}
		List<AttendanceTypeDivergenceAdapterDto> data = atType.getItemByScreenUseAtr(companyId, screenUseAtr);
		return data;
	}

	/**
	 * Gets the at name.
	 *
	 * @param dailyAttendanceItemIds
	 *            the daily attendance item ids
	 * @return the at name
	 */
	public List<AttendanceNameDivergenceDto> getAtName(List<Integer> dailyAttendanceItemIds) {
		List<AttendanceNameDivergenceDto> data = atName.getDailyAttendanceItemName(dailyAttendanceItemIds);
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
