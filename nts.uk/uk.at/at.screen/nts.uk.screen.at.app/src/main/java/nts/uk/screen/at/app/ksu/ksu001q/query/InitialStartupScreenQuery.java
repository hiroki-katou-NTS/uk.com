package nts.uk.screen.at.app.ksu.ksu001q.query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.WorkplaceInfo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 初期起動
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).Q：外部予算入力.メニュー別OCD.
 * 
 * @author thanhlv
 *
 */
@Stateless
public class InitialStartupScreenQuery {

	@Inject
	private WorkplaceGroupAdapter workplaceGroupAdapter;

	@Inject
	private WorkplaceExportServiceAdapter serviceAdapter;

	@Inject
	private AffWorkplaceAdapter wplAdapter;

	@Inject
	private ExternalBudgetRepository externalBudgetRepository;

	/**
	 * 初期起動
	 * 
	 * @param target
	 * @return
	 */
	public InitialStartupScreenResultDto getInfomation(InitialStartupScreenRequest target) {
		LoginUserContext context = AppContexts.user();
		String companyId = context.companyId();

		InitialStartupScreenResultDto dto = new InitialStartupScreenResultDto();

		RequireImpl require = new RequireImpl(workplaceGroupAdapter, serviceAdapter, wplAdapter);

		// 1. 組織の表示情報を取得する(Require, 年月日)
		if (("1").equals(target.getUnit())) {
			TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(target.getId());
			DisplayInfoOrganization infoOrganization = targetOrgIdenInfor.getDisplayInfor(require,
					GeneralDate.fromString(target.getEndDate(), "yyyy/MM/dd"));
			dto.setOrgName(infoOrganization.getDisplayName());
		} else {
			TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace(target.getId());
			DisplayInfoOrganization infoOrganization = targetOrgIdenInfor.getDisplayInfor(require,
					GeneralDate.fromString(target.getEndDate(), "yyyy/MM/dd"));
			dto.setOrgName(infoOrganization.getDisplayName());
		}

		// 2. 取得する
		List<ExternalBudget> budgets = externalBudgetRepository.findAll(companyId);

		List<ExternalBudgetItem> externalBudgetItems = budgets.stream().map(x -> {
			ExternalBudgetItem budgetItem = new ExternalBudgetItem();
			budgetItem.setCode(x.getExternalBudgetCd().v());
			budgetItem.setName(x.getExternalBudgetName().v());
			budgetItem.setAttribute(x.getBudgetAtr().toName());
			budgetItem.setUnit(x.getUnitAtr().toName());
			return budgetItem;
		}).sorted((o1, o2) -> o1.getCode().compareTo(o2.getCode())).collect(Collectors.toList());

		dto.setExternalBudgetItems(externalBudgetItems);

		return dto;
	}

	@AllArgsConstructor
	private class RequireImpl implements TargetOrgIdenInfor.Require {

		@Inject
		private WorkplaceGroupAdapter groupAdapter;

		@Inject
		private WorkplaceExportServiceAdapter serviceAdapter;

		@Inject
		private AffWorkplaceAdapter wplAdapter;

		@Override
		public List<WorkplaceGroupImport> getSpecifyingWorkplaceGroupId(List<String> workplacegroupId) {
			return groupAdapter.getbySpecWorkplaceGroupID(workplacegroupId);
		}

		@Override
		public List<WorkplaceInfo> getWorkplaceInforFromWkpIds(List<String> listWorkplaceId, GeneralDate baseDate) {
			String companyId = AppContexts.user().companyId();
			List<WorkplaceInfo> workplaceInfos = serviceAdapter
					.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate).stream()
					.map(mapper -> new WorkplaceInfo(mapper.getWorkplaceId(),
							Optional.ofNullable(mapper.getWorkplaceCode()),
							Optional.ofNullable(mapper.getWorkplaceName()),
							Optional.ofNullable(mapper.getWorkplaceExternalCode()),
							Optional.ofNullable(mapper.getWorkplaceGenericName()),
							Optional.ofNullable(mapper.getWorkplaceDisplayName()),
							Optional.ofNullable(mapper.getHierarchyCode())))
					.collect(Collectors.toList());
			return workplaceInfos;
		}

		@Override
		public List<String> getWKPID(String WKPGRPID) {
			String CID = AppContexts.user().companyId();
			return wplAdapter.getWKPID(CID, WKPGRPID);
		}
	}
}
