package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.util.List;
import java.util.Map;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

public interface AuthorityFormatMonthlyRepository {
	
	List<AuthorityFomatMonthly> getMonthlyDetail(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	List<AuthorityFomatMonthly> getListAuthorityFormatDaily(String companyId, List<String> listDailyPerformanceFormatCode);
	
	void update(AuthorityFomatMonthly authorityFomatMonthly);
	
	void updateColumnsWidth(String companyId, Map<Integer, Integer> lstHeaderMIGrid, List<String> formatCodes);

	void deleteExistData(String companyId, String dailyPerformanceFormatCode,List<Integer> attendanceItemIds);
	
	void add(List<AuthorityFomatMonthly> authorityFomatMonthlies);
	
	void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	boolean checkExistCode(String companyId,DailyPerformanceFormatCode dailyPerformanceFormatCode);

}
