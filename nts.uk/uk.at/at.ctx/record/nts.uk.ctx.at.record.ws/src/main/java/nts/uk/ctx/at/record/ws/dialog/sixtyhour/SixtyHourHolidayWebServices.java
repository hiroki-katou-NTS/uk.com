package nts.uk.ctx.at.record.ws.dialog.sixtyhour;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday.OverTimeIndicationInformationDetails;
import nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday.PegManagementDto;
import nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday.SixtyHourHolidayFinder;
import nts.uk.ctx.at.record.ws.dialog.sixtyhour.dto.RemainHourDetailDto;
import nts.uk.ctx.at.record.ws.dialog.sixtyhour.dto.SixtyHourHolidayDto;
import nts.uk.ctx.at.record.ws.dialog.sixtyhour.dto.UsageDateDto;

/**
 * The Class SuperHolidayWebServices.
 */
@Path("at/record/dialog/sixtyhour")
@Produces("application/json")
public class SixtyHourHolidayWebServices extends WebService {
	
	/** The super holiday finder. */
	@Inject
	private SixtyHourHolidayFinder superHolidayFinder;
	
	/**
	 * アルゴリズム「60H超休の表示」を実行する.
	 *
	 * @param employeeId 社員ID
	 * @param baseDate the base date
	 * @return the 60 h overtime display info detail
	 */
	@POST
	@Path("get60hOvertimeDisplayInfoDetail/{employeeId}/{baseDate}")
	public SixtyHourHolidayDto get60hOvertimeDisplayInfoDetail(
									@PathParam("employeeId") String employeeId,
									@PathParam("baseDate") String baseDate) {
		// 60H超休の表示
		OverTimeIndicationInformationDetails details = this.superHolidayFinder.getOverTimeIndicationInformationDetails(employeeId, baseDate);

		// map year month - list usage date by 紐付け管理
		Map<YearMonth, List<PegManagementDto>> mapOccurrenceMonthUsageDate = details.getPegManagementDtos()
															.stream()
															.collect(Collectors.groupingBy(t -> t.getOccurrenceMonth()));

		List<RemainHourDetailDto> remainHourDetailDtos = new ArrayList<RemainHourDetailDto>();

		// get detail usage date of month by 残数情報 and mapOccurrenceMonthUsageDate 
		details.getRemainNumberDetailDtos().stream().forEach(item -> {
			if (item.getOccurrenceMonth() != null) {
				List<GeneralDate> lstUsageDate = mapOccurrenceMonthUsageDate.get(item.getOccurrenceMonth()) != null 
						? mapOccurrenceMonthUsageDate.get(item.getOccurrenceMonth())
							.stream()
							.map(PegManagementDto::getUsageDate)
							.collect(Collectors.toList())
						: new ArrayList<>();
				remainHourDetailDtos.add(RemainHourDetailDto.builder()
						.occurrenceMonth(item.getOccurrenceMonth().v())
						.deadline(item.getDeadline())
						.occurrenceTime(item.getOccurrenceTime())
						.usageDateDtos(details.getRemainNumberDetailDtos()
								.stream()
								.filter(t -> t.getUsageDate() != null ? lstUsageDate.contains(t.getUsageDate()) : false) // filter by lstUsageDate
								.map(r -> UsageDateDto.builder()
										.creationCategory(r.getCreationCategory())
										.usageDate(r.getUsageDate())
										.usageTime(r.getUsageTime())
										.build())
								.collect(Collectors.toList()))
						.build());
			}
		});

		// convert to dto
		return SixtyHourHolidayDto.builder()
				.carryoverNumber(details.getCarryoverNumber())
				.departmentOvertime60H(details.isDepartmentOvertime60H())
				.endDate(details.getDeadline().end())
				.startDate(details.getDeadline().start())
				.remainHourDetailDtos(remainHourDetailDtos)
				.residual(details.getResidual())
				.usageNumber(details.getUsageNumber())
				.build();
	}

}