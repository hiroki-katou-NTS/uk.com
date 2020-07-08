package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;

/**
 * 申請表示情報(基準日関係なし)
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppDispInfoNoDateOutput_Old {
	
	/**
	 * 社員情報
	 */
	private List<EmployeeInfoImport> employeeInfoLst;
	
	/**
	 * 申請承認設定
	 */
	private RequestSetting requestSetting;
	
	/**
	 * 申請定型理由リスト
	 */
	private List<ApplicationReason> appReasonLst;
	
}
