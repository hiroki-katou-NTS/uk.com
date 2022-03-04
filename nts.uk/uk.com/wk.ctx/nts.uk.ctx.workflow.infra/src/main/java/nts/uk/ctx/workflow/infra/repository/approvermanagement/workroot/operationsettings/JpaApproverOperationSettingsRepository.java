package nts.uk.ctx.workflow.infra.repository.approvermanagement.workroot.operationsettings;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApprovalLevelNo;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverInputCareful;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverInputExplanation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverItemName;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettingsRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverSettingScreenInfor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.SettingTypeUsed;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.operationsettings.WwfmtApproverAppUse;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.operationsettings.WwfmtApproverAppUsePK;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.operationsettings.WwfmtApproverOperation;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class JpaApproverOperationSettingsRepository extends JpaRepository implements ApproverOperationSettingsRepository {
	
	/**
	 * Convert ApproverOperationSettings -> WwfmtApproverOperation
	 */
	private WwfmtApproverOperation toEntityOperation(ApproverOperationSettings domain) {
		WwfmtApproverOperation entity = new WwfmtApproverOperation();
		entity.contractCd = AppContexts.user().contractCode();
		entity.cid = AppContexts.user().companyId();
		entity.opeMode = domain.getOperationMode().value;
		entity.levelUseNumber = domain.getApprovalLevelNo().value;
		entity.item1Name = domain.getApproverSettingScreenInfor().getFirstItemName().v();
		if (domain.getApproverSettingScreenInfor().getSecondItemName().isPresent()) {
			entity.item2Name = domain.getApproverSettingScreenInfor().getSecondItemName().get().v();
		}
		if (domain.getApproverSettingScreenInfor().getThirdItemName().isPresent()) {
			entity.item3Name = domain.getApproverSettingScreenInfor().getThirdItemName().get().v();
		}
		if (domain.getApproverSettingScreenInfor().getFourthItemName().isPresent()) {
			entity.item4Name = domain.getApproverSettingScreenInfor().getFourthItemName().get().v();
		}
		if (domain.getApproverSettingScreenInfor().getFifthItemName().isPresent()) {
			entity.item5Name = domain.getApproverSettingScreenInfor().getFifthItemName().get().v();
		}
		if (!domain.getSettingTypeUseds().isEmpty() && domain.getSettingTypeUseds().get(0).getConfirmRootType().isPresent()) {
			ConfirmationRootType confirmRootType = domain.getSettingTypeUseds().get(0).getConfirmRootType().get();
			entity.confDayUse = (confirmRootType == ConfirmationRootType.DAILY_CONFIRMATION) ? 1 : 0;
			entity.confMonthUse = (confirmRootType == ConfirmationRootType.MONTHLY_CONFIRMATION) ? 1 : 0;
		}
		if (domain.getApproverSettingScreenInfor().getProcessMemo().isPresent()) {
			entity.processMemo = domain.getApproverSettingScreenInfor().getProcessMemo().get().v();
		}
		if (domain.getApproverSettingScreenInfor().getAttentionMemo().isPresent()) {
			entity.attentionMemo = domain.getApproverSettingScreenInfor().getAttentionMemo().get().v();
		}
		entity.wwfmtApproverAppUses = domain.getSettingTypeUseds().stream()
				.map(this::toEntityAppUse)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		
		return entity;
	}
	
	/**
	 * Convert SettingTypeUsed -> WwfmtApproverAppUse
	 */
	private WwfmtApproverAppUse toEntityAppUse(SettingTypeUsed domain) {
		if (!domain.getApplicationType().isPresent()) {
			return null;
		}
		WwfmtApproverAppUse wwfmtApproverAppUse = new WwfmtApproverAppUse();
		wwfmtApproverAppUse.pk = new WwfmtApproverAppUsePK(AppContexts.user().companyId(),
				domain.getApplicationType().get().value);
		wwfmtApproverAppUse.contractCd = AppContexts.user().contractCode();
		wwfmtApproverAppUse.useAtr = domain.getNotUseAtr().value;
		return wwfmtApproverAppUse;
	}
	
	private ApproverOperationSettings toDomainOperation(WwfmtApproverOperation entity) {
		OperationMode operationMode = EnumAdaptor.valueOf(entity.opeMode, OperationMode.class);
		ApprovalLevelNo approvalLevelNo = EnumAdaptor.valueOf(entity.levelUseNumber, ApprovalLevelNo.class);
		ApproverSettingScreenInfor approverSettingScreenInfor = new ApproverSettingScreenInfor(
				new ApproverItemName(entity.item1Name),
				Optional.ofNullable(entity.item2Name == null ? null : new ApproverItemName(entity.item2Name)),
				Optional.ofNullable(entity.item3Name == null ? null : new ApproverItemName(entity.item3Name)),
				Optional.ofNullable(entity.item4Name == null ? null : new ApproverItemName(entity.item4Name)),
				Optional.ofNullable(entity.item5Name == null ? null : new ApproverItemName(entity.item5Name)),
				Optional.ofNullable(entity.processMemo == null ? null : new ApproverInputExplanation(entity.processMemo)),
				Optional.ofNullable(entity.attentionMemo == null ? null : new ApproverInputCareful(entity.attentionMemo)));
		
		List<SettingTypeUsed> settingTypeUseds = entity.wwfmtApproverAppUses.stream()
				.map(x -> {
					return new SettingTypeUsed(EmploymentRootAtr.APPLICATION,
						Optional.ofNullable(EnumAdaptor.valueOf(x.pk.appType, ApplicationType.class)),
						Optional.empty(),
						EnumAdaptor.valueOf(x.useAtr, NotUseAtr.class));
				})
				.collect(Collectors.toList());
		
		settingTypeUseds.add(0, new SettingTypeUsed(EmploymentRootAtr.COMMON, Optional.empty(), Optional.empty(), NotUseAtr.USE));
		
		settingTypeUseds.add(new SettingTypeUsed(EmploymentRootAtr.CONFIRMATION,
				Optional.empty(),
				Optional.of(ConfirmationRootType.DAILY_CONFIRMATION),
				EnumAdaptor.valueOf(entity.confDayUse, NotUseAtr.class)));
		
		settingTypeUseds.add(new SettingTypeUsed(EmploymentRootAtr.CONFIRMATION,
				Optional.empty(),
				Optional.of(ConfirmationRootType.MONTHLY_CONFIRMATION),
				EnumAdaptor.valueOf(entity.confMonthUse, NotUseAtr.class)));
		
		return new ApproverOperationSettings(operationMode, approvalLevelNo, settingTypeUseds, approverSettingScreenInfor);
	}
	
	@Override
	public void insert(ApproverOperationSettings domain) {
		WwfmtApproverOperation entity = this.toEntityOperation(domain);
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(ApproverOperationSettings domain) {
		WwfmtApproverOperation entity = this.toEntityOperation(domain);
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<ApproverOperationSettings> get(String cid) {
		Optional<WwfmtApproverOperation> entity = this.queryProxy().find(cid, WwfmtApproverOperation.class);
		if (!entity.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(this.toDomainOperation(entity.get()));
		
	}

	@Override
	public Optional<OperationMode> getOperationOfApproverRegis(String cid) {
		Optional<WwfmtApproverOperation> entity = this.queryProxy().find(cid, WwfmtApproverOperation.class);
		OperationMode opeMode = entity.map(x -> EnumAdaptor.valueOf(x.opeMode, OperationMode.class)).orElse(null);
		return Optional.ofNullable(opeMode);
	}

}
