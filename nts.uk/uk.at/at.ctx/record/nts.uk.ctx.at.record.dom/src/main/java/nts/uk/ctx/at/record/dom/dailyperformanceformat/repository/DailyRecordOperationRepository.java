package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.DailyRecordOperation;

public interface DailyRecordOperationRepository {

	Optional<DailyRecordOperation> getSettingUnit(String companyId);
}
