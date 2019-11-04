package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatDaily;

public interface BusinessTypeSFormatDailyRepository {

	List<BusinessTypeSFormatDaily> getBusinessTypeFormat(String companyId, String businessTypeCode);

	List<BusinessTypeSFormatDaily> getBusinessTypeFormatDailyDetail(String companyId, String businessTypeCode);

	void deleteExistData(List<Integer> attendanceItemIds);

	void deleteExistDataByCode(String businesstypeCode, String companyId, List<Integer> attendanceItemIds);

	void update(BusinessTypeSFormatDaily businessTypeFormatDaily);

	void add(List<BusinessTypeSFormatDaily> businessTypeFormatDailies);

	boolean checkExistData(String companyId, String businessTypeCode);

	List<BusinessTypeSFormatDaily> getBusinessTypeFormatByCompanyId(String companyId);
}
