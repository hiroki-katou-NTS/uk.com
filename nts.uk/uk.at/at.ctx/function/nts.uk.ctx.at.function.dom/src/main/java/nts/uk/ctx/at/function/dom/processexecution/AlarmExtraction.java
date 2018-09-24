package nts.uk.ctx.at.function.dom.processexecution;
/**
 * Domain : アラーム抽出
 * @author tutk
 *
 */

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;

@Getter
public class AlarmExtraction extends DomainObject {
	/**アラーム抽出区分*/
	private boolean alarmAtr;
	/**コード*/
	private Optional<AlarmPatternCode> alarmCode;
	public AlarmExtraction(boolean alarmAtr, AlarmPatternCode alarmCode) {
		super();
		this.alarmAtr = alarmAtr;
		this.alarmCode = Optional.ofNullable(alarmCode);
	}
	
	
}
