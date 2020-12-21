package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;
import java.util.Optional;

public interface ExtraResultMonthlyRepository {
	List<ExtraResultMonthly> getExtraResultMonthlyByListID(List<String> listErrorAlarmCheckID);
	
	Optional<ExtraResultMonthly> getExtraResultMonthlyByID(String errorAlarmCheckID);
	
	void addExtraResultMonthly(ExtraResultMonthly extraResultMonthly);
	
	void updateExtraResultMonthly(ExtraResultMonthly extraResultMonthly);
	
	void deleteExtraResultMonthly(String errorAlarmCheckID);
	/**
	 * 抽出条件Idと使用区分から「月別実績の抽出条件」を取得
	 * @param lstAnyId
	 * @param useAtr
	 * @return
	 */
	List<ExtraResultMonthly> getAnyItemBySidAndUseAtr(List<String> lstAnyId, boolean useAtr);
}

