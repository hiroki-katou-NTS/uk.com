package nts.uk.ctx.at.shared.dom.worktimeset.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class FlowWorkRestSettingDetail.
 */
// 流動勤務の休憩設定詳細
@Getter
public class FlowWorkRestSettingDetail extends DomainObject {

	/** The flow rest setting. */
	// 流動休憩設定
	//private FlowRestSet flowRestSetting;

	/** The flow fixed rest setting. */
	// 流動固定休憩設定
	private FlowFixedRestSet flowFixedRestSetting;

	/** The use plural work rest time. */
	// 複数回勤務の間を休憩時間として扱う
	private boolean usePluralWorkRestTime;
}