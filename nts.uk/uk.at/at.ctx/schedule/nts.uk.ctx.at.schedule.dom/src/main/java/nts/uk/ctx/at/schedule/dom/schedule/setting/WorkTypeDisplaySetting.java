package nts.uk.ctx.at.schedule.dom.schedule.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 表示可能勤務種類制御設定
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class WorkTypeDisplaySetting extends DomainObject {
	private String companyId;
	private String workTypeCode;
}
