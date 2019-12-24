package nts.uk.ctx.pr.transfer.dom.adapter.employment;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * アダプタ：所属雇用履歴
 * @author HungTT
 */
public interface TransEmploymentHistAdapter {

	public List<EmploymentHistImport> findByCidAndDate(String companyId, GeneralDate baseDate);
	
}
