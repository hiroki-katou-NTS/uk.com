package nts.uk.ctx.at.schedule.dom.displaysetting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 組織別スケジュール修正日付別の表示設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.組織別スケジュール修正日付別の表示設定
 * @author hiroko_miura
 *
 */
@Getter
@RequiredArgsConstructor
public class DisplaySettingByDateForOrganization implements DomainAggregate {
	
	/** 対象組織 */
	private final TargetOrgIdenInfor targetOrg;
	
	/** 表示設定 */
	private final DisplaySettingByDate dispSetting;
	
}
