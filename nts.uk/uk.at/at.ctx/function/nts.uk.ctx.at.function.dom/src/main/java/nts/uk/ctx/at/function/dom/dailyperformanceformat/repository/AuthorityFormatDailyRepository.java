package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

public interface AuthorityFormatDailyRepository {

	List<AuthorityFomatDaily> getAuthorityFormatDaily(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);

	List<AuthorityFomatDaily> getAuthorityFormatDailyDetail(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode,
			BigDecimal sheetNo);

	void update(AuthorityFomatDaily authorityFomatDaily);

	void add(List<AuthorityFomatDaily> authorityFomatDailies);
	
	void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	void deleteExistData(String companyId, String dailyPerformanceFormatCode,
			BigDecimal sheetNo,List<Integer> attendanceItemIds);
}
