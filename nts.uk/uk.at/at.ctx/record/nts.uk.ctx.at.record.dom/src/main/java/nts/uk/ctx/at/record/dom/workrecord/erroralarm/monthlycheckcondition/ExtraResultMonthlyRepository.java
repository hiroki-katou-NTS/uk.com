package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;
import java.util.Optional;

public interface ExtraResultMonthlyRepository {
	List<ExtraResultMonthly> getExtraResultMonthlyByListID(List<String> listErrorAlarmCheckID);
	
	Optional<ExtraResultMonthly> getExtraResultMonthlyByID(String errorAlarmCheckID);
	
	void addExtraResultMonthly(ExtraResultMonthly extraResultMonthly);
	
	void updateExtraResultMonthly(ExtraResultMonthly extraResultMonthly);
	
	void deleteExtraResultMonthly(String errorAlarmCheckID);
}

