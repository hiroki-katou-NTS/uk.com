package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearRepository;

public class JpaElapseYearRepository implements ElapseYearRepository{

	@Override
	public Optional<ElapseYear> findByCode(CompanyId cid, SpecialHolidayCode code) {
		// TODO Auto-generated method stub
		return null;
	}

}
