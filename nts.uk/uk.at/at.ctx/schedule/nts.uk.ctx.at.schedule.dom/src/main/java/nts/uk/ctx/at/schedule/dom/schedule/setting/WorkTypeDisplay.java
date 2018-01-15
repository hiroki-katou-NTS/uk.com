package nts.uk.ctx.at.schedule.dom.schedule.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 表示可能勤務種類制御
 * 
 * @author sonnh1
 *
 */
@Getter
@AllArgsConstructor
public class WorkTypeDisplay extends AggregateRoot {
	private String companyId;
	// 制御使用区分
	private UseAtr useAtr;
}
