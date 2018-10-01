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
	/**メールを送信する(本人)*/
	private Optional<Boolean> mailPrincipal;
	/**メールを送信する(管理者)*/
	private Optional<Boolean> mailAdministrator;

	public AlarmExtraction(boolean alarmAtr, AlarmPatternCode alarmCode,Boolean mailPrincipal,
			Boolean mailAdministrator) {
		super();
		this.alarmAtr = alarmAtr;
		this.alarmCode = Optional.ofNullable(alarmCode);
		this.mailPrincipal = Optional.ofNullable(mailPrincipal);
		this.mailAdministrator = Optional.ofNullable(mailAdministrator);
	}
	
	
	
}
