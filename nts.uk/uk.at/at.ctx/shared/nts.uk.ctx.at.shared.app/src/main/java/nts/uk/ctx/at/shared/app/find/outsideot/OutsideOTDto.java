package nts.uk.ctx.at.shared.app.find.outsideot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTBRDItemDto;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OvertimeDto;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OutsideOTDto {
    private List<OutsideOTBRDItemDto> breakdownItems;
    /**
     * The overtimes.
     */
    private List<OvertimeDto> overtimes;

    /** The rounding time. */
    private Integer roundingTime;

    /** The rounding. */
    private Integer rounding;

    /** The super holiday occurrence unit. */
    private Integer superHolidayOccurrenceUnit;
}
