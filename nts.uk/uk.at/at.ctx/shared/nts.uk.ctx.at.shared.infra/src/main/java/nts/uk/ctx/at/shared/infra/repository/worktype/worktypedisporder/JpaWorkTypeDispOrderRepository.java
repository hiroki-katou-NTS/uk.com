package nts.uk.ctx.at.shared.infra.repository.worktype.worktypedisporder;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktype.worktypedisporder.WorkTypeDispOrder;
import nts.uk.ctx.at.shared.dom.worktype.worktypedisporder.WorkTypeDispOrderRepository;
import nts.uk.ctx.at.shared.infra.entity.worktype.worktypedisporder.KshmtWorkTypeOrder;
import nts.uk.ctx.at.shared.infra.entity.worktype.worktypedisporder.KshmtWorkTypeOrderPk;

/**
 * 
 * @author TanLV
 *
 */
@Stateless
public class JpaWorkTypeDispOrderRepository extends JpaRepository implements WorkTypeDispOrderRepository  {
	private static final String DELETE_WORK_TYPE_DISPORDER = "DELETE FROM KshmtWorkTypeOrder w "
			+ "WHERE w.kshmtWorkTypeDispOrderPk.companyId =:companyId ";
	
	@Override
	public void add(WorkTypeDispOrder workTypeDisporder) {
		this.commandProxy().insert(toEntity(workTypeDisporder));
	}

	@Override
	public void remove(String companyId) {
		this.getEntityManager().createQuery(DELETE_WORK_TYPE_DISPORDER)
		.setParameter("companyId", companyId)
		.executeUpdate();
	}

	/**
	 * Convert to entity.
	 *
	 * @param WorkTypeDispOrder the Work Type Disporder
	 */
	private KshmtWorkTypeOrder toEntity(WorkTypeDispOrder workTypeDisporder) {
		return new KshmtWorkTypeOrder(
				new KshmtWorkTypeOrderPk(workTypeDisporder.getCompanyId(), workTypeDisporder.getWorkTypeCode()),
				workTypeDisporder.getDisporder());
	}
}
