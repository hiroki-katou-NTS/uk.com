package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceSFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

public interface AuthorityDailyPerformanceSFormatRepository {

	void add(AuthorityDailyPerformanceSFormat authorityDailyPerformanceFormat);
	
	void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	void update(AuthorityDailyPerformanceSFormat authorityDailyPerformanceFormat);
	
	boolean checkExistCode(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);

	AuthorityDailyPerformanceSFormat getAllByCompanyIdAndCode(String companyId, String formatCode);

	List<AuthorityDailyPerformanceSFormat> getAllByCompanyId(String companyId);
	
}
