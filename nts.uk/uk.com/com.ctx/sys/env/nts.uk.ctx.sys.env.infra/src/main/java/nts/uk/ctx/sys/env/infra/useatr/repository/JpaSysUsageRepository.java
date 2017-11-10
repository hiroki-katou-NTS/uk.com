package nts.uk.ctx.sys.env.infra.useatr.repository;

import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.env.dom.useatr.SysUsageRepository;
import nts.uk.ctx.sys.env.dom.useatr.SysUsageSet;
import nts.uk.ctx.sys.env.infra.useatr.entity.SacmtSysUsageSet;
import nts.uk.ctx.sys.env.infra.useatr.entity.SacmtSysUsageSetPK;

public class JpaSysUsageRepository extends JpaRepository implements SysUsageRepository{
	// system usage setting
	private final String SELECT_NO_WHERE = "SELECT c FROM SacmtSysUsageSet c ";
	private final String SELECT_ITEM = SELECT_NO_WHERE + "WHERE c.sacmtSysUsageSetPK.companyId = :companyId";
	
	/**
	 * convert from SacmtSysUsageSet entity to SysUsageSet domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static SysUsageSet toDomainSys(SacmtSysUsageSet entity){
		SysUsageSet domain = SysUsageSet.createFromJavaType(entity.sacmtSysUsageSetPK.companyId, entity.personnelSystem,
															entity.employmentSys, entity.payrollSys);
		return domain;
	}
	
	/**
	 * convert from SysUsageSet domain to SacmtSysUsageSet entity 
	 * @param domain
	 * @return
	 * author: Hoang Yen
	 */
	private static SacmtSysUsageSet toEntitySys(SysUsageSet domain){
		val entity = new SacmtSysUsageSet();
		entity.sacmtSysUsageSetPK = new SacmtSysUsageSetPK(domain.getCompanyId());
		entity.personnelSystem = domain.getPersonnelSystem().value;
		entity.employmentSys = domain.getEmploymentSys().value;
		entity.payrollSys = domain.getPayrollSys().value;
		return entity;
	}

	/**
	 * find SysUsageSet
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	@Override
	public Optional<SysUsageSet> findUsageSet(String companyId) {
		SacmtSysUsageSet entity = this.queryProxy().query(SELECT_ITEM, SacmtSysUsageSet.class)
										.setParameter("companyId", companyId).getSingleOrNull();
		SysUsageSet sys = new SysUsageSet();
		if(entity!=null){
			sys = toDomainSys(entity);
		}
		return Optional.of(sys);
	}

	/**
	 * update UsageSet
	 * @param sysUsageSet
	 * author: Hoang Yen
	 */
	@Override
	public void updateUsageSet(SysUsageSet sysUsageSet) {
		SacmtSysUsageSet entity = toEntitySys(sysUsageSet);
		SacmtSysUsageSet oldEntity = this.queryProxy()
										.find(entity.sacmtSysUsageSetPK, SacmtSysUsageSet.class).get();
		oldEntity.employmentSys = entity.employmentSys;
		oldEntity.personnelSystem = entity.personnelSystem;
		oldEntity.payrollSys = entity.payrollSys;
	}

	/**
	 * insert UsageSet
	 * @param sysUsageSet
	 * author: Hoang Yen
	 */
	@Override
	public void insertUsageSet(SysUsageSet sysUsageSet) {
		SacmtSysUsageSet entity = toEntitySys(sysUsageSet);
		this.commandProxy().insert(entity);
	}
}
