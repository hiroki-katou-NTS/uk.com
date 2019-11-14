package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.overtime.service.output.OvertimeHolidaySetting;

/**
 * refactorKAF005_ver3
 * @author Doan Duy Hung
 *
 */

public interface AppOvertimeService {
	
	/**
	 * 10_申請者を作成
	 * @param employeeIDs 申請者リスト
	 * @return 申請者情報
	 */
	public List<EmployeeInfoImport> createApplicants(List<String> employeeIDs);
	
	/**
	 * 11_設定データを取得
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param rootAtr 就業ルート区分
	 * @param appType 申請種類
	 * @param appDate 申請日
	 * @return
	 */
	public OvertimeHolidaySetting getSettingDatas(String companyID, String employeeID, Integer rootAtr, ApplicationType appType, GeneralDate appDate);
	
	/**
	 * 12_承認ルートを取得
	 * @param companyID 会社ID
	 * @param employeeID 申請者ID
	 * @param rootAtr 就業ルート区分
	 * @param appType 申請種類
	 * @param appDate 基準日
	 * @return
	 */
	public ApprovalRootContentImport_New getApprovalRoute(String companyID, String employeeID, Integer rootAtr, ApplicationType appType, GeneralDate appDate);
	
}
