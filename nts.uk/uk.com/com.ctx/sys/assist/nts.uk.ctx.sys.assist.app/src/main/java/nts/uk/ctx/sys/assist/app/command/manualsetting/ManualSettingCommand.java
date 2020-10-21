/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.manualsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.BusinessName;
import nts.uk.ctx.sys.assist.dom.storage.EmployeeCode;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.StorageClassification;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.ctx.sys.assist.dom.storage.TargetCategory;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployees;

/**
 * @author nam.lh
 *
 */
@Setter
@Getter
public class ManualSettingCommand {

	private String cid;
	private String storeProcessingId;
	private int passwordAvailability;
	private String saveSetName;
	private GeneralDate referenceDate;
	private String compressedPassword;
	private GeneralDateTime executionDateAndTime;
	private GeneralDate daySaveEndDate;
	private GeneralDate daySaveStartDate;
	private GeneralDate monthSaveEndDate;
	private GeneralDate monthSaveStartDate;
	private String suppleExplanation;
	private Integer endYear;
	private Integer startYear;
	private int presenceOfEmployee;
	private String practitioner;
	private List<TargetEmployeesCommand> employees;
	private List<TargetCategoryCommand> category;
	private String patternCode;

	public ManualSetOfDataSave toDomain(String cid, String storeProcessingId, String practitioner) {
		return new ManualSetOfDataSave(cid, storeProcessingId, passwordAvailability, saveSetName, referenceDate,
				compressedPassword, executionDateAndTime, daySaveEndDate, daySaveStartDate,
				monthSaveEndDate != null ? monthSaveEndDate.toString("yyyy-MM") : null,
				monthSaveStartDate != null ? monthSaveStartDate.toString("yyyy-MM") : null, suppleExplanation, endYear,
				startYear, presenceOfEmployee, practitioner, StorageClassification.MANUAL.value,
				employees.stream().map(x -> {
					return new TargetEmployees(storeProcessingId, x.getSid(), new BusinessName(x.getBusinessname()),
							new EmployeeCode(x.getScd()));
				}).collect(Collectors.toList()), category.stream().map(x1 -> {
					return new TargetCategory(storeProcessingId, x1.getCategoryId(), SystemType.ATTENDANCE_SYSTEM);
				}).collect(Collectors.toList()));
	}
}
