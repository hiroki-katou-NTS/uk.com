package nts.uk.ctx.at.shared.dom.worktype.worktypedisporder;

/**
 * 
 * @author TanLV
 *
 */
public interface WorkTypeDispOrderRepository {
	
	/**
	 * Add multiple Work Type Disporder.
	 *
	 * @param WorkTypeDispOrder the Work Type Disporder
	 */
	void add(WorkTypeDispOrder workTypeDisporder);
	
	/**
	 * Remove all Work Type Disporder by companyId.
	 *
	 * @param companyId the company id
	 */
	void remove(String companyId);
}
