package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.AttendanceClock;
/**
 * 事前の受付制限
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class BeforehandRestriction extends DomainObject {
	
	/**
	 * チェック方法
	 */
	private BeforeAddCheckMethod methodCheck;
	
	/**
	 * 利用する
	 */
	private Boolean toUse;
	
	/**
	 * 日数
	 */
	private AppAcceptLimitDay dateBeforehandRestriction;
	
	/**
	 * 時刻
	 */
	private AttendanceClock timeBeforehandRestriction;
	
}
