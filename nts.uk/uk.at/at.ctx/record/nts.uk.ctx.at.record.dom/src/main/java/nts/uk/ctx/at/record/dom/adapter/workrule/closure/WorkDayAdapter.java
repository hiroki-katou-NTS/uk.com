package nts.uk.ctx.at.record.dom.adapter.workrule.closure;

import java.util.List;

public interface WorkDayAdapter {
	/**
	 * Adapter: [NO.642]指定した会社の雇用毎の締め日を取得する
	 * 
	 * @param 会社ID
	 * @return 雇用毎の締め日リスト
	 */
	List<ClosureDateOfEmploymentImport> getClosureDate(String companyId);
}
