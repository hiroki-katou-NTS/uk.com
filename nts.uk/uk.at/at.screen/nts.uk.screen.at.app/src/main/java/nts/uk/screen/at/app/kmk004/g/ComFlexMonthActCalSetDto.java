package nts.uk.screen.at.app.kmk004.g;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.screen.at.app.kmk004.h.AggregateTimeSettingDto;
import nts.uk.screen.at.app.kmk004.h.FlexMonthWorkTimeAggrSetDto;
import nts.uk.screen.at.app.kmk004.h.FlexTimeHandleDto;
import nts.uk.screen.at.app.kmk004.h.ShortageFlexSettingDto;

/**
 * 
 * @author sonnlb
 *
 *         会社別フレックス勤務集計方法
 */
@Data
public class ComFlexMonthActCalSetDto extends FlexMonthWorkTimeAggrSetDto {

	/** 所定労動時間使用区分 */
	private boolean withinTimeUsageAttr;

	public ComFlexMonthActCalSetDto(boolean withinTimeUsageAttr, String comId, int aggrMethod,
			ShortageFlexSettingDto insufficSet, AggregateTimeSettingDto legalAggrSet,
			FlexTimeHandleDto flexTimeHandle) {
		super(comId, aggrMethod, insufficSet, legalAggrSet, flexTimeHandle);
		this.withinTimeUsageAttr = withinTimeUsageAttr;
	}

	public static ComFlexMonthActCalSetDto fromDomain(ComFlexMonthActCalSet domain) {

		return new ComFlexMonthActCalSetDto(domain.isWithinTimeUsageAttr(), domain.getComId(),
				domain.getAggrMethod().value, ShortageFlexSettingDto.fromDomain(domain.getInsufficSet()),
				AggregateTimeSettingDto.fromDomain(domain.getLegalAggrSet()),
				FlexTimeHandleDto.fromDomain(domain.getFlexTimeHandle()));

	}

}
