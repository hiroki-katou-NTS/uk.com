package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe;

import java.util.List;


public interface DivergenceTimeRootRepository {
	/**
	 * Refactor5
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).日の勤怠計算.乖離時間.乖離時間枠.アルゴリズム.Query.<<Public>>乖離時間Listを取得する
	 * @param frames  List<乖離時間NO>
	 * @return 乖離時間
	 */
	public List<DivergenceTimeRoot> getList(List<Integer> frames);
	
	
	public List<DivergenceTimeRoot> getAllDivTime(String companyId);
}
