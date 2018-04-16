package nts.uk.ctx.at.function.app.find.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.function.dom.holidaysremaining.SpecialHolidayOutput;

/**
 * 出力する特別休暇
 */
@AllArgsConstructor
@Value
public class SpecialHolidayOutputDto {

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

	public static SpecialHolidayOutputDto fromDomain(SpecialHolidayOutput domain) {
		return new SpecialHolidayOutputDto(domain.getCode(), domain.getCompanyID(), domain.getHolidayCode());
	}

}
