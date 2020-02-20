package nts.uk.ctx.workflow.dom.hrapproverstatemana;

import java.util.Optional;

public interface ApprovalRootStateHrRepository {
	/**
	 * get Root by Id
	 * @param rootStateId
	 * @return
	 */
	public Optional<ApprovalRootStateHr> getById(String rootStateId);
	/**
	 * insert domain Root
	 * @param root
	 */
	public void insert(ApprovalRootStateHr root);
	/**
	 * update domain Root
	 * @param root
	 */
	public void update(ApprovalRootStateHr root);
}
