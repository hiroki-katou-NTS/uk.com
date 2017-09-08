package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
@Stateless
public class EmployeeOfApprovalRootImpl implements EmployeeOfApprovalRoot{
	@Inject
	private EmployeeApproveAdapter employeeApproveAdapter;
	@Override
	public boolean lstEmpApprovalRoot(String companyId,
			List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor,
			List<PersonApprovalRoot> lstPersonRootInfor,
			EmployeeApproveDto empInfor, 
			ApplicationType appType,
			GeneralDate baseDate) {
		//check ドメインモデル「個人別就業承認ルート」(domain 「個人別就業承認ルート」) ※ 就業ルート区分(申請か、確認か、任意項目か)
		List<PersonApprovalRoot> personRootAll = lstPersonRootInfor.stream()
				.filter(x -> x.getEmployeeId().equals(empInfor.getSId()))
				.filter(x -> x.getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION
						|| x.getEmploymentRootAtr() == EmploymentRootAtr.CONFIRMATION
						|| x.getEmploymentRootAtr() == EmploymentRootAtr.ANYITEM)
				.filter(x -> x.getApplicationType() == appType)
				.collect(Collectors.toList());
				
		//データが０件(data = 0)
		if(CollectionUtil.isEmpty(personRootAll)) {
			//check ドメインモデル「個人別就業承認ルート」を取得する(láy du lieu domain「個人別就業承認ルート」 ) ※・就業ルート区分(共通)			
			List<PersonApprovalRoot> psRootCommonAtr = lstPersonRootInfor.stream()
					.filter(x -> x.getEmployeeId().equals(empInfor.getSId()))
					.filter(x -> x.getEmploymentRootAtr() == EmploymentRootAtr.COMMON)
					.collect(Collectors.toList());
			//データが０件(data = 0)
			if(CollectionUtil.isEmpty(psRootCommonAtr)) {
				//対象者の所属職場を含める上位職場を取得する(lấy thông tin Affiliation workplace và Upper workplace của nhân viên)
				List<String> lstWpIds = employeeApproveAdapter.findWpkIdsBySid(companyId, empInfor.getSId(), baseDate);
				if(!CollectionUtil.isEmpty(lstWpIds)) {
					//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
					for(String WpId: lstWpIds) {
						//ドメインモデル「職場別就業承認ルート」を取得する(lấy domain「職場別就業承認ルート」)  ※ 就業ルート区分(申請か、確認か、任意項目か)
						List<WorkplaceApprovalRoot> wpRootAllAtr = lstWorkpalceRootInfor
								.stream()
								.filter(x -> x.getWorkplaceId().contains(WpId))
								.filter(x -> x.getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION
										||x.getEmploymentRootAtr() == EmploymentRootAtr.CONFIRMATION
										|| x.getEmploymentRootAtr() == EmploymentRootAtr.ANYITEM)
								.collect(Collectors.toList());
						//データが０件(data = 0)
						if(CollectionUtil.isEmpty(wpRootAllAtr)) {
							//ドメインモデル「職場別就業承認ルート」を取得する(lấy domain 「職場別就業承認ルート」)  ※・就業ルート区分(共通)
							List<WorkplaceApprovalRoot> wpRootAppAtr = lstWorkpalceRootInfor
									.stream()
									.filter(x -> x.getWorkplaceId().contains(WpId))
									.filter(x -> x.getEmploymentRootAtr() == EmploymentRootAtr.COMMON)
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
			//終了状態：承認ルートあり＋承認ルートのデータ(trang thai ket thuc: co approval route + du lieu approval route)
			return true;
		}
		//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
		//データが０件(data = 0)
		//ドメインモデル「会社別就業承認ルート」を取得する(lấy dư liệu domain 「会社別就業承認ルート」) ※ 就業ルート区分(申請か、確認か、任意項目か)
		List<CompanyApprovalRoot> companyRootAll = lstCompanyRootInfor.stream()
				.filter(x -> x.getEmploymentRootAtr() == EmploymentRootAtr.APPLICATION
						|| x.getEmploymentRootAtr() == EmploymentRootAtr.CONFIRMATION 
						|| x.getEmploymentRootAtr() ==EmploymentRootAtr.ANYITEM)
				.collect(Collectors.toList());
		//データが０件(data = 0)
		if(CollectionUtil.isEmpty(companyRootAll)) {
			//ドメインモデル「会社別就業承認ルート」を取得する(lấy dữ liệu domain「会社別就業承認ルート」)  ※・就業ルート区分(共通)
			List<CompanyApprovalRoot> companyRootCommonAtr = lstCompanyRootInfor.stream()
					.filter(x -> x.getEmploymentRootAtr() == EmploymentRootAtr.COMMON)
					.collect(Collectors.toList());
			if(companyRootCommonAtr.isEmpty()) {
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
