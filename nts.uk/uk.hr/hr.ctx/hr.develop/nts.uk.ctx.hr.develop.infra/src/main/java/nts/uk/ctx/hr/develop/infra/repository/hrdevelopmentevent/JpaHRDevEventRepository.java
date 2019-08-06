package nts.uk.ctx.hr.develop.infra.repository.hrdevelopmentevent;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.hrdevelopmentevent.HRDevEvent;
import nts.uk.ctx.hr.develop.dom.hrdevelopmentevent.HRDevEventRepository;
import nts.uk.ctx.hr.develop.infra.entity.hrdevelopmentevent.JcmmtHRDevEvent;

@Stateless
public class JpaHRDevEventRepository extends JpaRepository implements HRDevEventRepository{
	
	private static final String FIND_BY_AVAILABLE = "SELECT a FROM JcmmtHRDevEvent a "
											+ "WHERE a.availableEvent =: 1 "
											+ "ORDER BY a.eventId ASC ";
	/**
	 * find item by id
	 * @author yennth
	 */
	@Override
	public List<HRDevEvent> findByAvailable() {
		List<HRDevEvent> result = this.queryProxy().query(FIND_BY_AVAILABLE, JcmmtHRDevEvent.class)
										.getList(x -> convertToDomain(x));
		return result;
	}
	/**
	 * convert from entity to domain
	 * @param x
	 * @return
	 * @author yennth
	 */
	private HRDevEvent convertToDomain(JcmmtHRDevEvent x) {
		HRDevEvent domain = HRDevEvent.createFromJavaType(x.eventId, x.memo, x.availableEvent);
		return domain;
	}

}
