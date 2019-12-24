package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.util.Collection;
import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthoritySFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

public interface AuthorityFormatMonthlySRepository {
	
	List<AuthoritySFomatMonthly> getMonthlyDetail(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	List<AuthoritySFomatMonthly> getListAuthorityFormatDaily(String companyId, List<String> listDailyPerformanceFormatCode);
	
	List<AuthoritySFomatMonthly> getListAuthorityFormatDaily(String companyId, Collection<String> listDailyPerformanceFormatCode);
	
	void update(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode, AuthoritySFomatMonthly authorityFomatMonthly);
	
	void deleteExistData(String companyId, String dailyPerformanceFormatCode,List<Integer> attendanceItemIds);
	
	void add(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode, List<AuthoritySFomatMonthly> authorityFomatMonthlies);
	
	void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	boolean checkExistCode(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);

}
