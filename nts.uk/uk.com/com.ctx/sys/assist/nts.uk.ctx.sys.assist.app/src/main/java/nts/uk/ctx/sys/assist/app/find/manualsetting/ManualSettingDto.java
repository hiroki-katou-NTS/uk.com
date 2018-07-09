/**
 * 
 */
package nts.uk.ctx.sys.assist.app.find.manualsetting;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.ManualSetOfDataSave;

/**
 * @author nam.lh
 *
 */

@Data
public class ManualSettingDto {

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
	private int systemType;
	private Integer endYear;
	private Integer startYear;
	private int presenceOfEmployee;
	private int identOfSurveyPre;
	private String practitioner;

	public static ManualSettingDto fromDomain(ManualSetOfDataSave domain) {

		return new ManualSettingDto(domain.getCid(), domain.getStoreProcessingId(),
				domain.getPasswordAvailability().value, domain.getSaveSetName().v(), domain.getReferenceDate(),
				domain.getCompressedPassword().v(), domain.getExecutionDateAndTime(), domain.getDaySaveEndDate(),
				domain.getDaySaveStartDate(), domain.getMonthSaveEndDate(), domain.getMonthSaveStartDate(),
				domain.getSuppleExplanation(), domain.getSystemType().value,
				domain.getEndYear().isPresent() ? domain.getEndYear().get().v() : null,
				domain.getStartYear().isPresent() ? domain.getStartYear().get().v() : null,
				domain.getPresenceOfEmployee().value, domain.getIdentOfSurveyPre().value, domain.getPractitioner());
	}

	/**
	 * @param cid
	 * @param storeProcessingId
	 * @param passwordAvailability
	 * @param saveSetName
	 * @param referenceDate
	 * @param compressedPassword
	 * @param executionDateAndTime
	 * @param daySaveEndDate
	 * @param daySaveStartDate
	 * @param monthSaveEndDate
	 * @param monthSaveStartDate
	 * @param suppleExplanation
	 * @param systemType
	 * @param endYear
	 * @param startYear
	 * @param presenceOfEmployee
	 * @param identOfSurveyPre
	 * @param practitioner
	 */
	public ManualSettingDto(String cid, String storeProcessingId, int passwordAvailability, String saveSetName,
			GeneralDate referenceDate, String compressedPassword, GeneralDateTime executionDateAndTime,
			GeneralDate daySaveEndDate, GeneralDate daySaveStartDate, GeneralDate monthSaveEndDate,
			GeneralDate monthSaveStartDate, String suppleExplanation, int systemType, Integer endYear,
			Integer startYear, int presenceOfEmployee, int identOfSurveyPre, String practitioner) {
		super();
		this.cid = cid;
		this.storeProcessingId = storeProcessingId;
		this.passwordAvailability = passwordAvailability;
		this.saveSetName = saveSetName;
		this.referenceDate = referenceDate;
		this.compressedPassword = compressedPassword;
		this.executionDateAndTime = executionDateAndTime;
		this.daySaveEndDate = daySaveEndDate;
		this.daySaveStartDate = daySaveStartDate;
		this.monthSaveEndDate = monthSaveEndDate;
		this.monthSaveStartDate = monthSaveStartDate;
		this.suppleExplanation = suppleExplanation;
		this.systemType = systemType;
		this.endYear = endYear;
		this.startYear = startYear;
		this.presenceOfEmployee = presenceOfEmployee;
		this.identOfSurveyPre = identOfSurveyPre;
		this.practitioner = practitioner;
	}

}
