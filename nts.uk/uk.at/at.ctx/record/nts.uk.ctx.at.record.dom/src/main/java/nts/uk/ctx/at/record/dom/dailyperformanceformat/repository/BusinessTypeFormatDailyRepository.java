package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.math.BigDecimal;
import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatDaily;

public interface BusinessTypeFormatDailyRepository {
	
	List<BusinessTypeFormatDaily> getBusinessTypeFormat(String companyId, String businessTypeCode);
	
	List<BusinessTypeFormatDaily> getBusinessTypeFormatDailyDetail(String companyId, String businessTypeCode, BigDecimal sheetNo);
}
