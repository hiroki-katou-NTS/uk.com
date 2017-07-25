package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.WorkTypeFormatDaily;

public interface WorkTypeFormatDailyRepository {
	
	List<WorkTypeFormatDaily> getDailyDetail(String companyId, String workTypeCode);
}
