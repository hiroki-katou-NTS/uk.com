package nts.uk.ctx.at.request.dom.setting.company.request.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 申請反映設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AppReflectionSetting extends DomainObject {
	
	/**
	 * 事前申請スケジュール反映
	 */
	private Boolean scheReflectFlg;
	
	/**
	 * 反映時刻優先
	 */
	private PriorityTimeReflectAtr priorityTimeReflectFlag;
	
	/**
	 * 外出打刻漏れの箇所へ出勤退勤時刻を反映する
	 */
	private Boolean attendentTimeReflectFlg;
	
}
