package nts.uk.screen.at.app.kmk004.h;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;

/**
 * 
 * @author sonnlb
 *
 */
@Data
public class WkpFlexMonthActCalSetDto extends FlexMonthWorkTimeAggrSetDto {

	/** 職場ID */
	private String workplaceId;

	public WkpFlexMonthActCalSetDto(String workplaceId, String comId, int aggrMethod,
			ShortageFlexSettingDto insufficSet, AggregateTimeSettingDto legalAggrSet,
			FlexTimeHandleDto flexTimeHandle) {
		super(comId, aggrMethod, insufficSet, legalAggrSet, flexTimeHandle);

		this.workplaceId = workplaceId;

	}

	public static WkpFlexMonthActCalSetDto fromDomain(WkpFlexMonthActCalSet domain) {

		return new WkpFlexMonthActCalSetDto(domain.getWorkplaceId(), domain.getComId(), domain.getAggrMethod().value,
				ShortageFlexSettingDto.fromDomain(domain.getInsufficSet()),
				AggregateTimeSettingDto.fromDomain(domain.getLegalAggrSet()),
				FlexTimeHandleDto.fromDomain(domain.getFlexTimeHandle()));

	}

}
