package nts.uk.file.at.app.export.dailyschedule;

import java.util.List;

import lombok.Data;
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

	// 項目選択区分
	private int selectionType;

	// 定型選択_出力レイアウトID
	private String standardSelectionLayoutId;

	// 自由設定_出力レイアウトID
	private String freeSettingLayoutId;

	// 自由設定_コード
	private String freeSettingCode;

	// ゼロ表示区分
	private int zeroDisplayType;

	// 項目表示切替
	private int switchItemDisplay;

	// 条件指定
	private OutputConditionSpecification outputConditionSpecification;
}
