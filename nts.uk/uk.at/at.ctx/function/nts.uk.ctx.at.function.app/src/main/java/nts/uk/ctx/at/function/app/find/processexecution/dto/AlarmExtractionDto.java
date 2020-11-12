package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.AlarmExtraction;

/**
 * The class Alarm extraction dto.<br>
 * Dto アラーム抽出
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class AlarmExtractionDto {

	/**
	 * アラーム抽出区分
	 */
	private boolean alarmAtr;

	/**
	 * コード
	 */
	private String alarmCode;

	/**
	 * メールを送信する(本人)
	 */
	private Boolean mailPrincipal;

	/**
	 * メールを送信する(管理者)
	 */
	private Boolean mailAdministrator;

	/**
	 * トップページに表示(本人)
	 **/
	private Boolean displayOnTopPageAdministrator;

	/**
	 * トップページに表示(管理者)
	 **/
	private Boolean displayOnTopPagePrincipal;

	/**
	 * No args constructor.
	 */
	private AlarmExtractionDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Alarm extraction dto
	 */
	public static AlarmExtractionDto createFromDomain(AlarmExtraction domain) {
		if (domain == null) {
			return null;
		}
		AlarmExtractionDto dto = new AlarmExtractionDto();
		dto.alarmAtr = domain.getAlarmAtr().value == 1;
		dto.alarmCode = domain.getAlarmCode().map(AlarmPatternCode::v).orElse(null);
		dto.mailPrincipal = domain.getMailPrincipal().orElse(null);
		dto.mailAdministrator = domain.getMailAdministrator().orElse(null);
		dto.displayOnTopPageAdministrator = domain.getDisplayOnTopPageAdministrator().orElse(null);
		dto.displayOnTopPagePrincipal = domain.getDisplayOnTopPagePrincipal().orElse(null);
		return dto;
	}

}
