package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionDataRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KrcmtFixedConditionData;
@Stateless
public class JpaFixedConditionDataRepository extends JpaRepository implements FixedConditionDataRepository  {

	private final String SELECT_FROM_FIXED_CON_DATA = " SELECT c FROM KrcmtFixedConditionData c ";
	
	@Override
	public List<FixedConditionData> getAllFixedConditionData() {
		List<FixedConditionData> data = this.queryProxy().query(SELECT_FROM_FIXED_CON_DATA,KrcmtFixedConditionData.class)
				.getList(c->c.toDomain());
		return data;
	}

}
