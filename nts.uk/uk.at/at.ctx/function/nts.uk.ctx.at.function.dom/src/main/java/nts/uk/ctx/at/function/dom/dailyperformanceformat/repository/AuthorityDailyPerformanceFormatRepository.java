package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

public interface AuthorityDailyPerformanceFormatRepository {

	void add(AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat);
	
	void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	void update(AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat);
	
	List<AuthorityDailyPerformanceFormat> getListCode(String companyId);
	
	boolean checkExistCode(String companyId,DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
}
