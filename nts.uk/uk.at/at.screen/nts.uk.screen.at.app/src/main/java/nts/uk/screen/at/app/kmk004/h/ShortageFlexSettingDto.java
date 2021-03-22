package nts.uk.screen.at.app.kmk004.h;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;

/**
 * 
 * @author sonnlb
 *
 *         フレックス不足設定
 */
@AllArgsConstructor
@Data
public class ShortageFlexSettingDto {

	/** 繰越設定 */
	private int carryforwardSet;
	/** 清算期間 */
	private int settlePeriod;
	/** 開始月 */
	private int startMonth;
	/** 期間 */
	private int period;

	public static ShortageFlexSettingDto fromDomain(ShortageFlexSetting domain) {
		return new ShortageFlexSettingDto(domain.getCarryforwardSet().value, domain.getSettlePeriod().value,
				domain.getStartMonth().v(), domain.getPeriod().value);
	}
}
