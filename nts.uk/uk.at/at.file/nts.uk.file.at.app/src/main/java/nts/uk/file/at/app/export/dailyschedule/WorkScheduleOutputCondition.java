package nts.uk.file.at.app.export.dailyschedule;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * 日別勤務表の出力条件
 * @author HoangNDH
 *
 */
@Data
public class WorkScheduleOutputCondition  {
	// 会社ID
	private CompanyId companyId;
	// ユーザID
	private String userId;
	// 帳票出力種類
	private FormOutputType outputType;
	// 出力項目設定コード
	private List<OutputItemSettingCode> code;
	// 改ページ区分
	private PageBreakIndicator pageBreakIndicator;
	// 日別勤務表用明細・合計出力設定
	private WorkScheduleSettingTotalOutput settingDetailTotalOutput;
	// 条件設定
	private OutputConditionSetting conditionSetting;
	// 勤務実績のエラーアラームコード
	private Optional<List<ErrorAlarmWorkRecordCode>> errorAlarmCode;
}
