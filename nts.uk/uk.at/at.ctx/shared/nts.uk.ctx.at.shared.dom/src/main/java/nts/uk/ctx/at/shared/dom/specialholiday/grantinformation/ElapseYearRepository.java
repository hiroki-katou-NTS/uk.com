package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

/**
 * 特別休暇付与経過年数テーブル
 * @author masaaki_jinno
 *
 */
public interface ElapseYearRepository {
	
	Optional<ElapseYear> findByCode(CompanyId cid, SpecialHolidayCode code);
	void delete(String companyId, int specialHolidayCode);
}



