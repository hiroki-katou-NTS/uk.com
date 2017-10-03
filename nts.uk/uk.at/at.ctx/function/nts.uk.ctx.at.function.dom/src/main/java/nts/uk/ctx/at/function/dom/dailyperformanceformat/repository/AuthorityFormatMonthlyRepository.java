package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

public interface AuthorityFormatMonthlyRepository {
	
	List<AuthorityFomatMonthly> getMonthlyDetail(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	void update(AuthorityFomatMonthly authorityFomatMonthly);

	void deleteExistData(List<Integer> attendanceItemIds);
	
	void add(List<AuthorityFomatMonthly> authorityFomatMonthlies);
	
	void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	boolean checkExistCode(DailyPerformanceFormatCode dailyPerformanceFormatCode);

}
