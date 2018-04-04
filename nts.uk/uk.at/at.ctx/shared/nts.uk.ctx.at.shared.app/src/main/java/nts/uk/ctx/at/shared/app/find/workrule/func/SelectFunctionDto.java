package nts.uk.ctx.at.shared.app.find.workrule.func;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectFunctionDto {
	/** 変形労働を使用する */
	private int useAggDeformedSetting;
	/** 複数回勤務管理 */
	private int useWorkManagementMultiple;
	/** 臨時勤務利用管理 */
	private int useTempWorkUse;
	/** フレックス勤務の設定 */
	private int flexWorkManagement;
}
