package nts.uk.ctx.at.aggregation.app.command.scheduletable.outputsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.PersonalCounterCategory;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.WorkplaceCounterCategory;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OneRowOutputItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingCode;
import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingName;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableAttendanceItem;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSetting;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTablePersonalInfoItem;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author quytb
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTableOutputSettingSaveCommand {
	/** コード */
	private String code;

	/** 名称ド */
	private String name;

	/** 追加列情報 */
	private int additionalColumn;

	/** シフト表利用 */
	private int shiftBackgroundColor;

	/** 勤務情報 */
	private int dailyDataDisplay;

	/** 個人情報 */
	List<Integer> personalInfo;

	/** 追加列情報_1 */
	List<Integer> additionalInfo;

	/** 表示項目_1 */
	List<Integer> attendanceItem;

	/** 職場計出力設定名称 */
	List<Integer> workplaceCounterCategories;

	/** 個人計出力設定名称 */
	List<Integer> personalCounterCategories;

	public static ScheduleTableOutputSetting toDomain(ScheduleTableOutputSettingSaveCommand command) {
		List<WorkplaceCounterCategory> listWorkplaceCounterCategorie = new ArrayList<WorkplaceCounterCategory>();
		List<PersonalCounterCategory> listPersonalCounterCategorie = new ArrayList<PersonalCounterCategory>();
		List<OneRowOutputItem> details = new ArrayList<OneRowOutputItem>();
		int size = command.findMaxNumber(command.getPersonalInfo().size(), command.getAdditionalInfo().size(), command.getAttendanceItem().size());
		command.getWorkplaceCounterCategories().stream().forEach(x -> {
			listWorkplaceCounterCategorie.add(WorkplaceCounterCategory.of(x));
		});
		command.getPersonalCounterCategories().stream().forEach(x -> {
			listPersonalCounterCategorie.add(PersonalCounterCategory.of(x));
		});
		
		if (command.getPersonalInfo().isEmpty() && command.getAdditionalInfo().isEmpty() && command.getAttendanceItem().isEmpty()) {
			details.add(OneRowOutputItem.create(
					Optional.of(EnumAdaptor.valueOf(0, ScheduleTablePersonalInfoItem.class)),
					Optional.of(EnumAdaptor.valueOf(4, ScheduleTablePersonalInfoItem.class)),
					Optional.of(EnumAdaptor.valueOf(0, ScheduleTableAttendanceItem.class))));
		} else {
			for(int i = 0; i < size; i++) {				
				OneRowOutputItem oneRowOutputItem = OneRowOutputItem.create(Optional.ofNullable(i < command.getPersonalInfo().size()  ? EnumAdaptor.valueOf(command.getPersonalInfo().get(i), ScheduleTablePersonalInfoItem.class): null), 
						Optional.ofNullable(i < command.getAdditionalInfo().size() ? EnumAdaptor.valueOf(command.getAdditionalInfo().get(i), ScheduleTablePersonalInfoItem.class): null),
						Optional.ofNullable(i < command.getAttendanceItem().size() ? EnumAdaptor.valueOf(command.getAttendanceItem().get(i), ScheduleTableAttendanceItem.class) : null));
				details.add(oneRowOutputItem);
			}	
		}
		
		OutputItem outputItem = new OutputItem(NotUseAtr.valueOf(command.getAdditionalColumn()), 
											NotUseAtr.valueOf(command.getShiftBackgroundColor()), 
											NotUseAtr.valueOf(command.getDailyDataDisplay()), 
											details);
		
		ScheduleTableOutputSetting domain = new ScheduleTableOutputSetting(new OutputSettingCode(command.getCode()), 
																			new OutputSettingName(command.getName()), 
																			outputItem, 
																			listWorkplaceCounterCategorie, 
																			listPersonalCounterCategorie );
		return domain;		
	}
	
	public int findMaxNumber(int a, int b, int c) {
		if(a >= b && a >= c) {
			return a;
		} else if(b >= c) {
			return b;
		} else {
			return c;
		}		
	}
}
