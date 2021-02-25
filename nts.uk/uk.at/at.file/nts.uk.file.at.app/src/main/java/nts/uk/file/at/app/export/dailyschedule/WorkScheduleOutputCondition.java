package nts.uk.file.at.app.export.dailyschedule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

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
	private OutputItemSettingCode code;
	// 改ページ区分条件指定
	private PageBreakIndicator pageBreakIndicator;
	// 明細・合計出力設定
	private WorkScheduleSettingTotalOutput settingDetailTotalOutput;
	// 条件設定
	private OutputConditionSetting conditionSetting;
	// 勤務実績のエラーアラームコード
	private Optional<List<ErrorAlarmWorkRecordCode>> errorAlarmCode;
	// 項目選択区分
	private ItemSelectionType selectionType;
	// 定型選択_出力レイアウトID
	private String standardSelectionLayoutId;
	// 自由設定_出力レイアウトID
	private String freeSettingLayoutId;
	// 自由設定_コード
	private OutputItemSettingCode freeSettingCode;
	// ゼロ表示区分
	private ZeroDisplayType zeroDisplayType;
	// 項目表示切替
	private SwitchItemDisplay switchItemDisplay;
	// 条件指定
	private Optional<OutputConditionSpecification> outputConditionSpecification;

	/**
	 * Creates the domain from java type.
	 *
	 * @param dto the dto
	 * @return the work schedule output condition
	 */
	public static WorkScheduleOutputCondition createFromJavaType(WorkScheduleOutputConditionDto dto) {
		WorkScheduleOutputCondition condition = new WorkScheduleOutputCondition();
		condition.setCompanyId(dto.getCompanyId());
		condition.setUserId(dto.getUserId());
		condition.setOutputType(FormOutputType.valueOf(dto.getOutputType()));
		condition.setCode(new OutputItemSettingCode(dto.getCode()));
		condition.setPageBreakIndicator(PageBreakIndicator.valueOf(dto.getPageBreakIndicator()));
		condition.setSettingDetailTotalOutput(dto.getSettingDetailTotalOutput());
		condition.setConditionSetting(OutputConditionSetting.valueOf(dto.getConditionSetting()));
		condition.setErrorAlarmCode(Optional.of(dto.getErrorAlarmCode().stream().map(temp -> {
			return new ErrorAlarmWorkRecordCode(temp);
		}).collect(Collectors.toList())));
		condition.setSelectionType(ItemSelectionType.valueOf(dto.getSelectionType()));
		condition.setStandardSelectionLayoutId(dto.getStandardSelectionLayoutId());
		condition.setFreeSettingLayoutId(dto.getFreeSettingLayoutId());
		condition.setFreeSettingCode(new OutputItemSettingCode(dto.getFreeSettingCode()));
		condition.setZeroDisplayType(ZeroDisplayType.valueOf(dto.getZeroDisplayType()));
		condition.setSwitchItemDisplay(SwitchItemDisplay.valueOf(dto.getSwitchItemDisplay()));
		if (dto.getOutputConditionSpecification() != null) {
			condition.setOutputConditionSpecification(Optional.of(dto.getOutputConditionSpecification()));
		}
		return condition;
	}
	
	/**
	 * Checks if is show workplace.
	 * A3_1, A3_2, A3_3
	 * @return	boolean 
	 */
	public Boolean isShowWorkplace(){
		return this.settingDetailTotalOutput.isDetails() || this.settingDetailTotalOutput.isWorkplaceTotal() || this.settingDetailTotalOutput.isCumulativeWorkplace();
	}
	/**
	 * Update ver 18 in document
	 * B3_1, B3_2
	 * @return
	 */
	public Boolean isShowPeronal(){
		return this.settingDetailTotalOutput.isDetails() || this.settingDetailTotalOutput.isPersonalTotal() || this.settingDetailTotalOutput.isCumulativeWorkplace();
	}
	
	public Boolean isPrivatePerson(){
		return this.settingDetailTotalOutput.isDetails() || this.settingDetailTotalOutput.isPersonalTotal() ;
	}
	
	public Boolean isDating(){
		return this.settingDetailTotalOutput.isDetails() || this.settingDetailTotalOutput.isWorkplaceTotal() || this.settingDetailTotalOutput.isCumulativeWorkplace();
	}
}
