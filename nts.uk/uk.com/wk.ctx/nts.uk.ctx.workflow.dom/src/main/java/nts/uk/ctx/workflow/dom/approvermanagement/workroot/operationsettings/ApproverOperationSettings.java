package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.自分の承認者の運用設定.自分の承認者の運用設定
 * @author NWS-DungDV
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApproverOperationSettings extends AggregateRoot {
	
	/** 運用モード */
	private OperationMode operationMode;
	
	/** 利用するレベル */
	private ApprovalLevelNo approvalLevelNo;
	
	/** 利用する種類設定 */
	private List<SettingTypeUsed> settingTypeUseds;
	
	/** 自分の承認者設定画面に表示情報 */
	private ApproverSettingScreenInfor approverSettingScreenInfor;
	
	/**
	 * inv-1 項目の名称情報は利用するレベルに合わせる	
	 * @param approvalLevelNo 利用するレベル
	 * @param itemNameInformation 項目の名称情報
	 * @return true|false
	 */
	private boolean isItemNameInforMatchLevel() {
		if (approvalLevelNo == ApprovalLevelNo.ONE_LEVEL) {
			return approverSettingScreenInfor.getFirstItemName() != null && !approverSettingScreenInfor.getFirstItemName().v().isEmpty();
		}
		
		if (approvalLevelNo == ApprovalLevelNo.TWO_LEVEL) {
			return (approverSettingScreenInfor.getFirstItemName() != null && !approverSettingScreenInfor.getFirstItemName().v().isEmpty())
				&& (approverSettingScreenInfor.getSecondItemName().isPresent() && !approverSettingScreenInfor.getSecondItemName().get().v().isEmpty());
		}
		
		if (approvalLevelNo == ApprovalLevelNo.THREE_LEVEL) {
			return (approverSettingScreenInfor.getFirstItemName() != null && !approverSettingScreenInfor.getFirstItemName().v().isEmpty())
				&& (approverSettingScreenInfor.getSecondItemName().isPresent() && !approverSettingScreenInfor.getSecondItemName().get().v().isEmpty())
				&& (approverSettingScreenInfor.getThirdItemName().isPresent() && !approverSettingScreenInfor.getThirdItemName().get().v().isEmpty());
		}

		if (approvalLevelNo == ApprovalLevelNo.FOUR_LEVEL) {
			return (approverSettingScreenInfor.getFirstItemName() != null && !approverSettingScreenInfor.getFirstItemName().v().isEmpty())
				&& (approverSettingScreenInfor.getSecondItemName().isPresent() && !approverSettingScreenInfor.getSecondItemName().get().v().isEmpty())
				&& (approverSettingScreenInfor.getThirdItemName().isPresent() && !approverSettingScreenInfor.getThirdItemName().get().v().isEmpty())
				&& (approverSettingScreenInfor.getFourthItemName().isPresent() && !approverSettingScreenInfor.getFourthItemName().get().v().isEmpty());
		}

		if (approvalLevelNo == ApprovalLevelNo.FIVE_LEVEL) {
			return (approverSettingScreenInfor.getFirstItemName() != null && !approverSettingScreenInfor.getFirstItemName().v().isEmpty())
				&& (approverSettingScreenInfor.getSecondItemName().isPresent() && !approverSettingScreenInfor.getSecondItemName().get().v().isEmpty())
				&& (approverSettingScreenInfor.getThirdItemName().isPresent() && !approverSettingScreenInfor.getThirdItemName().get().v().isEmpty())
				&& (approverSettingScreenInfor.getFourthItemName().isPresent() && !approverSettingScreenInfor.getFourthItemName().get().v().isEmpty())
				&& (approverSettingScreenInfor.getFifthItemName().isPresent() && !approverSettingScreenInfor.getFifthItemName().get().v().isEmpty());
		}
		return false;
	}
	
	/**
	 * [C-1] 新規作成する
	 * @param operationMode 運用モード	
	 * @param itemNameInformation 項目の名称情報
	 */
	public ApproverOperationSettings(OperationMode operationMode, ItemNameInformation itemNameInformation) {
		List<SettingTypeUsed> settingTypes = SettingTypeUsed.createWithoutUsingAttr();
		ApproverSettingScreenInfor approverSettingScreenInformation = new ApproverSettingScreenInfor(itemNameInformation);
		this.operationMode = operationMode;
		this.approvalLevelNo = ApprovalLevelNo.FIVE_LEVEL;
		this.settingTypeUseds = settingTypes;
		this.approverSettingScreenInfor = approverSettingScreenInformation;
		
		if (!this.isItemNameInforMatchLevel()) {
			throw new BusinessException("Msg_3311");
		}
	}
	
	/**
	 * [1] 利用する承認フェーズを整理する 
	 * @param approvalPhase 承認フェーズList
	 * @return 表示される承認フェーズList
	 */
	public List<ApprovalPhase> organizeApprovalPhaseToBeUsed(List<ApprovalPhase> approvalPhases) {
		List<Integer> approvalPhaseOrder = approvalLevelNo.determineOrderOfPhases();
		return approvalPhases.stream()
				.filter(x -> approvalPhaseOrder.contains(x.getPhaseOrder()))
				.collect(Collectors.toList());
	}
	
	/**
	 * [2] 利用する申請を取得する
	 */
	public List<ApplicationType> getApplicationToUse() {
		return this.settingTypeUseds.stream()
				.map(x -> x.determineAppTypeIsUsed().orElse(null))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
			
	}
	
	/**
	 * [3] 利用する確認を取得する
	 */
	public List<ConfirmationRootType> getConfirmationToUse() {
		return this.settingTypeUseds.stream()
				.map(x -> x.determineConfirmRootTypeIsUsed().orElse(null))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
