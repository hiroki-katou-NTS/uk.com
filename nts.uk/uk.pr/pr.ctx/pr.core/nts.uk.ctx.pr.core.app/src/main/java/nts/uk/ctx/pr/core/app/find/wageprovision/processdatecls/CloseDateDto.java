package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CloseDate;

/**
 * 勤怠締め年月日
 */
@AllArgsConstructor
@Value
public class CloseDateDto {

	/**
	 * 勤怠締め日
	 */
	private int timeCloseDate;

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

	public static CloseDateDto fromDomain(CloseDate domain) {
		return new CloseDateDto(domain.getTimeCloseDate(), domain.getCloseDateBaseMonth().get().value,
				domain.getCloseDateBaseYear().get().value, domain.getCloseDateRefeDate().get().value);
	}

}
