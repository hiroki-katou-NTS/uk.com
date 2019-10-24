package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.util.Collection;
import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatMonthly;

public interface BusinessTypeSFormatMonthlyRepository {

	List<BusinessTypeSFormatMonthly> getMonthlyDetail(String companyId, String workTypeCode);

	List<BusinessTypeSFormatMonthly> getListBusinessTypeFormat(String companyId, Collection<String> listBusinessTypeCode);

	void update(BusinessTypeSFormatMonthly businessTypeFormatMonthly);

	void deleteExistData(String companyId, String businessTypeCode, List<Integer> attendanceItemIds);

	void add(List<BusinessTypeSFormatMonthly> businessTypeFormatMonthlyAdds);

	boolean checkExistData(String companyId, String businessTypeCode);

	List<BusinessTypeSFormatMonthly> getMonthlyDetailByCompanyId(String companyId);

}
