package nts.uk.ctx.at.function.app.find.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.function.dom.holidaysremaining.SpecialHoliday;

/**
 * 出力する特別休暇
 */
@AllArgsConstructor
@Value
public class SpecialHolidayDto {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * コード
	 */
	private String cd;

	/**
	 * コード
	 */
	private String specialCd;

	public static SpecialHolidayDto fromDomain(SpecialHoliday domain) {
		return new SpecialHolidayDto(domain.getCode(), domain.getCompanyID(), domain.getHolidayCode());
	}

}
