package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatInitialDisplay;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.enums.PCSmartPhoneAtt;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

public interface AuthorityFormatInitialDisplayRepository {
	
	void add(AuthorityFormatInitialDisplay authorityFormatInitialDisplay);
	
	void update(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode, PCSmartPhoneAtt att);

	void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode, PCSmartPhoneAtt att);
	
	boolean checkExistData(String companyId,DailyPerformanceFormatCode dailyPerformanceFormatCode, PCSmartPhoneAtt att);
	
	boolean checkExistDataByCompanyId(String companyId, PCSmartPhoneAtt att);
	
	void removeByCid(String companyId, PCSmartPhoneAtt att);
}
