package nts.uk.ctx.at.aggregation.app.find.scheduletable.outputsetting;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.WorkplaceCounterCategory;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OneRowOutputItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSetting;
/**
 * 
 * @author quytb
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleTableOutputSettingDto {
	private String code;
	private String name;
	private Integer additionalColumn;
	private Integer shiftBackgroundColor;
	private Integer dailyDataDisplay;
	private List<Integer> personalInfo;
	private List<Integer> additionalInfo;
	private List<Integer> attendanceItem;
	private List<Integer> workplaceCounterCategories;
	private List<Integer> personalCounterCategories;
	private Boolean isAttendance;
	
	
	public static ScheduleTableOutputSettingDto setData(ScheduleTableOutputSetting domain) {
		
		List<OneRowOutputItem> list = domain.getOutputItem().getDetails();
		List<Integer> personalInfos = new ArrayList<Integer>();
		List<Integer> additionalInfos = new ArrayList<Integer>();
		List<Integer> attendanceCodeItems = new ArrayList<Integer>();
		List<Integer> workplaceCounterCategories = new ArrayList<Integer>();
		List<Integer> personalCounterCategories = new ArrayList<Integer>();

		List<PersonalCounterCategory> personalCounterCategorieList = domain.getPersonalCounterCategories();
		List<WorkplaceCounterCategory> workplaceCounterCategorieList = domain.getWorkplaceCounterCategories();

		personalCounterCategorieList.stream().forEach(x -> {
			personalCounterCategories.add(x.value);
		});
		workplaceCounterCategorieList.stream().forEach(x -> {
			workplaceCounterCategories.add(x.value);
		});

		list.stream().forEach(item -> {
			personalInfos.add(item.getPersonalInfo().isPresent() ? item.getPersonalInfo().get().value : null);
			additionalInfos.add(item.getAdditionalInfo().isPresent() ? item.getAdditionalInfo().get().value : null);
			attendanceCodeItems.add(item.getAttendanceItem().isPresent() ? item.getAttendanceItem().get().value : null);

		});

		return ScheduleTableOutputSettingDto.builder().code(domain.getCode().v())
				.name(domain.getName().v())
				.additionalColumn(domain.getOutputItem().getAdditionalColumnUseAtr().value)
				.shiftBackgroundColor(domain.getOutputItem().getShiftBackgroundColorUseAtr().value)
				.dailyDataDisplay(domain.getOutputItem().getDailyDataDisplayAtr().value)
				.personalInfo(personalInfos)
				.additionalInfo(additionalInfos)
				.attendanceItem(attendanceCodeItems)
				.personalCounterCategories(personalCounterCategories)
				.workplaceCounterCategories(workplaceCounterCategories)
				.build();
	}	
	
}
