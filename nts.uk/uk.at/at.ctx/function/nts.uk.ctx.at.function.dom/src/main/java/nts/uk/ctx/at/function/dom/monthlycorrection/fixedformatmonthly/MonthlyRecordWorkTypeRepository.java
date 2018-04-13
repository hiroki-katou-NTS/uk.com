package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

public interface MonthlyRecordWorkTypeRepository {
	Optional<MonthlyRecordWorkType> getMonthlyRecordWorkTypeByCode(String companyID,String businessTypeCode);
	
	void addMonthlyRecordWorkType(MonthlyRecordWorkType monthlyRecordWorkType);
	
	void updateMonthlyRecordWorkType(MonthlyRecordWorkType monthlyRecordWorkType);
}
