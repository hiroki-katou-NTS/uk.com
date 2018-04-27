/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.manualSetting;

import javax.ejb.Stateless;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.Explanation;
import nts.uk.ctx.sys.assist.dom.storage.FileCompressionPassword;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;
import nts.uk.ctx.sys.assist.dom.storage.SaveSetName;
import nts.uk.ctx.sys.assist.dom.storage.Year;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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

	public ManualSetOfDataSave toDomain(String id) {
		return new ManualSetOfDataSave(cid, storeProcessingId, systemType, passwordAvailability, saveSetName,
				referenceDate, compressedPassword, executionDateAndTime, daySaveEndDate, daySaveStartDate,
				monthSaveEndDate, monthSaveStartDate, suppleExplanation, endYear, startYear, presenceOfEmployee,
				identOfSurveyPre, practitioner);
	}
}
