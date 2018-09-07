package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SociInsuStanDate;

/**
 * 社会保険基準年月日
 */
@AllArgsConstructor
@Value
public class SociInsuStanDateDto {

	/**
	 * 基準月
	 */
	private int baseMonth;

	/**
	 * 基準年
	 */
	private int baseYear;

	/**
	 * 基準日
	 */
	private int refeDate;

	public static SociInsuStanDateDto fromDomain(SociInsuStanDate domain) {
		return new SociInsuStanDateDto(domain.getSociInsuBaseMonth().value, domain.getSociInsuBaseYear().value,
				domain.getSociInsuRefeDate().value);
	}

}
