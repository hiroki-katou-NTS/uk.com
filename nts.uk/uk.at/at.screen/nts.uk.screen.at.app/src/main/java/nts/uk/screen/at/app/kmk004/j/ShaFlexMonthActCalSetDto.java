package nts.uk.screen.at.app.kmk004.j;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.screen.at.app.kmk004.h.AggregateTimeSettingDto;
import nts.uk.screen.at.app.kmk004.h.FlexMonthWorkTimeAggrSetDto;
import nts.uk.screen.at.app.kmk004.h.FlexTimeHandleDto;
import nts.uk.screen.at.app.kmk004.h.ShortageFlexSettingDto;

/**
 * 
 * @author sonnlb
 *
 *         社員別フレックス勤務集計方法
 */
@Data
public class ShaFlexMonthActCalSetDto extends FlexMonthWorkTimeAggrSetDto {

	/** 社員ID */
	private String empId;

	public ShaFlexMonthActCalSetDto(String empId, String comId, int aggrMethod, ShortageFlexSettingDto insufficSet,
			AggregateTimeSettingDto legalAggrSet, FlexTimeHandleDto flexTimeHandle) {
		super(comId, aggrMethod, insufficSet, legalAggrSet, flexTimeHandle);
		this.empId = empId;
	}
	
	public static ShaFlexMonthActCalSetDto fromDomain(ShaFlexMonthActCalSet domain) {

		return new ShaFlexMonthActCalSetDto(domain.getEmpId(), domain.getComId(),
				domain.getAggrMethod().value, ShortageFlexSettingDto.fromDomain(domain.getInsufficSet()),
				AggregateTimeSettingDto.fromDomain(domain.getLegalAggrSet()),
				FlexTimeHandleDto.fromDomain(domain.getFlexTimeHandle()));

	}

}
