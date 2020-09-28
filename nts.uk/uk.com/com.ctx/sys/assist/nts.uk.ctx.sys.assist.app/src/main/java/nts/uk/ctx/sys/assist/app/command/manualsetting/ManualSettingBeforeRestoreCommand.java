package nts.uk.ctx.sys.assist.app.command.manualsetting;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
public class ManualSettingBeforeRestoreCommand {
	private String cid;
	private String storeProcessingId;
	private int systemType;
	private int passwordAvailability;
	private String saveSetName;
	private GeneralDate referenceDate;
	private String compressedPassword;
	private GeneralDateTime executionDateAndTime;
	private GeneralDate daySaveEndDate;
	private GeneralDate daySaveStartDate;
	private GeneralDate monthSaveEndDate;
	private GeneralDate monthSaveStartDate;
	private Integer endYear;
	private Integer startYear;
	private String suppleExplanation;
	private int presenceOfEmployee;
	private int identOfSurveyPre;
	private String practitioner;
	private List<TargetEmployeesCommand> employees;
	private List<Category> category;
	
	@Value
	private class Category {
		private String categoryId;
		private String recoveryPeriod;
		private String startOfPeriod;
		private String endOfPeriod;
		private String saveSetName;
		
		public TargetCategoryCommand toCommand() {
			TargetCategoryCommand cmd = new TargetCategoryCommand();
			cmd.setCategoryId(categoryId);
			cmd.setStoreProcessingId(storeProcessingId);
			return cmd;
		}
	}
}
