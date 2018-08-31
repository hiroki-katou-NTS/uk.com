package nts.uk.ctx.at.schedule.infra.repository.shift.shiftcondition.shiftcondition;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionCategory;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionCategoryRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.shiftcondition.shiftcondition.KscmtShiftConditionCategory;

@Stateless
public class JpaShiftConditionCategoryRepository extends JpaRepository implements ShiftConditionCategoryRepository {
	private static final String SELECT_SHIFT_CONDITION_CATEGORY_NO_WHERE = "SELECT c FROM KscmtShiftConditionCategory c ";
	private static final String SELECT_SHIFT_CON_CATE_BY_COMPANY = SELECT_SHIFT_CONDITION_CATEGORY_NO_WHERE
			+ "WHERE c.pk.companyId =:companyId ORDER  BY c.pk.categoryNo ";

	@Override
	public List<ShiftConditionCategory> getListShifConditionCategory(String companyId) {
		return this.queryProxy().query(SELECT_SHIFT_CON_CATE_BY_COMPANY, KscmtShiftConditionCategory.class)
				.setParameter("companyId", companyId).getList(entity -> toDomain(entity));
	}

	private ShiftConditionCategory toDomain(KscmtShiftConditionCategory entity) {
		return ShiftConditionCategory.createFromJavaType(entity.pk.companyId, entity.pk.categoryNo,
				entity.categoryName);
	}

}
