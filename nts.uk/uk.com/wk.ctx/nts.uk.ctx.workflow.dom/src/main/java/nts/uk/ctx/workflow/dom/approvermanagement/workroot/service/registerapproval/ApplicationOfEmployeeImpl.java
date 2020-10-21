package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootCommonOutput;
import nts.uk.ctx.workflow.dom.service.CollectApprovalRootService;
@Stateless
public class ApplicationOfEmployeeImpl implements ApplicationOfEmployee{
	@Inject
	private ApprovalPhaseRepository phaseRespoitory;
	@Inject
	private CollectApprovalRootService collectApprSv;
	/**
	 * 02.社員の対象申請の承認ルートを取得する
	 */
	@Override
	public List<ApprovalRootCommonOutput> appOfEmployee(List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor, List<PersonApprovalRoot> lstPersonRootInfor,
			String companyID, String sId, AppTypes appType, GeneralDate baseDate, int sysAtr) {
		//Lay theo person
		//ドメインモデル「個人別就業承認ルート」(domain 「個人別就業承認ルート」): 申請本人の社員ID, 就業ルート区分(申請か、確認か、任意項目か), 対象申請（３６協定時間申請を除く）(ngoai loai don ３６協定時間申請）
		List<PersonApprovalRoot> lstPsRoots = lstPersonRootInfor
				.stream()
				.filter(x -> x.getEmployeeId().equals(sId) 
						&& this.checkByType(this.typeV(x.getApprRoot()), this.typeS(x.getApprRoot()), x.getApprRoot().getEmploymentRootAtr().value, appType))
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
				phaseRespoitory.getAllIncludeApprovers(y.getApprovalId()).stream().forEach(z -> {
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
								x.getApprRoot().getHistoryItems().get(0).getHistoryId(),
								x.getApprRoot().getApplicationType() == null ? 99: x.getApprRoot().getApplicationType().value, 
								x.getApprRoot().getHistoryItems().get(0).start(),
								x.getApprRoot().getHistoryItems().get(0).end(),
								// x.getApprRoot().getBranchId(),
								// x.getApprRoot().getAnyItemApplicationId(),
								x.getApprRoot().getConfirmationRootType() == null ? null: x.getApprRoot().getConfirmationRootType().value,
								x.getApprRoot().getEmploymentRootAtr().value,
								x.getApprRoot().getNoticeId(),
								x.getApprRoot().getBusEventId()))
						.collect(Collectors.toList());
				return rootOutputs;
			}			
		}
		//対象者の所属職場・部門を含める上位職場・部門を取得する
		List<String> lstWpDepIds = collectApprSv.getUpperIDIncludeSelf(companyID, sId, baseDate,
				EnumAdaptor.valueOf(sysAtr, SystemAtr.class));
		
		//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
		for(String wpId: lstWpDepIds) {
			//ドメインモデル「職場別就業承認ルート」を取得する(lấy domain「職場別就業承認ルート」): 職場ID（ループ中の職場ID）, 就業ルート区分(申請か、確認か、任意項目か), 対象申請（３６協定時間申請を除く）
			List<WorkplaceApprovalRoot> lstWpRoots = lstWorkpalceRootInfor
					.stream()
					.filter(x -> (x.getWorkplaceId().equals(wpId) 
							&& this.checkByType(this.typeV(x.getApprRoot()), this.typeS(x.getApprRoot()), x.getApprRoot().getEmploymentRootAtr().value, appType)))
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
					phaseRespoitory.getAllIncludeApprovers(y.getApprovalId()).stream().forEach(z -> {
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
									x.getApprRoot().getHistoryItems().get(0).getHistoryId(),
									x.getApprRoot().getApplicationType() == null ? 99: x.getApprRoot().getApplicationType().value, 
									x.getApprRoot().getHistoryItems().get(0).start(),
									x.getApprRoot().getHistoryItems().get(0).end(),
									// x.getApprRoot().getBranchId(),
									// x.getApprRoot().getAnyItemApplicationId(),
									x.getApprRoot().getConfirmationRootType() == null ? null: x.getApprRoot().getConfirmationRootType().value,
									x.getApprRoot().getEmploymentRootAtr().value,
									x.getApprRoot().getNoticeId(),
									x.getApprRoot().getBusEventId()))
							.collect(Collectors.toList());
					return rootOutputs;
				}				
				
			}
			
			//ドメインモデル「会社別就業承認ルート」を取得する(lấy dư liệu domain 「会社別就業承認ルート」): 就業ルート区分(申請か、確認か、任意項目か), 対象申請（３６協定時間申請を除く）
			List<CompanyApprovalRoot> lstRoots = lstCompanyRootInfor.stream()
					.filter(x -> x.getCompanyId().equals(companyID)
								&& this.checkByType(this.typeV(x.getApprRoot()), this.typeS(x.getApprRoot()), x.getApprRoot().getEmploymentRootAtr().value, appType))
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
					phaseRespoitory.getAllIncludeApprovers(y.getApprovalId()).stream().forEach(z -> {
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
									x.getApprRoot().getHistoryItems().get(0).getHistoryId(),
									x.getApprRoot().getApplicationType() == null ? 99: x.getApprRoot().getApplicationType().value, 
									x.getApprRoot().getHistoryItems().get(0).start(),
									x.getApprRoot().getHistoryItems().get(0).end(),
									// x.getApprRoot().getBranchId(),
									// x.getApprRoot().getAnyItemApplicationId(),
									x.getApprRoot().getConfirmationRootType() == null ? null: x.getApprRoot().getConfirmationRootType().value,
									x.getApprRoot().getEmploymentRootAtr().value,
									x.getApprRoot().getNoticeId(),
									x.getApprRoot().getBusEventId()))
							.collect(Collectors.toList());
					return rootOutputs;
				}
			}
		}
		return new ArrayList<>();
	}
	/**
	 * 03.社員の共通の承認ルートを取得する
	 */
	@Override
	public List<ApprovalRootCommonOutput> commonOfEmployee(List<CompanyApprovalRoot> lstCompanyRootInfor,
			List<WorkplaceApprovalRoot> lstWorkpalceRootInfor, List<PersonApprovalRoot> lstPersonRootInfor,
			String companyID, String sId, GeneralDate baseDate, int sysAtr) {
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
							x.getApprRoot().getHistoryItems().get(0).getHistoryId(),
							null, 
							x.getApprRoot().getHistoryItems().get(0).start(),
							x.getApprRoot().getHistoryItems().get(0).end(),
							// x.getApprRoot().getBranchId(),
							// x.getApprRoot().getAnyItemApplicationId(),
							null,
							x.getApprRoot().getEmploymentRootAtr().value,
							x.getApprRoot().getNoticeId(),
							x.getApprRoot().getBusEventId()))
					.collect(Collectors.toList());
			return rootOutputs;
		}
		//対象者の所属職場・部門を含める上位職場・部門を取得する
		List<String> lstWpDepIds = collectApprSv.getUpperIDIncludeSelf(companyID, sId, baseDate,
				EnumAdaptor.valueOf(sysAtr, SystemAtr.class));
		//取得した所属職場ID＋その上位職場IDを先頭から最後までループする
		for(String wpId: lstWpDepIds) {
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
								x.getApprRoot().getHistoryItems().get(0).getHistoryId(),
								x.getApprRoot().getApplicationType() == null ? 99: x.getApprRoot().getApplicationType().value, 
								x.getApprRoot().getHistoryItems().get(0).start(),
								x.getApprRoot().getHistoryItems().get(0).end(),
								// x.getApprRoot().getBranchId(),
								// x.getApprRoot().getAnyItemApplicationId(),
								x.getApprRoot().getConfirmationRootType() == null ? null: x.getApprRoot().getConfirmationRootType().value,
								x.getApprRoot().getEmploymentRootAtr().value,
								x.getApprRoot().getNoticeId(),
								x.getApprRoot().getBusEventId()))
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
							x.getApprRoot().getHistoryItems().get(0).getHistoryId(),
							x.getApprRoot().getApplicationType() == null ? 99: x.getApprRoot().getApplicationType().value, 
							x.getApprRoot().getHistoryItems().get(0).start(),
							x.getApprRoot().getHistoryItems().get(0).end(),
							// x.getApprRoot().getBranchId(),
							// x.getApprRoot().getAnyItemApplicationId(),
							x.getApprRoot().getConfirmationRootType() == null ? null: x.getApprRoot().getConfirmationRootType().value,
							x.getApprRoot().getEmploymentRootAtr().value,
							x.getApprRoot().getNoticeId(),
							x.getApprRoot().getBusEventId()))
					.collect(Collectors.toList());
			return rootOutputs;
		}
		return new ArrayList<>();
	}
	/**
	 * check co loai don xin (application or confirm)
	 * @param confirmType
	 * @param empRootAtr
	 * @param appType
	 * @param appTypes
	 * @return
	 */
	private boolean checkByType(Integer typeV, String typeS, int empR, AppTypes appTypes){
		// CMM018 ver10 , do not get 36 type applications
//		if (appTypes.getEmpRoot() != 0 || appTypes.getEmpRoot() != 2 || appTypes.getEmpRoot() != 4 || appTypes.getEmpRoot() != 5) {
//			return false;
//		}
		if(empR == 0) {
			if(appTypes.getEmpRoot() == 0) return true;
		}else if(empR == 5) {
			if(appTypes.getEmpRoot() == 5 && appTypes.getCode().equals(typeS)) return true;
		}else {
			if(appTypes.getEmpRoot() == empR && Integer.valueOf(appTypes.getCode()).equals(typeV)){
				return true;
			}
		}
		return false;
	}

	private String typeS(ApprovalRoot apprRoot) {
		if(apprRoot.getEmploymentRootAtr().equals(EmploymentRootAtr.BUS_EVENT)) {
			return apprRoot.getBusEventId();
		}
		return null;
	}
	private Integer typeV(ApprovalRoot apprRoot) {
		if(apprRoot.getEmploymentRootAtr().equals(EmploymentRootAtr.APPLICATION)) {
			return apprRoot.getApplicationType().value;
		}
		if(apprRoot.getEmploymentRootAtr().equals(EmploymentRootAtr.CONFIRMATION)) {
			return apprRoot.getConfirmationRootType().value;
		}
		if(apprRoot.getEmploymentRootAtr().equals(EmploymentRootAtr.NOTICE)) {
			return apprRoot.getNoticeId();
		}
		return null;
	}
}
