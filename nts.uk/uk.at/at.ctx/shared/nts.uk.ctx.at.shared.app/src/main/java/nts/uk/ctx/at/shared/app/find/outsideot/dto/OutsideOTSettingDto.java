/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingProcessOfExcessOutsideTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.TimeRoundingOfExcessOutsideTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNote;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutsideOTSettingDto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutsideOTSettingDto {

    /**
     * The note.
     */
    private String note;

    /**
     * The calculation method.
     */
    private Integer calculationMethod;

    /**
     * The breakdown items.
     */
    private List<OutsideOTBRDItemDto> breakdownItems;

    /**
     * The overtimes.
     */
    private List<OvertimeDto> overtimes;

    //TODO QA 39234
    private Integer roundingUnit;

    private Integer roundingProcess;

    public static OutsideOTSettingDto of(OutsideOTSetting domain) {

        return new OutsideOTSettingDto(domain.getNote().v(),
            domain.getCalculationMethod().value,
            domain.getBreakdownItems().stream().map(c -> OutsideOTBRDItemDto.of(c)).collect(Collectors.toList()),
            domain.getOvertimes().stream().map(c -> OvertimeDto.of(c)).collect(Collectors.toList()),
            domain.getTimeRoundingOfExcessOutsideTime().isPresent() ? domain.getTimeRoundingOfExcessOutsideTime().get().getRoundingUnit().value : null,
            domain.getTimeRoundingOfExcessOutsideTime().isPresent() ? domain.getTimeRoundingOfExcessOutsideTime().get().getRoundingProcess().value : null
        );
    }

    public OutsideOTSetting domain() {

        return new OutsideOTSetting(AppContexts.user().companyId(), new OvertimeNote(note),
            breakdownItems.stream().map(c -> c.domain()).collect(Collectors.toList()),
            EnumAdaptor.valueOf(calculationMethod, OutsideOTCalMed.class),
            overtimes.stream().map(c -> c.domain()).collect(Collectors.toList()),
            Optional.of(TimeRoundingOfExcessOutsideTime.of(
                calculationMethod == OutsideOTCalMed.DECISION_AFTER.value ? Unit.ROUNDING_TIME_1MIN.value : roundingUnit,
                calculationMethod == OutsideOTCalMed.DECISION_AFTER.value ? RoundingProcessOfExcessOutsideTime.FOLLOW_ELEMENTS.value : roundingProcess
            ))
        );
    }
}