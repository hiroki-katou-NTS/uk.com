package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootCommonOutput;
@Stateless
public class ApplicationOfEmployeeImpl implements ApplicationOfEmployee{
	@Inject
	private EmployeeAdapter empAdapter;
	@Inject
	private ApprovalPhaseRepository phaseRespoitory;
	/**
	 * get root by application/confirm
	 */
	@Override
	public List<ApprovalRootCommonOutput> appOfEmployee(List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor, List<PersonApprovalRoot> lstPersonRootInfor,
			String companyID, String sId, AppTypes appType, GeneralDate baseDate) {
		//Lay theo person
		//ドメインモデル「個人別就業承認ルート」(domain 「個人別就業承認ルート」): 申請本人の社員ID, 就業ルート区分(申請か、確認か、任意項目か), 対象申請（３６協定時間申請を除く）(ngoai loai don ３６協定時間申請）
		List<PersonApprovalRoot> lstPsRoots = lstPersonRootInfor
				.stream()
				.filter(x -> x.getEmployeeId().equals(sId) 
						&& this.checkByType(x.getConfirmationRootType(), x.getEmploymentRootAtr(), x.getApplicationType(), appType))
				.collect(Collectors.toList());
		if(lstPsRoots.isEmpty()){//TH: set cua type khong co -> lay theo common
			lstPsRoots = lstPersonRootInfor
					.stream()
					.filter(x -> x.getEmployeeId().equals(sId) && x.isCommon())
					.collect(Collectors.toList());
		}
		//データが１件以上取得した場合(data >= 1)
		if(!CollectionUtil.isEmpty(lstPsRoots)) {
			List<ApprovalPhase> lstPhase = new ArrayList<>();
			lstPsRoots.stream().forEach(y -> {
				phaseRespoitory.getAllIncludeApprovers(companyID, y.getBranchId()).stream().forEach(z -> {
					lstPhase.add(z);
				});
				
			});
			
			if(!CollectionUtil.isEmpty(lstPhase)) {
				List<ApprovalRootCommonOutput> rootOutputs = lstPsRoots
						.stream()
						.map(x -> new ApprovalRootCommonOutput(x.getCompanyId(),
								x.getApprovalId(), 
								x.getEmployeeId(), 
								"",
								x.getEmploymentAppHistoryItems().get(0).getHistoryId(),
								x.getApplicationType() == null ? 99: x.getApplicationType().value, 
								x.getEmploymentAppHistoryItems().get(0).start(),
								x.getEmploymentAppHistoryItems().get(0).end(),
								x.getBranchId(),
								x.getAnyItemApplicationId(),
								x.getConfirmationRootType() == null ? null: x.getConfirmationRootType().value,
								x.getEmploymentRootAtr().value))
						.collect(Collectors.toList());
				return rootOutputs;
			}			
		}
		
		//対象者の所属職場を含める上位職場を取得する(lấy thông tin Affiliation workplace và Upper workplace của nhân viên)
		List<String> findWpkIdsBySid = empAdapter.findWpkIdsBySid(companyID, sId, baseDate);
		//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
		for(String wpId: findWpkIdsBySid) {
			//ドメインモデル「職場別就業承認ルート」を取得する(lấy domain「職場別就業承認ルート」): 職場ID（ループ中の職場ID）, 就業ルート区分(申請か、確認か、任意項目か), 対象申請（３６協定時間申請を除く）
			List<WorkplaceApprovalRoot> lstWpRoots = lstWorkpalceRootInfor
					.stream()
					.filter(x -> (x.getWorkplaceId().equals(wpId) 
							&& this.checkByType(x.getConfirmationRootType(), x.getEmploymentRootAtr(), x.getApplicationType(), appType)))
					.collect(Collectors.toList());
			if(lstWpRoots.isEmpty()){//TH: set cua type khong co -> lay theo common
				lstWpRoots = lstWorkpalceRootInfor
						.stream()
						.filter(x -> x.getWorkplaceId().equals(wpId) && x.isCommon())
						.collect(Collectors.toList());
			}
			
			//データが１件以上取得した場合(data >= 1)
			if(!CollectionUtil.isEmpty(lstWpRoots)) {
				List<ApprovalPhase> lstPhase = new ArrayList<>();
				lstWpRoots.stream().forEach(y -> {
					phaseRespoitory.getAllIncludeApprovers(companyID, y.getBranchId()).stream().forEach(z -> {
						lstPhase.add(z);
					});
					
				});
				if(!CollectionUtil.isEmpty(lstPhase)) {
					List<ApprovalRootCommonOutput> rootOutputs = lstWpRoots
							.stream()
							.map(x -> new ApprovalRootCommonOutput(x.getCompanyId(),
									x.getApprovalId(), 
									"", 
									x.getWorkplaceId(),
									x.getEmploymentAppHistoryItems().get(0).getHistoryId(),
									x.getApplicationType() == null ? 99: x.getApplicationType().value, 
									x.getEmploymentAppHistoryItems().get(0).start(),
									x.getEmploymentAppHistoryItems().get(0).end(),
									x.getBranchId(),
									x.getAnyItemApplicationId(),
									x.getConfirmationRootType() == null ? null: x.getConfirmationRootType().value,
									x.getEmploymentRootAtr().value))
							.collect(Collectors.toList());
					return rootOutputs;
				}				
				
			}
			
			//ドメインモデル「会社別就業承認ルート」を取得する(lấy dư liệu domain 「会社別就業承認ルート」): 就業ルート区分(申請か、確認か、任意項目か), 対象申請（３６協定時間申請を除く）
			List<CompanyApprovalRoot> lstRoots = lstCompanyRootInfor.stream()
					.filter(x -> x.getCompanyId().equals(companyID)
								&& this.checkByType(x.getConfirmationRootType(), x.getEmploymentRootAtr(), x.getApplicationType(), appType))
					.collect(Collectors.toList());
			if(lstRoots.isEmpty()){//TH: set cua type khong co -> lay theo common
				lstRoots = lstCompanyRootInfor
						.stream()
						.filter(x -> x.getCompanyId().equals(companyID) && x.isCommon())
						.collect(Collectors.toList());
			}
			//データが１件以上取得した場合(data >= 1)
			if(!CollectionUtil.isEmpty(lstRoots)) {
				List<ApprovalPhase> lstPhase = new ArrayList<>();
				lstRoots.stream().forEach(y -> {
					phaseRespoitory.getAllIncludeApprovers(companyID, y.getBranchId()).stream().forEach(z -> {
						lstPhase.add(z);
					});
					
				});
				if(!CollectionUtil.isEmpty(lstPhase)) {
					List<ApprovalRootCommonOutput> rootOutputs = lstRoots
							.stream()
							.map(x -> new ApprovalRootCommonOutput(x.getCompanyId(),
									x.getApprovalId(), 
									"", 
									"",
									x.getEmploymentAppHistoryItems().get(0).getHistoryId(),
									x.getApplicationType() == null ? 99: x.getApplicationType().value, 
									x.getEmploymentAppHistoryItems().get(0).start(),
									x.getEmploymentAppHistoryItems().get(0).end(),
									x.getBranchId(),
									x.getAnyItemApplicationId(),
									x.getConfirmationRootType() == null ? null: x.getConfirmationRootType().value,
									x.getEmploymentRootAtr().value))
							.collect(Collectors.toList());
					return rootOutputs;
				}
			}
		}
		return null;
	}
	/**
	 * get root by common
	 */
	@Override
	public List<ApprovalRootCommonOutput> commonOfEmployee(List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor,
			List<PersonApprovalRoot> lstPersonRootInfor,
			String companyID, 
			String sId, 
			GeneralDate baseDate) {
		//ドメインモデル「個人別就業承認ルート」を取得する(láy du lieu domain「個人別就業承認ルート」 ): 申請本人の社員ID, 就業ルート区分(共通)
		List<PersonApprovalRoot> lstPsCommonRoots = lstPersonRootInfor
				.stream()
				.filter(x -> x.getEmployeeId().equals(sId) && x.isCommon())
				.collect(Collectors.toList());
		//データが１件以上取得した場合(data >= 1)
		if(!CollectionUtil.isEmpty(lstPsCommonRoots)) {
			List<ApprovalRootCommonOutput> rootOutputs = lstPsCommonRoots
					.stream()
					.map(x -> new ApprovalRootCommonOutput(x.getCompanyId(),
							x.getApprovalId(), 
							x.getEmployeeId(), 
							"",
							x.getEmploymentAppHistoryItems().get(0).getHistoryId(),
							x.getApplicationType() == null ? 99: x.getApplicationType().value, 
							x.getEmploymentAppHistoryItems().get(0).start(),
							x.getEmploymentAppHistoryItems().get(0).end(),
							x.getBranchId(),
							x.getAnyItemApplicationId(),
							x.getConfirmationRootType() == null ? null: x.getConfirmationRootType().value,
							x.getEmploymentRootAtr().value))
					.collect(Collectors.toList());
			return rootOutputs;
		}
		//対象者の所属職場を含める上位職場を取得する(lấy thông tin Affiliation workplace và Upper workplace của nhân viên)
		List<String> findWpkIdsBySid = empAdapter.findWpkIdsBySid(companyID, sId, baseDate);
		//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
		for(String wpId: findWpkIdsBySid) {
			//ドメインモデル「職場別就業承認ルート」を取得する(lấy domain 「職場別就業承認ルート」): 職場ID（ループ中の職場ID）, 就業ルート区分(共通)
			List<WorkplaceApprovalRoot> lstWpCommonRoots = lstWorkpalceRootInfor
					.stream()
					.filter(x -> x.getWorkplaceId().equals(wpId) 
							&& x.isCommon())
					.collect(Collectors.toList());
			//データが１件以上取得した場合(data >= 1)
			if(!CollectionUtil.isEmpty(lstWpCommonRoots)) {
				List<ApprovalRootCommonOutput> rootOutputs = lstWpCommonRoots
						.stream()
						.map(x -> new ApprovalRootCommonOutput(x.getCompanyId(),
								x.getApprovalId(), 
								"", 
								x.getWorkplaceId(),
								x.getEmploymentAppHistoryItems().get(0).getHistoryId(),
								x.getApplicationType() == null ? 99: x.getApplicationType().value, 
								x.getEmploymentAppHistoryItems().get(0).start(),
								x.getEmploymentAppHistoryItems().get(0).end(),
								x.getBranchId(),
								x.getAnyItemApplicationId(),
								x.getConfirmationRootType() == null ? null: x.getConfirmationRootType().value,
								x.getEmploymentRootAtr().value))
						.collect(Collectors.toList());
				return rootOutputs;
			}
		}
		//ドメインモデル「会社別就業承認ルート」を取得する(lấy dữ liệu domain「会社別就業承認ルート」): 就業ルート区分(共通)
		List<CompanyApprovalRoot> lstComRoots = lstCompanyRootInfor.stream()
				.filter(x -> x.isCommon()
						&& x.getCompanyId().equals(companyID))
				.collect(Collectors.toList());
		//データが１件以上取得した場合(data >= 1)
		if(!CollectionUtil.isEmpty(lstComRoots)) {
			List<ApprovalRootCommonOutput> rootOutputs = lstComRoots
					.stream()
					.map(x -> new ApprovalRootCommonOutput(x.getCompanyId(),
							x.getApprovalId(), 
							"", 
							"",
							x.getEmploymentAppHistoryItems().get(0).getHistoryId(),
							x.getApplicationType() == null ? 99: x.getApplicationType().value, 
							x.getEmploymentAppHistoryItems().get(0).start(),
							x.getEmploymentAppHistoryItems().get(0).end(),
							x.getBranchId(),
							x.getAnyItemApplicationId(),
							x.getConfirmationRootType() == null ? null: x.getConfirmationRootType().value,
							x.getEmploymentRootAtr().value))
					.collect(Collectors.toList());
			return rootOutputs;
		}
		
		return null;
	}
	/**
	 * check co loai don xin (application or confirm)
	 * @param confirmType
	 * @param empRootAtr
	 * @param appType
	 * @param appTypes
	 * @return
	 */
	private boolean checkByType(ConfirmationRootType confirmType, EmploymentRootAtr empRootAtr,
			ApplicationType appType, AppTypes appTypes){
		if(appTypes.getEmpRoot() == EmploymentRootAtr.CONFIRMATION.value){
			if(appTypes.getEmpRoot() == empRootAtr.value && appTypes.getCode().equals(confirmType.value)){
				return true;
			}
		}else{
			if(appTypes.getEmpRoot() == empRootAtr.value && appTypes.getCode().equals(appType.value)){
				return true;
			}
		}
		
		return false;
	}

}
