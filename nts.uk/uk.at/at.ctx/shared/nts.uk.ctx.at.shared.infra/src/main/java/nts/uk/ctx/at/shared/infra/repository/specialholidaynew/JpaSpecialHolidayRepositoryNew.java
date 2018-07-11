package nts.uk.ctx.at.shared.infra.repository.specialholidaynew;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHolidayRepository;

/**
 * Jpa Special Holiday Repository
 * 
 * @author tanlv
 *
 */
@Stateless
public class JpaSpecialHolidayRepositoryNew extends JpaRepository implements SpecialHolidayRepository {
	private static final String SELECT_BY_CID;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstSpecialHolidayNew e");
		builderString.append(" WHERE e.pk.companyId = :companyId");
		builderString.append(" ORDER BY e.pk.specialHolidayCode ASC");
		SELECT_BY_CID = builderString.toString();
	}
	
	@Override
	public List<SpecialHoliday> findByCompanyId(String companyId) {
//		return this.queryProxy().query(SELECT_BY_CID, KshstSpecialHolidayNew.class).setParameter("companyId", companyId)
//				.getList(c -> toDomainSpecialHoliday(c));
		return null;
	}

	@Override
	public Optional<SpecialHoliday> findByCode(String companyId, int specialHolidayCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkExists(String companyId, int specialHolidayCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(SpecialHoliday specialHoliday) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SpecialHoliday specialHoliday) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyId, int specialHolidayCode) {
		// TODO Auto-generated method stub
		
	}
}
