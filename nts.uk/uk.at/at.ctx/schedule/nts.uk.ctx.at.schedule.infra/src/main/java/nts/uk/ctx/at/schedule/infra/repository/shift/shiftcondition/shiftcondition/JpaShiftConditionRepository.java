package nts.uk.ctx.at.schedule.infra.repository.shift.shiftcondition.shiftcondition;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftCondition;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.shiftcondition.shiftcondition.KscmtShiftCondition;

@Stateless
public class JpaShiftConditionRepository extends JpaRepository implements ShiftConditionRepository {
	private final String SELECT_SHIFT_CONDITION_NO_WHERE = "SELECT c FROM KscmtShiftCondition c ";

	private final String SELECT_SHIFT_CON_BY_COMPANY = SELECT_SHIFT_CONDITION_NO_WHERE
			+ "WHERE c.kscmntShiftConditionPk.companyId =:companyId ORDER  BY c.kscmntShiftConditionPk.conditionNo";

	@Override
	public List<ShiftCondition> getListShiftCondition(String companyId) {
		return this.queryProxy().query(SELECT_SHIFT_CON_BY_COMPANY, KscmtShiftCondition.class)
				.setParameter("companyId", companyId).getList(entity -> toDomain(entity));
	}

	private ShiftCondition toDomain(KscmtShiftCondition entity) {
		return ShiftCondition.createFromJavaType(entity.kscmntShiftConditionPk.companyId, entity.categoryNo,
				entity.kscmntShiftConditionPk.conditionNo, entity.conditionErrorMessage, entity.conditionName,
				entity.conditionDetailNo);
	}

}
