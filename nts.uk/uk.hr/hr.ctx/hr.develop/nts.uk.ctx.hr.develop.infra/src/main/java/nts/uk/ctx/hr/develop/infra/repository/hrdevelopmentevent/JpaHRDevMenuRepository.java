package nts.uk.ctx.hr.develop.infra.repository.hrdevelopmentevent;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevMenu;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.algorithm.HRDevMenuRepository;
import nts.uk.ctx.hr.develop.infra.entity.humanresourcedevevent.JcmmtHRDevMenu;
@Stateless
public class JpaHRDevMenuRepository extends JpaRepository implements HRDevMenuRepository{
	
	private static final String FIND_BY_AVAILABLE = "SELECT a FROM JcmmtHRDevMenu a "
												+ "WHERE a.availableMenu = :availableMenu "
												+ "ORDER BY a.dispOrder ASC ";
	
	/**
	 * find item by id
	 * @author yennth
	 */
	@Override
	public List<HRDevMenu> findByAvailable() {
		List<HRDevMenu> result = this.queryProxy().query(FIND_BY_AVAILABLE, JcmmtHRDevMenu.class)
										.setParameter("availableMenu", 1)
										.getList(x -> convertToDomain(x));
		return result;
	}

	/**
	 * convert from entity to domain
	 * @param x
	 * @return
	 * @author yennth
	 */
	private HRDevMenu convertToDomain(JcmmtHRDevMenu x) {
		HRDevMenu domain = HRDevMenu.createFromJavaType(x.eventId, 
														x.programId, 
														x.programName, 
														x.availableMenu, 
														x.availableApproval, 
														x.dispOrder, 
														x.availableNotice);
		return domain;
	}

	private static final String FIND_PROGRAM_ID = "SELECT a FROM JcmmtHRDevMenu a "
			+ "WHERE a.eventId in :eventId "
			+ "AND a.availableMenu = 1 "
			+ "AND a.availableApproval = 1 ";
	@Override
	public List<String> findProgramId(List<Integer> eventIds) {
		List<String> result = new ArrayList<>();
		CollectionUtil.split(eventIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			result.addAll(this.queryProxy().query(FIND_PROGRAM_ID, JcmmtHRDevMenu.class)
					.setParameter("eventId", eventIds)
					.getList(c -> c.programId));
		});
		return result;
	}
}
