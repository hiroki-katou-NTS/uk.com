package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.WorkTypeFormatMonthly;

public interface WorkTypeFormatMonthlyRepository {
	
	List<WorkTypeFormatMonthly> getMonthlyDetail(String companyId, String workTypeCode);

}
