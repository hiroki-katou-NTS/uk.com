package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMonRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition.KrcmtFixedExtraItemMon;

@Stateless
public class JpaFixedExtraItemMonRepository extends JpaRepository implements FixedExtraItemMonRepository  {

	private static final String SELECT_FROM_FIXED_EXTRA_ITEM = " SELECT c FROM KrcmtFixedExtraItemMon c ";
	
	@Override
	public List<FixedExtraItemMon> getAllFixedExtraItemMon() {
		List<FixedExtraItemMon> data = this.queryProxy().query(SELECT_FROM_FIXED_EXTRA_ITEM,KrcmtFixedExtraItemMon.class)
				.getList(c->c.toDomain());
		return data;
	}

}
