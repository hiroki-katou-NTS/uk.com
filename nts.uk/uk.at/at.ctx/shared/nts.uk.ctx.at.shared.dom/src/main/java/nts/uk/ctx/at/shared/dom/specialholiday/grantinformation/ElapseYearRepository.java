package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

public interface ElapseYearRepository {

	Optional<ElapseYear> findByCode(CompanyId cid, SpecialHolidayCode code);
}
