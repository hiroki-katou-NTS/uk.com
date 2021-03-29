package nts.uk.ctx.at.shared.dom.specialholiday.algorithm.service;

import java.util.List;

import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;

/**
 * 特別休暇の経過年数テーブルと付与日数テーブルの整合
 * @author dungbn
 *
 */
public class MatchingElapsedYearsTblWithNumDaysGrantedTblService {

	// 	[1] 付与日数テーブルを整合する
	public static List<GrantDateTbl> consistentGrantDaysTbl(Require require, ElapseYear elapseYear, GrantDateTbl grantDateTbl) {
		
		List<GrantDateTbl> listGrantDateTbl = require.findBySphdCd(elapseYear.getCompanyId(), elapseYear.getSpecialHolidayCode().v());
		listGrantDateTbl.forEach(e -> {
			if (e.getCompanyId().equals(grantDateTbl.getCompanyId()) 
					&& e.getSpecialHolidayCode().v() == grantDateTbl.getSpecialHolidayCode().v()
					&& e.getGrantDateCode().v().equals(grantDateTbl.getGrantDateCode().v())) {
				
				e.setGrantDateName(grantDateTbl.getGrantDateName());
				e.setElapseYear(grantDateTbl.getElapseYear());
				e.setSpecified(grantDateTbl.isSpecified());
				e.setGrantedDays(grantDateTbl.getGrantedDays());
			}
		});
		
		List<GrantDateTbl> afterMatchingList = elapseYear.matchNumberOfGrantsInGrantDaysTable(listGrantDateTbl);
		
		if (grantDateTbl.isSpecified()) {
			
			afterMatchingList.forEach(e -> {
				if (!grantDateTbl.getGrantDateCode().v().equals(e.getGrantDateCode().v())) {
					e.setSpecified(false);
				}
			});
			
			afterMatchingList.stream().
					filter(e -> e.isSpecified() == true)
					.forEach(e -> {
						afterMatchingList.forEach(k -> {
							if (k.getCompanyId().equals(e.getCompanyId()) && k.getSpecialHolidayCode().v() == e.getSpecialHolidayCode().v()) {
								k.setSpecified(false);
							}
						});
					});
		}
		
		return afterMatchingList;
	}
	
	public static interface Require {
		
		// [R-1] 特別休暇付与日数テーブルRepository.取得する(会社ID、特別休暇コード)
		List<GrantDateTbl> findBySphdCd(String companyId, int specialHolidayCode);
	}
}
