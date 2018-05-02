/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.manualSetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.TargetCategory;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployees;


/**
 * @author nam.lh
 *
 */
@Value
@Stateless
public class ManualSettingCommand {

	private String cid;
	private String storeProcessingId;
	int systemType;
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
	private int endYear;
	private int startYear;
	private int presenceOfEmployee;
	private int identOfSurveyPre;
	private String practitioner;
	List<TargetEmployees> employees;
	List<TargetCategory> category;
	private int selectionTarget;

	public ManualSetOfDataSave toDomain(String storeProcessingId) {
		return new ManualSetOfDataSave(cid, storeProcessingId, systemType, passwordAvailability, saveSetName,
				referenceDate, compressedPassword, executionDateAndTime, daySaveEndDate, daySaveStartDate,
				monthSaveEndDate, monthSaveStartDate, suppleExplanation, endYear, startYear, presenceOfEmployee,
				identOfSurveyPre, practitioner, employees.stream().map(x -> {
					return new TargetEmployees(storeProcessingId, x.getSid(), x.getBusinessname(), x.getScd());
				}).collect(Collectors.toList()), category.stream().map(x1 -> {
					return new TargetCategory(storeProcessingId, x1.getCategoryId());
				}).collect(Collectors.toList()));
	}
}
