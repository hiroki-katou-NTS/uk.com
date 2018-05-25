package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeFormatMonthly;

public interface BusinessTypeFormatMonthlyRepository {
	
	List<BusinessTypeFormatMonthly> getMonthlyDetail(String companyId, String workTypeCode);
	
	void update(BusinessTypeFormatMonthly businessTypeFormatMonthly);
	
	void deleteExistData(List<Integer> attendanceItemIds);
	
	void add(List<BusinessTypeFormatMonthly> businessTypeFormatMonthlyAdds);
	
	boolean checkExistData(String companyId, String businessTypeCode);
	
	List<BusinessTypeFormatMonthly> getMonthlyDetailByCompanyId(String companyId);

}
