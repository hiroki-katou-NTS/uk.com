package nts.uk.screen.at.app.shift.workcycle;

import lombok.*;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.RefImageEachDay;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.screen.at.app.ksm003.find.WorkCycleDto;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * 勤務サイクル反映ダイアログDto
 * @author khai.dh
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkCycleReflectionDto {
	private List<WorkTypeDto> pubHoliday;
    private List<WorkTypeDto> satHoliday;
    private List<WorkTypeDto> nonSatHoliday;
	private List<RefImageEachDayDto> reflectionImage;
	private List<WorkCycleDto> workCycleList;

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	public static class WorkTypeDto{

		private String companyId;

		private String workTypeCode;

		private String name;

		public static WorkTypeDto fromDomain(WorkType workType){
		    return new WorkTypeDto(
                    workType.getCompanyId(),
                    workType.getWorkTypeCode().v(),
                    workType.getName().v()
            );
        }
	}

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	public static class RefImageEachDayDto{

		private int workCreateMethod;

		private WorkInformationDto workInformation;

		private GeneralDate date;

		private int workStyles;

		public static RefImageEachDayDto fromDomain(RefImageEachDay domain, WorkInformation.Require require){
            Optional<WorkStyle> workStyle = domain.getWorkInformation().getWorkStyle(require);
            return new WorkCycleReflectionDto.RefImageEachDayDto(
                    domain.getWorkCreateMethod().value,
                    new WorkCycleReflectionDto.WorkInformationDto(domain.getWorkInformation().getWorkTypeCode().v(),
                            domain.getWorkInformation().getWorkTimeCode().v()),
                    domain.getDate(),
                    workStyle.map(workStyle1 -> workStyle1.value).orElse(-1)
            );
		}
	}

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	public static class WorkInformationDto{
		private String workTypeCode;
		private String workTimeCode;
	}

}