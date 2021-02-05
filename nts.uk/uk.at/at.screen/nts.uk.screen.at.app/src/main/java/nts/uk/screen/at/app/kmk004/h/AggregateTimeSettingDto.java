package nts.uk.screen.at.app.kmk004.h;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.AggregateTimeSetting;

/**
 * 
 * @author sonnlb
 *
 *         集計時間設定
 */
@AllArgsConstructor
@Data
public class AggregateTimeSettingDto {

	/** 集計設定 */
	private int aggregateSet;

	public static AggregateTimeSettingDto fromDomain(AggregateTimeSetting domain) {
		return new AggregateTimeSettingDto(domain.getAggregateSet().value);
	}
}
