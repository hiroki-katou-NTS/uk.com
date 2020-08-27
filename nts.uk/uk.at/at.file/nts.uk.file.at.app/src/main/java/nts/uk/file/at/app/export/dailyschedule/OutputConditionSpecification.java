package nts.uk.file.at.app.export.dailyschedule;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * UKDesign.UniversalK.就業.KWR_帳表.KWR001_日別勤務表(daily work schedule).ユーザ固有情報(User specific information).条件指定
 * @author LienPTK
 */
@Getter
@Setter
public class OutputConditionSpecification {
	// エラー・アラームの抽出
	private boolean alarmExtraction;
	// 確認済みのデータ抽出
	private boolean verifiedDataExtraction;
	// 勤務条件指定
	private boolean workingConditionSpecification;
	// 応援勤務を抽出
	private boolean extractSupportWork;
	// 勤務種類参照
	private boolean workTypeReference;
	// 就業時間帯参照
	private boolean referToWorkingHours;
	// 確認済みのデータ抽出区分
	private Optional<ConfirmedData> confirmedData;
	// 勤務条件指定情報
	private Optional<WorkConditionSpecification> workConditionSpecification;
	// 応援勤務を抽出条件
	private Optional<ExtractSupportWorkCondition> extractSupportWorkCondition;
	// 勤務種類参照コード
	private List<String> workTypeReferenceCode;
	// 就業時間帯参照コード
	private List<String> workHoursReferenceCode;
	// 抽出条件のエラー・アラームコード
	private List<String> alarmCodeOfExtractionCondition;
}
