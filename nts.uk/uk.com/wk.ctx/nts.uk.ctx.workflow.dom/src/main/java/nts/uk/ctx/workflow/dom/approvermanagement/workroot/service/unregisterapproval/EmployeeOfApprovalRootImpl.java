package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
@Stateless
public class EmployeeOfApprovalRootImpl implements EmployeeOfApprovalRoot{
	@Inject
	private CollectApprovalRootService collectApprSv;
	@Inject
	private ApprovalPhaseRepository approvalPhase;
	@Override
	public boolean lstEmpApprovalRoot(String companyId, List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor, List<PersonApprovalRoot> lstPersonRootInfor,
			EmployeeImport empInfor,  String typeV, GeneralDate baseDate, int empR, int sysAtr) {
		//check ドメインモデル「個人別就業承認ルート」(domain 「個人別就業承認ルート」)
		List<PersonApprovalRoot> personRootAll = this.checkExistPs(lstPersonRootInfor, empInfor.getSId(), typeV, empR);
		//co truong hop co root nhung khong co phase
		List<ApprovalPhase> approvalPhases = new ArrayList<>();
		if(!CollectionUtil.isEmpty(personRootAll)) {
			personRootAll.stream().forEach(x -> {
				approvalPhase.getAllApprovalPhasebyCode(x.getApprRoot().getHistoryItems().isEmpty()
						? ""
						: x.getApprRoot().getHistoryItems().get(0).getApprovalId()).stream()
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
					approvalPhase.getAllApprovalPhasebyCode(x.getApprRoot().getHistoryItems().isEmpty()
							? ""
							: x.getApprRoot().getHistoryItems().get(0).getApprovalId()).stream()
					.forEach(y -> {
						approvalPhases.add(y);
					});
				});
			}
			//データが０件(data = 0)
			if(CollectionUtil.isEmpty(psRootCommonAtr)
					|| CollectionUtil.isEmpty(approvalPhases)) {
				
				//対象者の所属職場・部門を含める上位職場・部門を取得する
				List<String> lstWpDepIds = collectApprSv.getUpperIDIncludeSelf(companyId, empInfor.getSId(), baseDate,
						EnumAdaptor.valueOf(sysAtr, SystemAtr.class));
				
				if(!CollectionUtil.isEmpty(lstWpDepIds)) {
					//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
					for(String WpId: lstWpDepIds) {
						//ドメインモデル「職場別就業承認ルート」を取得する(lấy domain「職場別就業承認ルート」)  ※ 就業ルート区分(申請か、確認か、任意項目か)
						List<WorkplaceApprovalRoot> wpRootAllAtr = this.checkExistWp(lstWorkpalceRootInfor, WpId, typeV, empR);
						if(!CollectionUtil.isEmpty(wpRootAllAtr)) {
							wpRootAllAtr.stream().forEach(x -> {
								approvalPhase.getAllApprovalPhasebyCode(x.getApprovalId()).stream()
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
		List<CompanyApprovalRoot> companyRootAll = this.checkExistCom(lstCompanyRootInfor, typeV, empR);
		if(!CollectionUtil.isEmpty(companyRootAll)) {
			companyRootAll.stream().forEach(x -> {
				approvalPhase.getAllApprovalPhasebyCode(x.getApprovalId()).stream()
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
					approvalPhase.getAllApprovalPhasebyCode(x.getApprovalId()).stream()
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

	private List<PersonApprovalRoot> checkExistPs(List<PersonApprovalRoot> lstPs, String sid, String typeV, int empR) {
		List<PersonApprovalRoot> personRootAll = lstPs.stream()
				.filter(x -> x.getEmployeeId().equals(sid))
				.filter(x -> x.getApprRoot().getEmploymentRootAtr().value == empR)
				.collect(Collectors.toList());
		List<PersonApprovalRoot> lstR = new ArrayList<>();
		switch(empR) {
			case 0 :
				lstR = personRootAll;
				break;
			case 1:
				lstR = personRootAll.stream().filter(c -> c.getApprRoot().getApplicationType().value.equals(Integer.valueOf(typeV)))
					.collect(Collectors.toList());
				break;
			case 2: 
				lstR = personRootAll.stream().filter(c -> c.getApprRoot().getConfirmationRootType().value.equals(Integer.valueOf(typeV)))
					.collect(Collectors.toList());
				break;
			case 4: 
				lstR = personRootAll.stream().filter(c -> c.getApprRoot().getNoticeId().equals(Integer.valueOf(typeV)))
					.collect(Collectors.toList());
				break;
			case 5: 
				lstR = personRootAll.stream().filter(c -> c.getApprRoot().getBusEventId().equals(typeV))
					.collect(Collectors.toList());
				break;
		}
		return lstR;
	}
	private List<CompanyApprovalRoot> checkExistCom(List<CompanyApprovalRoot> lstCom, String typeV, int empR) {
		List<CompanyApprovalRoot> comRootAll = lstCom.stream()
				.filter(x -> x.getApprRoot().getEmploymentRootAtr().value == empR)
				.collect(Collectors.toList());
		List<CompanyApprovalRoot> lstR = new ArrayList<>();
		switch(empR) {
			case 0 :
				lstR = comRootAll;
				break;
			case 1:
				lstR = comRootAll.stream().filter(c -> c.getApprRoot().getApplicationType().value.equals(Integer.valueOf(typeV)))
					.collect(Collectors.toList());
				break;
			case 2: 
				lstR = comRootAll.stream().filter(c -> c.getApprRoot().getConfirmationRootType().value.equals(Integer.valueOf(typeV)))
					.collect(Collectors.toList());
				break;
			case 4: 
				lstR = comRootAll.stream().filter(c -> c.getApprRoot().getNoticeId().equals(Integer.valueOf(typeV)))
					.collect(Collectors.toList());
				break;
			case 5: 
				lstR = comRootAll.stream().filter(c -> c.getApprRoot().getBusEventId().equals(typeV))
					.collect(Collectors.toList());
				break;
		}
		return lstR;
	}
	private List<WorkplaceApprovalRoot> checkExistWp(List<WorkplaceApprovalRoot> lstWp, String wkpId, String typeV, int empR) {
		List<WorkplaceApprovalRoot> wpRootAll = lstWp.stream()
				.filter(x -> x.getWorkplaceId().equals(wkpId))
				.filter(x -> x.getApprRoot().getEmploymentRootAtr().value == empR)
				.collect(Collectors.toList());
		List<WorkplaceApprovalRoot> lstR = new ArrayList<>();
		switch(empR) {
			case 0 :
				lstR = wpRootAll;
				break;
			case 1:
				lstR = wpRootAll.stream().filter(c -> c.getApprRoot().getApplicationType().value.equals(Integer.valueOf(typeV)))
					.collect(Collectors.toList());
				break;
			case 2: 
				lstR = wpRootAll.stream().filter(c -> c.getApprRoot().getConfirmationRootType().value.equals(Integer.valueOf(typeV)))
					.collect(Collectors.toList());
				break;
			case 4: 
				lstR = wpRootAll.stream().filter(c -> c.getApprRoot().getNoticeId().equals(Integer.valueOf(typeV)))
					.collect(Collectors.toList());
				break;
			case 5: 
				lstR = wpRootAll.stream().filter(c -> c.getApprRoot().getBusEventId().equals(typeV))
					.collect(Collectors.toList());
				break;
		}
		return lstR;
	}
}
