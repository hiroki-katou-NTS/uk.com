package nts.uk.ctx.at.shared.dom.workrule.workday.export;

import java.util.List;

/**
 * 
 * @author sonnlb
 *
 */
public interface IWorkDayServices {
	/**
	 * [NO.642]指定した会社の雇用毎の締め日を取得する
	 * 
	 * @param 会社ID
	 * @return 雇用毎の締め日リスト
	 */
	List<ClosureDateOfEmployment> getClosureDate(String companyId);
}
