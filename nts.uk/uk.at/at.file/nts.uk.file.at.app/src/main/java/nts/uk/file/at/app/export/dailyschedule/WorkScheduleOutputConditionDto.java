package nts.uk.file.at.app.export.dailyschedule;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * Instantiates a new work schedule output condition dto.
 * @author HoangNDH
 */
@Data
public class WorkScheduleOutputConditionDto {
	
	// 会社ID
	private CompanyId companyId;
	
	// ユーザID
	private String userId;
	
	// 帳票出力種類
	private int outputType;
	
	// 出力項目設定コード
	private String code;
	
	// 改ページ区分
	private int pageBreakIndicator;
	
	// 日別勤務表用明細・合計出力設定
	private WorkScheduleSettingTotalOutput settingDetailTotalOutput;
	
	// 条件設定
	private int conditionSetting;
	
	// 勤務実績のエラーアラームコード
	private List<String> errorAlarmCode;
}
