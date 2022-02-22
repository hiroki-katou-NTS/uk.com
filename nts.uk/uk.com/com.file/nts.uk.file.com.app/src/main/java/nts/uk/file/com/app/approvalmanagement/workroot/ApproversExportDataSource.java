package nts.uk.file.com.app.approvalmanagement.workroot;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApprovalSettingInformationExport;

@Data
@AllArgsConstructor
public class ApproversExportDataSource {

	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
	
	/**
	 * 配下社員IDリスト
	 */
	private List<String> subordinateEmployeeIds;
	
	/**
	 * 社員情報
	 */
	private List<EmployeeInformationExport> employeeInfos;
	
	/**
	 * 職場情報一覧
	 */
	private List<WorkplaceInforParam> workplaces;
	
	/**
	 * 承認者設定情報
	 */
	private List<ApprovalSettingInformationExport> settingInfos;
	
	/**
	 * 社員コード/名称
	 */
	private List<ResultRequest600Export> employees;
	
	/**
	 * 会社情報
	 */
	private Optional<Company> company;
}
