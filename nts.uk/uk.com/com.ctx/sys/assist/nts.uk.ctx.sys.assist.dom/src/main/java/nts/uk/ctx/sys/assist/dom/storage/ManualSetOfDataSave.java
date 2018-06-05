package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * データ保存の手動設定
 */
@NoArgsConstructor
@Getter
public class ManualSetOfDataSave extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * データ保存処理ID
	 */
	private String storeProcessingId;

	/**
	 * システム種類
	 */
	private SystemType systemType;

	/**
	 * パスワード有無
	 */
	private NotUseAtr passwordAvailability;

	/**
	 * 保存セット名称
	 */
	private SaveSetName saveSetName;

	/**
	 * 基準日
	 */
	private GeneralDate referenceDate;

	/**
	 * 手動保存の圧縮パスワード
	 */
	private FileCompressionPassword compressedPassword;

	/**
	 * 実行日時
	 */
	private GeneralDateTime executionDateAndTime;

	/**
	 * 日次保存終了日
	 */
	private GeneralDate daySaveEndDate;

	/**
	 * 日次保存開始日
	 */
	private GeneralDate daySaveStartDate;

	/**
	 * 月次保存終了日
	 */
	private GeneralDate monthSaveEndDate;

	/**
	 * 月次保存開始日
	 */
	private GeneralDate monthSaveStartDate;

	/**
	 * 補足説明
	 */
	private String suppleExplanation;

	/**
	 * 年次終了年
	 */
	private Year endYear;

	/**
	 * 年次開始年
	 */
	private Year startYear;

	/**
	 * 社員指定の有無
	 */
	private NotUseAtr presenceOfEmployee;

	/**
	 * 調査用保存の識別
	 */
	private NotUseAtr identOfSurveyPre;

	/**
	 * 実行者
	 */
	private String practitioner;
	private List<TargetEmployees> employees;
	private List<TargetCategory> category;


	public ManualSetOfDataSave(String cid, String storeProcessingId, int systemType, int passwordAvailability,
			String saveSetName, GeneralDate referenceDate, String compressedPassword,
			GeneralDateTime executionDateAndTime, GeneralDate daySaveEndDate, GeneralDate daySaveStartDate,
			GeneralDate monthSaveEndDate, GeneralDate monthSaveStartDate, String suppleExplanation, int endYear,
			int startYear, int presenceOfEmployee, int identOfSurveyPre, String practitioner) {

		super();
		this.cid = cid;
		this.storeProcessingId = storeProcessingId;
		this.systemType = EnumAdaptor.valueOf(systemType, SystemType.class);
		this.passwordAvailability = EnumAdaptor.valueOf(passwordAvailability, NotUseAtr.class);
		this.saveSetName = new SaveSetName(saveSetName);
		this.referenceDate = referenceDate;
		this.compressedPassword = new FileCompressionPassword(compressedPassword);
		this.executionDateAndTime = executionDateAndTime;
		this.daySaveEndDate = daySaveEndDate;
		this.daySaveStartDate = daySaveStartDate;
		this.monthSaveEndDate = monthSaveEndDate;
		this.monthSaveStartDate = monthSaveStartDate;
		this.suppleExplanation = suppleExplanation;
		this.endYear = new Year(endYear);
		this.startYear = new Year(startYear);
		this.presenceOfEmployee = EnumAdaptor.valueOf(presenceOfEmployee, NotUseAtr.class);
		this.identOfSurveyPre = EnumAdaptor.valueOf(identOfSurveyPre, NotUseAtr.class);
		this.practitioner = practitioner;
	}

	public ManualSetOfDataSave(String cid, String storeProcessingId, int systemType, int passwordAvailability,
			String saveSetName, GeneralDate referenceDate, String compressedPassword,
			GeneralDateTime executionDateAndTime, GeneralDate daySaveEndDate, GeneralDate daySaveStartDate,
			GeneralDate monthSaveEndDate, GeneralDate monthSaveStartDate, String suppleExplanation, int endYear,
			int startYear, int presenceOfEmployee, int identOfSurveyPre, String practitioner,
			List<TargetEmployees> employees, List<TargetCategory> category) {

		super();
		this.cid = cid;
		this.storeProcessingId = storeProcessingId;
		this.systemType = EnumAdaptor.valueOf(systemType, SystemType.class);
		this.passwordAvailability = EnumAdaptor.valueOf(passwordAvailability, NotUseAtr.class);
		this.saveSetName = new SaveSetName(saveSetName);
		this.referenceDate = referenceDate;
		
		if (getCompressedPassword() != null) {
			this.compressedPassword = new FileCompressionPassword(CommonKeyCrypt.encrypt(compressedPassword));
		}
		
		this.executionDateAndTime = executionDateAndTime;
		this.daySaveEndDate = daySaveEndDate;
		this.daySaveStartDate = daySaveStartDate;
		this.monthSaveEndDate = monthSaveEndDate;
		this.monthSaveStartDate = monthSaveStartDate;
		this.suppleExplanation = suppleExplanation;
		this.endYear = new Year(endYear);
		this.startYear = new Year(startYear);
		this.presenceOfEmployee = EnumAdaptor.valueOf(presenceOfEmployee, NotUseAtr.class);
		this.identOfSurveyPre = EnumAdaptor.valueOf(identOfSurveyPre, NotUseAtr.class);
		this.practitioner = practitioner;
		this.employees = employees;
		this.category = category;
	}

}
