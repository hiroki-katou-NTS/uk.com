package nts.uk.screen.at.app.kmk004.i;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.screen.at.app.kmk004.h.AggregateTimeSettingDto;
import nts.uk.screen.at.app.kmk004.h.FlexMonthWorkTimeAggrSetDto;
import nts.uk.screen.at.app.kmk004.h.FlexTimeHandleDto;
import nts.uk.screen.at.app.kmk004.h.ShortageFlexSettingDto;

/**
 * 
 * @author sonnlb
 *
 *         雇用別フレックス勤務集計方法
 */
@Data
public class EmpFlexMonthActCalSetDto extends FlexMonthWorkTimeAggrSetDto {

	/** 雇用コード */
	private String employmentCode;

	public EmpFlexMonthActCalSetDto(String employmentCode, String comId, int aggrMethod,
			ShortageFlexSettingDto insufficSet, AggregateTimeSettingDto legalAggrSet,
			FlexTimeHandleDto flexTimeHandle) {
		super(comId, aggrMethod, insufficSet, legalAggrSet, flexTimeHandle);

		this.employmentCode = employmentCode;
	}

	public static EmpFlexMonthActCalSetDto fromDomain(EmpFlexMonthActCalSet domain) {

		return new EmpFlexMonthActCalSetDto(domain.getEmploymentCode().v(), domain.getComId(),
				domain.getAggrMethod().value, ShortageFlexSettingDto.fromDomain(domain.getInsufficSet()),
				AggregateTimeSettingDto.fromDomain(domain.getLegalAggrSet()),
				FlexTimeHandleDto.fromDomain(domain.getFlexTimeHandle()));

	}

}
