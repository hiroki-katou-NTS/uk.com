package nts.uk.ctx.at.shared.app.command.outsideot.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.PremiumExtra60HRateDto;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.SuperHD60HConMedDto;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHDOccUnit;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PremiumExtra60HComand {


    /** The rounding time. */
    private Integer roundingTime;

    /** The rounding. */
    private Integer rounding;

    /** The super holiday occurrence unit. */
    private Integer superHolidayOccurrenceUnit;

    /** The premium extra 60 H rates. */
    private List<PremiumExtra60HRateDto> premiumExtra60HRates;

    public static SuperHD60HConMedDto of (SuperHD60HConMed domain) {

        return new SuperHD60HConMedDto(true, domain.getTimeRoundingSetting().getRoundingTime().value,
                domain.getTimeRoundingSetting().getRounding().value,
                domain.getSuperHolidayOccurrenceUnit().v(), new ArrayList<>());
    }
    public SuperHD60HConMed toDomainSuper() {

        return new SuperHD60HConMed(AppContexts.user().companyId(),
                new TimeRoundingSetting(roundingTime, rounding),
                new SuperHDOccUnit(superHolidayOccurrenceUnit != null ? superHolidayOccurrenceUnit : 0));
    }
}
