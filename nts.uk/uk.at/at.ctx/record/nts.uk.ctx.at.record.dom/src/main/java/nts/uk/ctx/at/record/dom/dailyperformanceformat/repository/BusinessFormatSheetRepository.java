package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessFormatSheet;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;

public interface BusinessFormatSheetRepository {
	
	Optional<BusinessFormatSheet> getSheetInformation(String companyId, BusinessTypeCode businessTypeCode, BigDecimal sheetNo);

	List<BusinessFormatSheet> getSheetInformationByCode(String companyId, String businessTypeCode);
	
	boolean checkExistData(String companyId, BusinessTypeCode businessTypeCode, BigDecimal sheetNo);
	
	void update(BusinessFormatSheet businessFormatSheet);
	
	void add(BusinessFormatSheet businessFormatSheet);
	
	void deleteBusBySheetNo(String companyId, String businessTypeCode, BigDecimal sheetNo);

}
