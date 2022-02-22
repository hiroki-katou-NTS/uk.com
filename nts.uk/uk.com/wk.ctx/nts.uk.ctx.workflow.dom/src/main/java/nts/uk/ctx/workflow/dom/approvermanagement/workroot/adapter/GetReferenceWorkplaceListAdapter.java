package nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * IF_指定職場の指定在籍状態の社員を取得Adapter
 */
public interface GetReferenceWorkplaceListAdapter {

	/**
	 * [1]取得する
	 * 
	 * @param baseDate 年月日
	 * @return List<職場ID>
	 * 
	 */
	List<String> findByBaseDate(GeneralDate baseDate);
}
