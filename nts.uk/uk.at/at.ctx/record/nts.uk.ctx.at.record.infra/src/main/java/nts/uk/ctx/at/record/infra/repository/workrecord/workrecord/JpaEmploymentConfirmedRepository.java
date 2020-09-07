package nts.uk.ctx.at.record.infra.repository.workrecord.workrecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmed;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmedRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.workrecord.KrcdtWorkFixed;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 就業確定Repository	
 * @author chungnt
 *
 */

public class JpaEmploymentConfirmedRepository extends JpaRepository implements EmploymentConfirmedRepository {

	private static final String SELECT_WORK_FIXED = "SELECT r FROM KrcdtWorkFixed r WHERE r.pk.companyId = :companyId"
			+ "and r.pk.workplaceId = :workplaceId" 
			+ "and r.pk.closureId = :closureId" 
			+ "and r.pk.processYM = :processYM";
	
	private static final String SELECT_LIST_WORK_FIXED = "SELECT r FROM KrcdtWorkFixed r WHERE r.pk.companyId = :companyId"
			+ "and r.pk.workplaceId in :workplaceId" 
			+ "and r.pk.closureId = :closureId" 
			+ "and r.pk.processYM = :processYM";
	
	@Override
	public void insert(EmploymentConfirmed domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void delete(EmploymentConfirmed domain) {
		this.commandProxy().remove(toEntity(domain));
	}

	@Override
	public Optional<EmploymentConfirmed> get(String companyId, String workplaceId, ClosureId closureId,
			YearMonth processYM) {
		
		List<KrcdtWorkFixed> list = this.queryProxy().query(SELECT_WORK_FIXED, KrcdtWorkFixed.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("closureId", closureId.value)
				.setParameter("processYM", processYM.v()).getList();
		
		if (list == null) {
			
			return Optional.ofNullable(null);
		}
		
		return Optional.of(toDomain(list.get(0)));
	}

	@Override
	public List<EmploymentConfirmed> get(String companyId, List<String> workplaceId, ClosureId closureId,
			YearMonth processYM) {
		
		List<EmploymentConfirmed> list = this.queryProxy().query(SELECT_LIST_WORK_FIXED, KrcdtWorkFixed.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("closureId", closureId.value)
				.setParameter("processYM", processYM.v()).getList(x ->toDomain(x));
		
		if (list == null) {
			
			return new ArrayList<EmploymentConfirmed>();
		}
		
		return list;
	}
	
	public KrcdtWorkFixed toEntity(EmploymentConfirmed domain) {
		KrcdtWorkFixed entity = new KrcdtWorkFixed();
		
		entity.confirm_date_time = domain.getDate();
		entity.employeeId = domain.getEmployeeId();
		entity.pk.companyId = domain.getCompanyId().v();
		entity.pk.closureId = domain.getClosureId().value;
		entity.pk.processYM = domain.getProcessYM().v();
		entity.pk.workplaceId = domain.getWorkplaceId().v();
		
		return entity;
	}
	
	public EmploymentConfirmed toDomain(KrcdtWorkFixed entity) {
		
		EmploymentConfirmed domain = new EmploymentConfirmed(new CompanyId(entity.pk.companyId),
				new WorkplaceId(entity.pk.workplaceId),
				ClosureId.valueOf(entity.pk.closureId),
				new YearMonth(entity.pk.processYM),
				entity.employeeId,
				entity.confirm_date_time);
		
		return domain;
	}

}
