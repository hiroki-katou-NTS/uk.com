package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.Optional;

/**
 * The class Alarm extraction.<br>
 * Domain アラーム抽出
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AlarmExtraction extends DomainObject {

	/**
	 * アラーム抽出区分
	 */
	private NotUseAtr alarmAtr;

	/**
	 * コード
	 */
	private Optional<AlarmPatternCode> alarmCode;

	/**
	 * メールを送信する(本人)
	 */
	private Optional<Boolean> mailPrincipal;

	/**
	 * メールを送信する(管理者)
	 */
	private Optional<Boolean> mailAdministrator;

	/**
	 * トップページに表示(本人)
	 **/
	private Optional<Boolean> displayOnTopPageAdministrator;

	/**
	 * トップページに表示(管理者)
	 **/
	private Optional<Boolean> displayOnTopPagePrincipal;

	/**
	 * Instantiates a new Alarm extraction.
	 *
	 * @param alarmAtr                      the alarm atr
	 * @param alarmCode                     the alarm code
	 * @param mailPrincipal                 the mail principal
	 * @param mailAdministrator             the mail administrator
	 * @param displayOnTopPageAdministrator the display on top page administrator
	 * @param displayOnTopPagePrincipal     the display on top page principal
	 */
	public AlarmExtraction(NotUseAtr alarmAtr, AlarmPatternCode alarmCode, Boolean mailPrincipal,
						   Boolean mailAdministrator, Boolean displayOnTopPageAdministrator, Boolean displayOnTopPagePrincipal) {
		super();
		this.alarmAtr = alarmAtr;
		this.alarmCode = Optional.ofNullable(alarmCode);
		this.mailPrincipal = Optional.ofNullable(mailPrincipal);
		this.mailAdministrator = Optional.ofNullable(mailAdministrator);
		this.displayOnTopPageAdministrator = Optional.ofNullable(displayOnTopPageAdministrator);
		this.displayOnTopPagePrincipal = Optional.ofNullable(displayOnTopPagePrincipal);
	}

	/**
	 * Instantiates a new Alarm extraction.
	 *
	 * @param alarmAtr                      the alarm atr
	 * @param alarmCode                     the alarm code
	 * @param mailPrincipal                 the mail principal
	 * @param mailAdministrator             the mail administrator
	 * @param displayOnTopPageAdministrator the display on top page administrator
	 * @param displayOnTopPagePrincipal     the display on top page principal
	 */
	public AlarmExtraction(int alarmAtr, String alarmCode, Integer mailPrincipal, Integer mailAdministrator,
						   Integer displayOnTopPageAdministrator, Integer displayOnTopPagePrincipal) {
		super();
		this.alarmAtr = EnumAdaptor.valueOf(alarmAtr, NotUseAtr.class);
		this.alarmCode = Optional.ofNullable(alarmCode).map(AlarmPatternCode::new);
		this.mailPrincipal = this.convertIntToOptBoolean(mailPrincipal);
		this.mailAdministrator = this.convertIntToOptBoolean(mailAdministrator);
		this.displayOnTopPageAdministrator = this.convertIntToOptBoolean(displayOnTopPageAdministrator);
		this.displayOnTopPagePrincipal = this.convertIntToOptBoolean(displayOnTopPagePrincipal);
	}

	/**
	 * Convert int to optional of Boolean.
	 *
	 * @param intValue the int value
	 * @return the optional of Boolean
	 */
	private Optional<Boolean> convertIntToOptBoolean(Integer intValue) {
		return Optional.ofNullable(intValue).map(value -> value == 1);
	}

}
