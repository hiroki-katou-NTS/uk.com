package nts.uk.ctx.at.function.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthoritySFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

public interface AuthorityDailySItemRepository {

	List<AuthoritySFomatDaily> getAuthorityFormatDailyDetail(String companyId,
			DailyPerformanceFormatCode dailyPerformanceFormatCode);

	void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode);

	void deleteExistData(String companyId, String dailyPerformanceFormatCode, List<Integer> attendanceItemIds);

	void add(String companyId, String dailyPerformanceFormatCode, List<AuthoritySFomatDaily> authorityFormatDailyAdds);

	void update(String companyId, String dailyPCode, AuthoritySFomatDaily item);

}
