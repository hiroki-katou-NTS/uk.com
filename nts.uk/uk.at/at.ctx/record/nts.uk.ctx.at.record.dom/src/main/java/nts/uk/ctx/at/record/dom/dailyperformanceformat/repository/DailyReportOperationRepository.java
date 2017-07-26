package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.DailyReportOperation;

public interface DailyReportOperationRepository {

	Optional<DailyReportOperation> getSettingUnit(String companyId);
}
