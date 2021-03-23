package nts.uk.ctx.at.shared.dom.specialholiday.algorithm.service;

import java.util.Collections;
import java.util.List;

import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;

public class MatchingElapsedYearsTblWithNumDaysGrantedTblService {

	// 	[1] 付与日数テーブルを整合する
	public static List<GrantDateTbl> consistentGrantDaysTbl(Require require, ElapseYear elapseYear, GrantDateTbl grantDateTbl) {
		
		List<GrantDateTbl> listGrantDateTbl = require.findBySphdCd(elapseYear.getCompanyId(), elapseYear.getSpecialHolidayCode().v());
		
		return Collections.EMPTY_LIST;
	}
	
	private void ConsistencyGrantDaysByChangingElapsedYears(ElapseYear elapseYear, GrantDateTbl grantDateTbl, List<GrantDateTbl> listGrantDateTbl) {
		
	}
	
	public static interface Require {
		
		// 	[R-1]
		List<GrantDateTbl> findBySphdCd(String companyId, int specialHolidayCode);
	}
}
