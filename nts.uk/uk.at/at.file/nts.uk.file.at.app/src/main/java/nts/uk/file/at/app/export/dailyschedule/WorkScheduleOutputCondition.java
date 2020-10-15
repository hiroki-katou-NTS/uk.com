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
	// 改ページ区分
	private PageBreakIndicator pageBreakIndicator;
	// 日別勤務表用明細・合計出力設定
	private WorkScheduleSettingTotalOutput settingDetailTotalOutput;
	// 条件設定
	private OutputConditionSetting conditionSetting;
	// 勤務実績のエラーアラームコード
	private Optional<List<ErrorAlarmWorkRecordCode>> errorAlarmCode;
	
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
