package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;

public interface AuthorityFormatDailyRepository {

	List<AuthorityFomatDaily> getAuthorityFormatDaily(String companyId, String dailyPerformanceFormatCode);

	List<AuthorityFomatDaily> getAuthorityFormatDailyDetail(String companyId, String dailyPerformanceFormatCode,
			BigDecimal sheetNo);

	void update(AuthorityFomatDaily authorityFomatDaily);

	void add(List<AuthorityFomatDaily> authorityFomatDailies);
	
	boolean checkExistData(String dailyPerformanceFormatCode);
	
//	void remove();
}
