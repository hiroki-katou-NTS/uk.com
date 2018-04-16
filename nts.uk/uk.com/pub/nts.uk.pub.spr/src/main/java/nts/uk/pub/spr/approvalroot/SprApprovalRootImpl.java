package nts.uk.pub.spr.approvalroot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.pub.spr.EmployeeSprPub;
import nts.uk.ctx.bs.employee.pub.spr.export.EmpInDesignSprExport;
import nts.uk.ctx.bs.employee.pub.spr.export.EmpJobHistSprExport;
import nts.uk.ctx.bs.employee.pub.spr.export.EmpSprExport;
import nts.uk.ctx.bs.employee.pub.spr.export.PersonInfoSprExport;
import nts.uk.ctx.workflow.pub.spr.SprApprovalSearchPub;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalComSprExport;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalPersonSprExport;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalPhaseSprExport;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalRootStateSprExport;
import nts.uk.ctx.workflow.pub.spr.export.ApprovalWorkplaceSprExport;
import nts.uk.pub.spr.approvalroot.output.ApprovalRootSpr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class SprApprovalRootImpl implements SprApprovalRootService {
	
	private final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private EmployeeSprPub employeeSprPub;
	
	@Inject
	private SprApprovalSearchPub sprApprovalSearchPub;

	@Override
	public List<ApprovalRootSpr> getApprovalRoot(String employeeCD, String date) {
		String companyID = "000000000000-0001";
		this.checkParam(employeeCD, date);
		Optional<EmpSprExport> opEmployeeSpr = employeeSprPub.getEmployeeID(companyID, employeeCD);
		if(!opEmployeeSpr.isPresent()){
			throw new BusinessException("Msg_301");
		}
		return this.getApproverStatus(companyID, opEmployeeSpr.get().getEmployeeID(), GeneralDate.fromString(date, DATE_FORMAT));
	}

	@Override
	public void checkParam(String employeeCD, String date) {
		if(Strings.isBlank(employeeCD)){
			throw new BusinessException("Msg_999", "Msg_1026");
		}
		employeeSprPub.validateEmpCodeSpr(employeeCD);
		if(Strings.isBlank(date)){
			throw new BusinessException("Msg_1009", "Msg_1026");
		}
		try {
			GeneralDate.fromString(date, DATE_FORMAT);
		} catch (Exception e) {
			throw new BusinessException("Msg_1009", date);
		}
	}

	@Override
	public List<ApprovalRootSpr> getApproverStatus(String companyID, String approverID, GeneralDate date) {
		List<ApprovalRootSpr> approvalRootSprList = new ArrayList<>();
		List<String> empOnSettingList = new ArrayList<>();
		List<String> empOnAppList = new ArrayList<>();
		List<String> empOnConfirmList = new ArrayList<>();
 		Optional<EmpJobHistSprExport> opEmployeeJobHistSpr = employeeSprPub.findBySid(approverID, date);
		if(opEmployeeJobHistSpr.isPresent()){
			empOnSettingList = this.approvalRootSearch(companyID, date, approverID, opEmployeeJobHistSpr.get().getJobTitleID());
		}
		empOnAppList = this.getAppRootStateIDByType(companyID, approverID, date, 0); 
		empOnConfirmList = this.getAppRootStateIDByType(companyID, approverID, date, 1); 
		empOnSettingList.addAll(empOnAppList);
		empOnSettingList.addAll(empOnConfirmList);
		empOnSettingList = empOnSettingList.stream().distinct().collect(Collectors.toList());
		for(String empID : empOnSettingList){
			Integer status1 = 0;
			Integer status2 = 0;
			if(empOnAppList.contains(empID)){
				status1 = 1;
			}
			if(empOnConfirmList.contains(empID)){
				status2 = 1;
			}
			approvalRootSprList.add(new ApprovalRootSpr(empID, status1, status2));
		};
		return approvalRootSprList;
	}

	@Override
	public List<String> approvalRootSearch(String companyID, GeneralDate date, String approverID, String jobTitleID) {
		List<String> employeeIDList = new ArrayList<>();
		// 会社別ルート設定対象者を取得する
		employeeIDList.addAll(this.approvalRootSearchByCom(companyID, date, approverID, jobTitleID));
		// 職場別ルート設定対象者を取得する
		employeeIDList.addAll(this.approvalRootSearchByWp(companyID, date, approverID, jobTitleID));
		// 個人別ルート設定対象者を取得する
		employeeIDList.addAll(this.approvalRootSearchByPs(companyID, date, approverID, jobTitleID));
		return employeeIDList.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> approvalRootSearchByCom(String companyID, GeneralDate date, String approverID,
			String jobTitleID) {
		// 会社別ルート設定対象者を取得する　【共通】
		List<String> commonRootList = this.searchApprovalRootCom(companyID, date, approverID, jobTitleID, 0, null);
		if(!CollectionUtil.isEmpty(commonRootList)){
			return commonRootList;
		}
		// 会社別ルート設定対象者を取得する　【申請】
		List<String> empRootList = this.searchApprovalRootCom(companyID, date, approverID, jobTitleID, 1, null);
		if(!CollectionUtil.isEmpty(empRootList)){
			return empRootList;
		}
		// 会社別ルート設定対象者を取得する　【確認・日次】　取得結果を返す
		return this.searchApprovalRootCom(companyID, date, approverID, jobTitleID, 2, 0);
	}

	@Override
	public List<String> searchApprovalRootCom(String companyID, GeneralDate date, String approverID,
			String jobTitleID, Integer employmentRootAtr, Integer confirmRootType) {
		List<String> resultList = new ArrayList<>();
		// ドメインモデル「会社別就業承認ルート」を取得する
		List<ApprovalComSprExport> approvalRootComList = sprApprovalSearchPub.getApprovalRootCom(companyID, date, employmentRootAtr, confirmRootType);
		if(CollectionUtil.isEmpty(approvalRootComList)){
			return Collections.emptyList();
		}
		Boolean approverSetting = false;
		for(ApprovalComSprExport approvalComSpr : approvalRootComList){
			// 「会社別就業承認ルート」にINPUT.承認者（個人／職位）が設定されているかをチェックする
			if(this.juddmentApprover(companyID, approverID, jobTitleID, approvalComSpr.getBranchId())){
				approverSetting = true;
				break;
			}
		}
		if(!approverSetting){
			return Collections.emptyList();
		}
		// （基盤・社員Export）アルゴリズム「基準日時点在職中全社社員抽出」を実行する
		resultList = employeeSprPub.getEmployeesAtWorkByBaseDate(companyID, date)
			.stream().map(x -> x.getEmployeeId()).collect(Collectors.toList());
		return resultList;
	}

	@Override
	public boolean juddmentApprover(String companyID, String employeeID, String jobTitleID, String branchID) {
		List<String> empApprovalList = new ArrayList<>();
		List<String> jobApprovalList = new ArrayList<>();
		List<ApprovalPhaseSprExport> approvalPhaseSprList = sprApprovalSearchPub.getAllIncludeApprovers(companyID, branchID);
		approvalPhaseSprList.forEach(x -> {
			// ドメインモデル「承認フェーズ.承認者」が設定されたかをチェックする(check valid approval phase.approved person)
			if(CollectionUtil.isEmpty(x.getApprovers())){
				return;
			}
			x.getApprovers().forEach(y -> {
				// ドメインモデル「承認者.承認者指定区分」をチェックする(check Approver.ApproverAtr)
				if(y.getApprovalAtr()==0){ // 承認者.承認者指定区分 == 個人
					empApprovalList.add(y.getApproverId());
				} else { // 承認者.承認者指定区分 == 職位
					jobApprovalList.add(y.getJobTitleId());
				}
			});
		});
		// INPUT.承認社員IDが（承認者社員ID List）に存在するかをチェックする(INPUT. Check if approved employee ID exists in (Approver Employee ID List))
		if(empApprovalList.contains(employeeID)){
			return true;
		}
		// INPUT.承認者職位IDが（承認者職位ID List）に存在するかをチェックする(INPUT. Check whether the approver's position ID exists in (approver's position ID List))
		if(jobApprovalList.contains(jobTitleID)){
			return true;
		}
		return false;
	}

	@Override
	public List<String> approvalRootSearchByWp(String companyID, GeneralDate date, String approverID,
			String jobTitleID) {
		List<String> approverIDList = new ArrayList<>();
		// 職場別ルート設定対象者を取得する　【共通】
		approverIDList.addAll(this.searchApprovalRootWp(
				companyID, 
				date, 
				approverID, 
				jobTitleID, 
				0, 
				null));
		// 職場別ルート設定対象者を取得する　【申請】
		approverIDList.addAll(this.searchApprovalRootWp(
				companyID, 
				date, 
				approverID, 
				jobTitleID, 
				1, 
				null));
		// 職場別ルート設定対象者を取得する　【確認・日次】　
		approverIDList.addAll(this.searchApprovalRootWp(
				companyID, 
				date, 
				approverID, 
				jobTitleID, 
				2, 
				0));
		return approverIDList.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> searchApprovalRootWp(String companyID, GeneralDate date, String approverID, String jobTitleID,
			Integer employmentRootAtr, Integer confirmRootType) {
		List<String> approverIDList = new ArrayList<>();
		// ドメインモデル「職場別就業承認ルート」を取得する
		List<ApprovalWorkplaceSprExport> approvalRootWpList = sprApprovalSearchPub.getApprovalRootWp(companyID, date, employmentRootAtr, confirmRootType);
		if(CollectionUtil.isEmpty(approvalRootWpList)){
			return Collections.emptyList();
		}
		approvalRootWpList.forEach(x -> {
			// 「職場別就業承認ルート」にINPUT.承認者（個人／職位）が設定されているかをチェックする
			if(!this.juddmentApprover(companyID, approverID, jobTitleID, x.getBranchId())){
				return;
			}
			// 職場所属（下位職場含む）社員を取得する
			List<String> lowerWkpIDList = this.getEmployeeByWpList(companyID, x.getWorkplaceId(), date);
			approverIDList.addAll(lowerWkpIDList);
		});
		return approverIDList.stream().distinct().collect(Collectors.toList());
	}
	
	@Override
	public List<String> getEmployeeByWpList(String companyID, String workplaceID, GeneralDate date) {
		List<String> empWkpIDList = new ArrayList<>();
		// 対象職場所属社員を取得する 
		empWkpIDList.addAll(this.getEmployeeByWorkplace(workplaceID, date));
		
		// （基盤・社員Export）アルゴリズム「職場IDと基準日から下位職場を取得する」を実行する
		List<String> wpkIDList = employeeSprPub.findListWorkplaceIdByCidAndWkpIdAndBaseDate(companyID, workplaceID, date);
		wpkIDList.forEach(x -> {
			empWkpIDList.addAll(this.getEmployeeByWorkplace(x, date));
		});
		return empWkpIDList.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> getEmployeeByWorkplace(String workplaceID, GeneralDate date) {
		List<String> employeeIDList = new ArrayList<>();
		// （基盤・社員Export）アルゴリズム「指定職場の指定在籍状態の社員を取得」を実行する
		List<EmpInDesignSprExport> empInDesignatedSpr = employeeSprPub.getEmpInDesignated(
				workplaceID, 
				date, 
				Arrays.asList(1)); // List＜在籍状態(在職)＞
		empInDesignatedSpr.forEach(x -> {
			// （基幹・社員Export）アルゴリズム「社員IDから個人社員基本情報を取得」を実行する　RequestList No.1
			PersonInfoSprExport personInfoSpr = employeeSprPub.getPersonInfo(x.getEmployeeId());
			if(personInfoSpr==null){
				return;
			}
			int length = personInfoSpr.getEmployeeId().length();
			employeeIDList.add(personInfoSpr.getEmployeeId().substring(length - 6, length));
		});
		return employeeIDList.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> approvalRootSearchByPs(String companyID, GeneralDate date, String approverID,
			String jobTitleID) {
		List<String> approverIDList = new ArrayList<>();
		// 個人別ルート設定対象者を取得する　【共通】
		approverIDList.addAll(this.searchApprovalRootPs(
				companyID, 
				date, 
				approverID, 
				jobTitleID, 
				0, 
				null));
		// 個人別ルート設定対象者を取得する　【申請】
		approverIDList.addAll(this.searchApprovalRootPs(
				companyID, 
				date, 
				approverID, 
				jobTitleID, 
				1, 
				null));
		// 個人別ルート設定対象者を取得する　【確認・日次】　
		approverIDList.addAll(this.searchApprovalRootPs(
				companyID, 
				date, 
				approverID, 
				jobTitleID, 
				2, 
				0));
		return approverIDList.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> searchApprovalRootPs(String companyID, GeneralDate date, String approverID, String jobTitleID,
			Integer employmentRootAtr, Integer confirmRootType) {
		List<String> approverIDList = new ArrayList<>();
		// ドメインモデル「個人別就業承認ルート」を取得する
		List<ApprovalPersonSprExport> approvalRootPsList = sprApprovalSearchPub.getApprovalRootPs(companyID, date, employmentRootAtr, confirmRootType);
		if(CollectionUtil.isEmpty(approvalRootPsList)){
			return Collections.emptyList();
		}
		approvalRootPsList.forEach(x -> {
			// 「個人別就業承認ルート」にINPUT.承認者（個人／職位）が設定されているかをチェックする
			if(!this.juddmentApprover(companyID, approverID, jobTitleID, x.getBranchId())){
				return;
			}
			// （基幹・社員Export）アルゴリズム「社員IDから個人社員基本情報を取得」を実行する　RequestList No.1
			PersonInfoSprExport personInfoSpr = employeeSprPub.getPersonInfo(x.getEmployeeId());
			if(personInfoSpr==null){
				return;
			}
			int length = personInfoSpr.getEmployeeId().length();
			approverIDList.add(personInfoSpr.getEmployeeId().substring(length - 6, length));
		});
		return approverIDList.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public List<String> getAppRootStateIDByType(String companyID, String employeeID, GeneralDate date,
			Integer rootType) {
		List<String> empIDList = new ArrayList<>();
		// ドメインモデル「承認ルートインスタンス」を取得する
		List<ApprovalRootStateSprExport> approvalRootStateSprList = sprApprovalSearchPub.getRootStateByDateAndType(date, rootType);
		approvalRootStateSprList.forEach(x -> {
			// （ワークフローExport）アルゴリズム「3.指定した社員が承認できるかの判断」を実行する
			if(!sprApprovalSearchPub.judgmentTargetPersonCanApprove(companyID, x.getRootStateID(), employeeID)){
				return;
			}
			// （基幹・社員Export）アルゴリズム「社員IDから個人社員基本情報を取得」を実行する　RequestList No.1
			PersonInfoSprExport personInfoSpr = employeeSprPub.getPersonInfo(x.getEmployeeID());
			if(personInfoSpr==null){
				return;
			}
			int length = personInfoSpr.getEmployeeId().length();
			empIDList.add(personInfoSpr.getEmployeeId().substring(length - 6, length));
		});
		return empIDList;
	}
}
