package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;

public interface BusinessTypeFormatMonthlyRepository {
	
	List<BusinessTypeFormatMonthly> getMonthlyDetail(String companyId, String workTypeCode);

}
