/**
 * 11:14:22 AM Nov 8, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.worktime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * @author hungnm
 *
 */
// 対象の就業時間帯
public class TargetWorkTime extends DomainObject {

	// しぼり込む
	private boolean filterAtr;

	// 対象の就業時間帯一覧
	@Getter
	private List<WorkTimeCode> lstWorkTime;

	private TargetWorkTime(boolean filterAtr, List<WorkTimeCode> lstWorkTime) {
		super();
		this.filterAtr = filterAtr;
		this.lstWorkTime = lstWorkTime;
	}

	public static TargetWorkTime createFromJavaType(boolean filterAtr, List<String> lstWorkTime) {
		return new TargetWorkTime(filterAtr, lstWorkTime.stream().map((code) -> {
			return new WorkTimeCode(code);
		}).collect(Collectors.toList()));
	}

	public boolean contains(WorkTimeCode target) {
		if (this.isUse()) {
			return this.lstWorkTime.contains(target);
		}
		return false;
	}
	
	public boolean isUse() {
		return this.filterAtr;
	}
	
	public void clearDuplicate() {
		this.lstWorkTime = this.lstWorkTime.stream().distinct().collect(Collectors.toList());
	}
}
