package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatInitialDisplay;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

public interface AuthorityFormatInitialDisplayRepository {
	
	void add(AuthorityFormatInitialDisplay authorityFormatInitialDisplay);
	
	void update(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);

	void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	boolean checkExistData(String companyId,DailyPerformanceFormatCode dailyPerformanceFormatCode);
	
	boolean checkExistDataByCompanyId(String companyId);
	
	void removeByCid(String companyId);
}
