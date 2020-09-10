package nts.uk.screen.at.app.shift.workcycle;

import lombok.*;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.RefImageEachDay;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeInfor;
import nts.uk.screen.at.app.ksm003.find.WorkCycleDto;
import nts.uk.shr.com.context.AppContexts;

import java.util.*;

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

		private String workTypeCode;

		private String name;

		public static WorkTypeDto fromDomain(WorkType workType){
		    return new WorkTypeDto(
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

		public static RefImageEachDayDto fromDomain(RefImageEachDay domain, WorkInformation.Require require,
													WorkTypeFinder workTypeFinder, WorkTimeSettingRepository workTimeSettingRepository){
            Optional<WorkStyle> workStyle = domain.getWorkInformation().getWorkStyle(require);
			String workTypeCode = domain.getWorkInformation().getWorkTypeCode() == null ? "" : domain.getWorkInformation().getWorkTypeCode().v();
			String workTypeName = getWorkTypeName(workTypeCode, workTypeFinder);
			String workTimeCode = domain.getWorkInformation().getWorkTimeCode() == null ? "" : domain.getWorkInformation().getWorkTimeCode().v();
			String workTimeName = getWorkTimeName(workTimeCode, workTimeSettingRepository);
			return new WorkCycleReflectionDto.RefImageEachDayDto(
                    domain.getWorkCreateMethod().value,
                    new WorkCycleReflectionDto.WorkInformationDto(
							workTypeCode,
							workTypeName,
							workTimeCode,
							workTimeName
					),
					domain.getDate(),
                    workStyle.map(workStyle1 -> workStyle1.value).orElse(-1)
            );
		}

		private static String getWorkTypeName(String workTypeCode, WorkTypeFinder workTypeFinder){
			if(StringUtil.isNullOrEmpty(workTypeCode, true))
				return "";
			List<WorkTypeInfor> getPossibleWorkType = workTypeFinder.getPossibleWorkTypeKDL002(Arrays.asList(workTypeCode));
			return CollectionUtil.isEmpty(getPossibleWorkType) ? "" : getPossibleWorkType.get(0).getName();
		}
		private static String getWorkTimeName(String workTimeCode, WorkTimeSettingRepository workTimeSettingRepository){
			if(StringUtil.isNullOrEmpty(workTimeCode, true))
				return "";
			Map<String, String> listWorkTimeCodeName = workTimeSettingRepository
					.getCodeNameByListWorkTimeCd(AppContexts.user().companyId(), Collections.singletonList(workTimeCode));
			return listWorkTimeCodeName == null || CollectionUtil.isEmpty(listWorkTimeCodeName.values()) ? ""
					: listWorkTimeCodeName.values().toArray()[0].toString();
		}
	}

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	public static class WorkInformationDto{
		private String workTypeCode;
		private String workTypeName;
		private String workTimeCode;
		private String workTimeName;
	}

}