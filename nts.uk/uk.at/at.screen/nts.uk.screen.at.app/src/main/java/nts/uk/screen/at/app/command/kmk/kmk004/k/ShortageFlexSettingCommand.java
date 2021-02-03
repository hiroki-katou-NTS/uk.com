package nts.uk.screen.at.app.command.kmk.kmk004.k;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.CarryforwardSetInShortageFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.SettlePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.SettlePeriodMonths;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.ShortageFlexSetting;
import nts.uk.ctx.at.shared.dom.common.Month;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@NoArgsConstructor
public class ShortageFlexSettingCommand {
	/** 繰越設定 */
	private int carryforwardSet;
	/** 清算期間 */
	private int settlePeriod;
	/** 開始月 */
	private int startMonth;
	/** 期間 */
	private int period;

	public ShortageFlexSetting toDomain() {

		return ShortageFlexSetting.of(
				EnumAdaptor.valueOf(carryforwardSet, CarryforwardSetInShortageFlex.class),
				EnumAdaptor.valueOf(settlePeriod, SettlePeriod.class), 
				new Month(startMonth),
				EnumAdaptor.valueOf(period, SettlePeriodMonths.class));
	}
}
