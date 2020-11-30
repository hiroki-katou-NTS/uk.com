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
	 * The Alarm extraction classification.<br>
	 * アラーム抽出区分
	 */
	private NotUseAtr alarmExtractionCls;

	/**
	 * The Alarm code.<br>
	 * コード
	 */
	private Optional<AlarmPatternCode> alarmCode;

	/**
	 * The Mail principal.<br>
	 * メールを送信する(本人)
	 */
	private Optional<Boolean> mailPrincipal;

	/**
	 * The Mail administrator.<br>
	 * メールを送信する(管理者)
	 */
	private Optional<Boolean> mailAdministrator;

	/**
	 * The Display on top page administrator.<br>
	 * トップページに表示(管理者)
	 **/
	private Optional<Boolean> displayOnTopPageAdministrator;

	/**
	 * The Display on top page principal.<br>
	 * トップページに表示(本人)
	 **/
	private Optional<Boolean> displayOnTopPagePrincipal;

	/**
	 * Instantiates a new Alarm extraction.
	 *
	 * @param alarmExtractionCls            the alarm extraction classification
	 * @param alarmCode                     the alarm code
	 * @param mailPrincipal                 the mail principal
	 * @param mailAdministrator             the mail administrator
	 * @param displayOnTopPageAdministrator the display on top page administrator
	 * @param displayOnTopPagePrincipal     the display on top page principal
	 */
	public AlarmExtraction(boolean alarmExtractionCls, String alarmCode, Boolean mailPrincipal, Boolean mailAdministrator,
						   Boolean displayOnTopPageAdministrator, Boolean displayOnTopPagePrincipal) {
		super();
		this.alarmExtractionCls = alarmExtractionCls ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.alarmCode = Optional.ofNullable(alarmCode).map(AlarmPatternCode::new);
		this.mailPrincipal = Optional.ofNullable(mailPrincipal);
		this.mailAdministrator = Optional.ofNullable(mailAdministrator);
		this.displayOnTopPageAdministrator = Optional.ofNullable(displayOnTopPageAdministrator);
		this.displayOnTopPagePrincipal = Optional.ofNullable(displayOnTopPagePrincipal);
	}

	/**
	 * Instantiates a new Alarm extraction.
	 *
	 * @param alarmExtractionCls            the alarm extraction classification
	 * @param alarmCode                     the alarm code
	 * @param mailPrincipal                 the mail principal
	 * @param mailAdministrator             the mail administrator
	 * @param displayOnTopPageAdministrator the display on top page administrator
	 * @param displayOnTopPagePrincipal     the display on top page principal
	 */
	public AlarmExtraction(int alarmExtractionCls, String alarmCode, Integer mailPrincipal, Integer mailAdministrator,
						   Integer displayOnTopPageAdministrator, Integer displayOnTopPagePrincipal) {
		super();
		this.alarmExtractionCls = EnumAdaptor.valueOf(alarmExtractionCls, NotUseAtr.class);
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
