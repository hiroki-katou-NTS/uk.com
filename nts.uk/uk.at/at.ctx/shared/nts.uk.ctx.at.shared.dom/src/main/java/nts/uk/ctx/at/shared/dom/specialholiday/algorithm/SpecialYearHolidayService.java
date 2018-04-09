package nts.uk.ctx.at.shared.dom.specialholiday.algorithm;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.YearServicePer;
import nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.repository.YearServicePerRepository;

/**
 * 個人用勤続年数テーブルを取得する
 * @author tanlv
 *
 */
@Stateless
public class SpecialYearHolidayService {
	@Inject
	public YearServicePerRepository yearServicePerRepository;
	
	public List<YearServicePer> AcquireYearServicePertbl(String companyId) {
		// ドメインモデル「個人用勤続年数テーブル」を取得する
		List<YearServicePer> data = yearServicePerRepository.getAllPer(companyId);
		
		// ドメインモデル「個人用勤続年数テーブル」（List)を返す
		return data != null ? data : Collections.emptyList();
	}
}
