package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import java.util.List;
import java.util.Optional;

public interface ScheduleDailyTableRepository {
	
	/**
	 * get
	 * @param companyId 会社ID
	 * @param code 勤務計画実施表のコード
	 * @return
	 */
	public Optional<ScheduleDailyTablePrintSetting> get(String companyId, ScheduleDailyTableCode code);
	
	/**
	 * get*
	 * @param companyId 会社ID
	 * @return
	 */
	public List<ScheduleDailyTablePrintSetting> getList(String companyId);
	
	/**
	 * @param domain 勤務計画実施表の出力設定
	 */
	public void insert(ScheduleDailyTablePrintSetting domain);
	
	/**
	 * @param domain 勤務計画実施表の出力設定
	 */
	public void update(ScheduleDailyTablePrintSetting domain);
	
	/**
	 * @param companyId 会社ID
	 * @param code 勤務計画実施表のコード
	 */
	public void delete(String companyId, ScheduleDailyTableCode code);

}
