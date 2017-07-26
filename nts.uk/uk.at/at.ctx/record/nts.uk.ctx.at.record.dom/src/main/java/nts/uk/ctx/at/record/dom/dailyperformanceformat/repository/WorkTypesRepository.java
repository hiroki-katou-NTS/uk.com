package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.WorkType;

public interface WorkTypesRepository {
	
	List<WorkType> findAll(String companyId);

}
