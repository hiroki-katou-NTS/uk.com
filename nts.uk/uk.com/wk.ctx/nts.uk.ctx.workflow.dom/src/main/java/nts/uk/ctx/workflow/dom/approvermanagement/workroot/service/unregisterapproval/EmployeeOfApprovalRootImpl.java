package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
@Stateless
public class EmployeeOfApprovalRootImpl implements EmployeeOfApprovalRoot{
	@Inject
	private EmployeeAdapter employeeApproveAdapter;
	@Inject
	private ApprovalPhaseRepository approvalPhase;
	@Override
	public boolean lstEmpApprovalRoot(String companyId,
			List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor,
			List<PersonApprovalRoot> lstPersonRootInfor,
			EmployeeImport empInfor, 
			ApplicationType appType,
			GeneralDate baseDate) {
		//check ドメインモデル「個人別就業承認ルート」(domain 「個人別就業承認ルート」) ※ 就業ルート区分(申請か、確認か、任意項目か)
		List<PersonApprovalRoot> personRootAll = lstPersonRootInfor.stream()
				.filter(x -> x.getEmployeeId().equals(empInfor.getSId()))
				.filter(x -> x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION
						|| x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.CONFIRMATION
						|| x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.ANYITEM)
				.filter(x -> x.getApprRoot().getApplicationType() == appType)
				.collect(Collectors.toList());
		//co truong hop co root nhung khong co phase
		List<ApprovalPhase> approvalPhases = new ArrayList<>();
		if(!CollectionUtil.isEmpty(personRootAll)) {
			personRootAll.stream().forEach(x -> {
				approvalPhase.getAllApprovalPhasebyCode(companyId, x.getApprRoot().getBranchId()).stream()
				.forEach(y -> {
					approvalPhases.add(y);
				});
			});
		}
		//データが０件(data = 0)
		if(CollectionUtil.isEmpty(personRootAll)
				|| CollectionUtil.isEmpty(approvalPhases)) {
			//check ドメインモデル「個人別就業承認ルート」を取得する(láy du lieu domain「個人別就業承認ルート」 ) ※・就業ルート区分(共通)			
			List<PersonApprovalRoot> psRootCommonAtr = lstPersonRootInfor.stream()
					.filter(x -> x.getEmployeeId().equals(empInfor.getSId()))
					.filter(x -> x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.COMMON)
					.collect(Collectors.toList());
			if(!CollectionUtil.isEmpty(psRootCommonAtr)) {
				psRootCommonAtr.stream().forEach(x -> {
					approvalPhase.getAllApprovalPhasebyCode(companyId, x.getApprRoot().getBranchId()).stream()
					.forEach(y -> {
						approvalPhases.add(y);
					});
				});
			}
			//データが０件(data = 0)
			if(CollectionUtil.isEmpty(psRootCommonAtr)
					|| CollectionUtil.isEmpty(approvalPhases)) {
				//対象者の所属職場を含める上位職場を取得する(lấy thông tin Affiliation workplace và Upper workplace của nhân viên)
				List<String> lstWpIds = employeeApproveAdapter.findWpkIdsBySid(companyId, empInfor.getSId(), baseDate);
				if(!CollectionUtil.isEmpty(lstWpIds)) {
					//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
					for(String WpId: lstWpIds) {
						//ドメインモデル「職場別就業承認ルート」を取得する(lấy domain「職場別就業承認ルート」)  ※ 就業ルート区分(申請か、確認か、任意項目か)
						List<WorkplaceApprovalRoot> wpRootAllAtr = lstWorkpalceRootInfor
								.stream()
								.filter(x -> x.getWorkplaceId().contains(WpId))
								.filter(x -> x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION
										||x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.CONFIRMATION
										|| x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.ANYITEM)
								.filter(x -> x.getApprRoot().getApplicationType() == appType)
								.collect(Collectors.toList());
						if(!CollectionUtil.isEmpty(wpRootAllAtr)) {
							wpRootAllAtr.stream().forEach(x -> {
								approvalPhase.getAllApprovalPhasebyCode(companyId, x.getApprRoot().getBranchId()).stream()
								.forEach(y -> {
									approvalPhases.add(y);
								});
							});
						}
						//データが０件(data = 0)
						if(CollectionUtil.isEmpty(wpRootAllAtr)
								|| CollectionUtil.isEmpty(approvalPhases)) {
							//ドメインモデル「職場別就業承認ルート」を取得する(lấy domain 「職場別就業承認ルート」)  ※・就業ルート区分(共通)
							List<WorkplaceApprovalRoot> wpRootAppAtr = lstWorkpalceRootInfor
									.stream()
									.filter(x -> x.getWorkplaceId().contains(WpId))
									.filter(x -> x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.COMMON)
									.collect(Collectors.toList());
							//データが１件以上取得した場合(data >= 1)
							if(!CollectionUtil.isEmpty(wpRootAppAtr)) {
								//終了状態：承認ルートあり＋承認ルートのデータ(trang thai ket thuc: co approval route + du lieu approval route)
								return true;
							}							
						}else {
							//終了状態：承認ルートあり＋承認ルートのデータ(trang thai ket thuc: co approval route + du lieu approval route)
							return true;
						}
					}
				}else {
					//Trong EAP chua co xu ly cho truong hop nay
					return true;
				}
			}else {
				//終了状態：承認ルートあり＋承認ルートのデータ(trang thai ket thuc: co approval route + du lieu approval route)
				return true;
			}
		}else {
			//co du lieu nhung ko co phase
			//終了状態：承認ルートあり＋承認ルートのデータ(trang thai ket thuc: co approval route + du lieu approval route)
			return true;
		}
		//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
		//データが０件(data = 0)
		//ドメインモデル「会社別就業承認ルート」を取得する(lấy dư liệu domain 「会社別就業承認ルート」) ※ 就業ルート区分(申請か、確認か、任意項目か)
		List<CompanyApprovalRoot> companyRootAll = lstCompanyRootInfor.stream()
				.filter(x -> x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION
						|| x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.CONFIRMATION 
						|| x.getApprRoot().getEmploymentRootAtr() ==EmploymentRootAtr.ANYITEM)
				.filter(x -> x.getApprRoot().getApplicationType() == appType)
				.collect(Collectors.toList());
		if(!CollectionUtil.isEmpty(companyRootAll)) {
			companyRootAll.stream().forEach(x -> {
				approvalPhase.getAllApprovalPhasebyCode(companyId, x.getApprRoot().getBranchId()).stream()
				.forEach(y -> {
					approvalPhases.add(y);
				});
			});
		}
		//データが０件(data = 0)
		if(CollectionUtil.isEmpty(companyRootAll)
				||CollectionUtil.isEmpty(approvalPhases)) {
			//ドメインモデル「会社別就業承認ルート」を取得する(lấy dữ liệu domain「会社別就業承認ルート」)  ※・就業ルート区分(共通)
			List<CompanyApprovalRoot> companyRootCommonAtr = lstCompanyRootInfor.stream()
					.filter(x -> x.getApprRoot().getEmploymentRootAtr() == EmploymentRootAtr.COMMON)
					.collect(Collectors.toList());
			if(!CollectionUtil.isEmpty(companyRootCommonAtr)) {
				companyRootCommonAtr.stream().forEach(x -> {
					approvalPhase.getAllApprovalPhasebyCode(companyId, x.getApprRoot().getBranchId()).stream()
					.forEach(y -> {
						approvalPhases.add(y);
					});
				});
			}
			if(CollectionUtil.isEmpty(companyRootCommonAtr)
					||CollectionUtil.isEmpty(approvalPhases)) {
				//終了状態：承認ルートなし(trang thai ket thuc : khong co approval route)
				return false;
			}else {
				//終了状態：承認ルートあり＋承認ルートのデータ(trang thai ket thuc: co approval route + du lieu approval route)
				return true;
			}
		}else {
			//終了状態：承認ルートあり＋承認ルートのデータ(trang thai ket thuc: co approval route + du lieu approval route)
			return true;
		}
	}

}
