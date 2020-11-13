/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshmtLegalTimeMCom;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshmtLegalTimeMComPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshmtLegalTimeMSya;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshmtLegalTimeMSyaPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshmtLegalTimeMEmp;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshmtLegalTimeMEmpPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshmtLegalTimeMWkp;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshmtLegalTimeMWkpPK;

/**
 * The Class JpaComNormalSettingRepository.
 */
@Stateless
public class JpaMonthlyWorkTimeSetRepo extends JpaRepository implements MonthlyWorkTimeSetRepo {

	private static final String SELECT_YEAR_COM = "SELECT x FROM KshmtLegalTimeMCom x "
			+ "WHERE x.pk.cid = :cid AND x.pk.type = :type AND x.pk.ym >= :start "
			+ "AND x.pk.ym <= :end "
			+ "ORDER BY x.pk.ym ASC";
	private static final String SELECT_YEAR_SYA = "SELECT x FROM KshmtLegalTimeMSya x "
			+ "WHERE x.pk.cid = :cid AND x.pk.type = :type AND x.pk.ym >= :start "
			+ "AND x.pk.ym <= :end AND x.pk.sid = :sid";
	private static final String SELECT_YEAR_WKP = "SELECT x FROM KshmtLegalTimeMWkp x "
			+ "WHERE x.pk.cid = :cid AND x.pk.type = :type AND x.pk.ym >= :start "
			+ "AND x.pk.ym <= :end AND x.pk.wkpId = :wkpId";
	private static final String SELECT_YEAR_EMP = "SELECT x FROM KshmtLegalTimeMEmp x "
			+ "WHERE x.pk.cid = :cid AND x.pk.type = :type AND x.pk.ym >= :start "
			+ "AND x.pk.ym <= :end AND x.pk.empCD = :empCD";
	
	private static final String SELECT_EMP_BY_CID = "SELECT x FROM KshmtLegalTimeMEmp x "
			+ "WHERE x.pk.cid = :cid ";

	@Override
	public Optional<MonthlyWorkTimeSetCom> findCompany(String cid, LaborWorkTypeAttr laborAttr, YearMonth ym) {
		
		return this.queryProxy().find(new KshmtLegalTimeMComPK(cid, laborAttr.value, ym.v()), KshmtLegalTimeMCom.class)
				.map(c -> MonthlyWorkTimeSetCom.of(cid, laborAttr, ym, c.domain()));
	}

	@Override
	public Optional<MonthlyWorkTimeSetEmp> findEmployment(String cid, String empCode, LaborWorkTypeAttr laborAttr,
			YearMonth ym) {

		return this.queryProxy().find(new KshmtLegalTimeMEmpPK(cid, empCode, laborAttr.value, ym.v()), KshmtLegalTimeMEmp.class)
				.map(c -> MonthlyWorkTimeSetEmp.of(cid, new EmploymentCode(empCode), laborAttr, ym, c.domain()));
	}

	@Override
	public Optional<MonthlyWorkTimeSetSha> findEmployee(String cid, String sid, LaborWorkTypeAttr laborAttr,
			YearMonth ym) {

		return this.queryProxy().find(new KshmtLegalTimeMSyaPK(cid, sid, laborAttr.value, ym.v()), KshmtLegalTimeMSya.class)
				.map(c -> MonthlyWorkTimeSetSha.of(cid, sid, laborAttr, ym, c.domain()));
	}

	@Override
	public Optional<MonthlyWorkTimeSetWkp> findWorkplace(String cid, String workplaceId, LaborWorkTypeAttr laborAttr,
			YearMonth ym) {

		return this.queryProxy().find(new KshmtLegalTimeMWkpPK(cid, workplaceId, laborAttr.value, ym.v()), KshmtLegalTimeMWkp.class)
				.map(c -> MonthlyWorkTimeSetWkp.of(cid, workplaceId, laborAttr, ym, c.domain()));
	}

	@Override
	public List<MonthlyWorkTimeSetCom> findCompany(String cid, LaborWorkTypeAttr laborAttr, int year) {
		return this.queryProxy().query(SELECT_YEAR_COM, KshmtLegalTimeMCom.class)
				.setParameter("cid", cid)
				.setParameter("type", laborAttr.value)
				.setParameter("start", year * 100 + 01)
				.setParameter("end", year * 100 + 12)
				.getList(c -> MonthlyWorkTimeSetCom.of(cid, laborAttr, new YearMonth(c.pk.ym), c.domain()));
	}

	@Override
	public List<MonthlyWorkTimeSetEmp> findEmployment(String cid, String empCode, LaborWorkTypeAttr laborAttr,
			int year) {
		return this.queryProxy().query(SELECT_YEAR_EMP, KshmtLegalTimeMEmp.class)
				.setParameter("cid", cid)
				.setParameter("type", laborAttr.value)
				.setParameter("start", year * 100 + 01)
				.setParameter("end", year * 100 + 12)
				.setParameter("empCD", empCode)
				.getList(c -> MonthlyWorkTimeSetEmp.of(cid, new EmploymentCode(empCode), laborAttr, new YearMonth(c.pk.ym), c.domain()));
	}

	@Override
	public List<MonthlyWorkTimeSetSha> findEmployee(String cid, String sid, LaborWorkTypeAttr laborAttr, int year) {
		return this.queryProxy().query(SELECT_YEAR_SYA, KshmtLegalTimeMSya.class)
				.setParameter("cid", cid)
				.setParameter("type", laborAttr.value)
				.setParameter("start", year * 100 + 01)
				.setParameter("end", year * 100 + 12)
				.setParameter("sid", sid)
				.getList(c -> MonthlyWorkTimeSetSha.of(cid, sid, laborAttr, new YearMonth(c.pk.ym), c.domain()));
	}

	@Override
	public List<MonthlyWorkTimeSetWkp> findWorkplace(String cid, String workplaceId, LaborWorkTypeAttr laborAttr,
			int year) {
		return this.queryProxy().query(SELECT_YEAR_WKP, KshmtLegalTimeMWkp.class)
				.setParameter("cid", cid)
				.setParameter("type", laborAttr.value)
				.setParameter("start", year * 100 + 01)
				.setParameter("end", year * 100 + 12)
				.setParameter("wkpId", workplaceId)
				.getList(c -> MonthlyWorkTimeSetWkp.of(cid, workplaceId, laborAttr, new YearMonth(c.pk.ym), c.domain()));
	}

	@Override
	public void add(MonthlyWorkTimeSetCom domain) {
		val pk = new KshmtLegalTimeMComPK(domain.getComId(), domain.getLaborAttr().value, domain.getYm().v());
		val entity = new KshmtLegalTimeMCom(pk);
		entity.transfer(domain.getLaborTime());
		
		this.commandProxy().insert(entity);
	}

	@Override
	public void add(MonthlyWorkTimeSetEmp domain) {
		val pk = new KshmtLegalTimeMEmpPK(domain.getComId(), domain.getEmployment().v(), domain.getLaborAttr().value, domain.getYm().v());
		val entity = new KshmtLegalTimeMEmp(pk);
		entity.transfer(domain.getLaborTime());
		
		this.commandProxy().insert(entity);
	}

	@Override
	public void add(MonthlyWorkTimeSetSha domain) {
		val pk = new KshmtLegalTimeMSyaPK(domain.getComId(), domain.getEmpId(), domain.getLaborAttr().value, domain.getYm().v());
		val entity = new KshmtLegalTimeMSya(pk);
		entity.transfer(domain.getLaborTime());
		
		this.commandProxy().insert(entity);
	}

	@Override
	public void add(MonthlyWorkTimeSetWkp domain) {
		val pk = new KshmtLegalTimeMWkpPK(domain.getComId(), domain.getWorkplaceId(), domain.getLaborAttr().value, domain.getYm().v());
		val entity = new KshmtLegalTimeMWkp(pk);
		entity.transfer(domain.getLaborTime());
		
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(MonthlyWorkTimeSetCom domain) {
		val pk = new KshmtLegalTimeMComPK(domain.getComId(), domain.getLaborAttr().value, domain.getYm().v());

		this.queryProxy().find(pk, KshmtLegalTimeMCom.class).ifPresent(entity -> {
			entity.transfer(domain.getLaborTime());
			
			this.commandProxy().update(entity);
		});
	}

	@Override
	public void update(MonthlyWorkTimeSetEmp domain) {
		val pk = new KshmtLegalTimeMEmpPK(domain.getComId(), domain.getEmployment().v(), domain.getLaborAttr().value, domain.getYm().v());

		this.queryProxy().find(pk, KshmtLegalTimeMEmp.class).ifPresent(entity -> {
			entity.transfer(domain.getLaborTime());
			
			this.commandProxy().update(entity);
		});
	}

	@Override
	public void update(MonthlyWorkTimeSetSha domain) {
		val pk = new KshmtLegalTimeMSyaPK(domain.getComId(), domain.getEmpId(), domain.getLaborAttr().value, domain.getYm().v());

		this.queryProxy().find(pk, KshmtLegalTimeMSya.class).ifPresent(entity -> {
			entity.transfer(domain.getLaborTime());
			
			this.commandProxy().update(entity);
		});
	}

	@Override
	public void update(MonthlyWorkTimeSetWkp domain) {
		val pk = new KshmtLegalTimeMWkpPK(domain.getComId(), domain.getWorkplaceId(), domain.getLaborAttr().value, domain.getYm().v());

		this.queryProxy().find(pk, KshmtLegalTimeMWkp.class).ifPresent(entity -> {
			entity.transfer(domain.getLaborTime());
			
			this.commandProxy().update(entity);
		});
	}

	@Override
	public List<String> findEmploymentCD(String cid, int year) {
		
		return this.queryProxy().query("SELECT x FROM KshmtLegalTimeMEmp x "
			+ "WHERE x.pk.cid = :cid  AND x.pk.ym >= :start "
			+ "AND x.pk.ym <= :end", KshmtLegalTimeMEmp.class)
				.setParameter("cid", cid)
				.setParameter("start", year * 100 + 01)
				.setParameter("end", year * 100 + 12)
				.getList(c -> c.pk.empCD);
	}

	@Override
	public List<String> findWorkplaceID(String cid, int year) {
		
		return this.queryProxy().query("SELECT x FROM KshmtLegalTimeMWkp x "
			+ "WHERE x.pk.cid = :cid  AND x.pk.ym >= :start "
			+ "AND x.pk.ym <= :end", KshmtLegalTimeMWkp.class)
				.setParameter("cid", cid)
				.setParameter("start", year * 100 + 01)
				.setParameter("end", year * 100 + 12)
				.getList(c -> c.pk.wkpId);
	}

	@Override
	public void removeCompany(String cid, int year) {
		this.queryProxy().query("SELECT x FROM KshmtLegalTimeMCom x "
				+ "WHERE x.pk.cid = :cid  AND x.pk.ym >= :start "
				+ "AND x.pk.ym <= :end", KshmtLegalTimeMCom.class)
					.setParameter("cid", cid)
					.setParameter("start", year * 100 + 01)
					.setParameter("end", year * 100 + 12)
					.getList().forEach(c -> commandProxy().remove(cid));
	}

	@Override
	public List<MonthlyWorkTimeSetSha> findEmployee(String cid, String sid, LaborWorkTypeAttr laborAttr) {
		
		return this.queryProxy().query("SELECT x FROM KshmtLegalTimeMSya x "
			+ "WHERE x.pk.cid = :cid  AND x.pk.type = :type AND x.pk.sid = :sid", KshmtLegalTimeMSya.class)
				.setParameter("cid", cid)
				.setParameter("type", laborAttr.value)
				.setParameter("sid", sid)
				.getList(c -> MonthlyWorkTimeSetSha.of(cid, sid, laborAttr, new YearMonth(c.pk.ym), c.domain()));
	}

	@Override
	public List<MonthlyWorkTimeSetWkp> findWorkplace(String cid, String wkpId, LaborWorkTypeAttr laborAttr) {
		
		return this.queryProxy().query("SELECT x FROM KshmtLegalTimeMWkp x "
			+ "WHERE x.pk.cid = :cid  AND x.pk.type = :type AND x.pk.wkpId = :wkpId", KshmtLegalTimeMWkp.class)
				.setParameter("cid", cid)
				.setParameter("type", laborAttr.value)
				.setParameter("sid", wkpId)
				.getList(c -> MonthlyWorkTimeSetWkp.of(cid, wkpId, laborAttr, new YearMonth(c.pk.ym), c.domain()));
	}

	@Override
	public List<MonthlyWorkTimeSetEmp> findEmployment(String cid, String empCD, LaborWorkTypeAttr laborAttr) {
		
		return this.queryProxy().query("SELECT x FROM KshmtLegalTimeMEmp x "
			+ "WHERE x.pk.cid = :cid  AND x.pk.type = :type AND x.pk.empCD = :empCD", KshmtLegalTimeMEmp.class)
				.setParameter("cid", cid)
				.setParameter("type", laborAttr.value)
				.setParameter("empCD", empCD)
				.getList(c -> MonthlyWorkTimeSetEmp.of(cid, new EmploymentCode(empCD), laborAttr, new YearMonth(c.pk.ym), c.domain()));
	}

	@Override
	public void removeEmployee(String cid, String sid, int year) {
		this.queryProxy().query("SELECT x FROM KshmtLegalTimeMSya x "
				+ "WHERE x.pk.cid = :cid  AND x.pk.ym >= :start "
				+ "AND x.pk.ym <= :end AND x.pk.sid = :sid", KshmtLegalTimeMSya.class)
					.setParameter("cid", cid)
					.setParameter("sid", sid)
					.setParameter("start", year * 100 + 01)
					.setParameter("end", year * 100 + 12)
					.getList().forEach(c -> commandProxy().remove(cid));
	}

	@Override
	public void removeEmployment(String cid, String empCD, int year) {
		this.queryProxy().query("SELECT x FROM KshmtLegalTimeMEmp x "
				+ "WHERE x.pk.cid = :cid  AND x.pk.ym >= :start "
				+ "AND x.pk.ym <= :end AND c.pk.empCD = :empCD", KshmtLegalTimeMEmp.class)
					.setParameter("cid", cid)
					.setParameter("empCD", empCD)
					.setParameter("start", year * 100 + 01)
					.setParameter("end", year * 100 + 12)
					.getList().forEach(c -> commandProxy().remove(cid));
	}

	@Override
	public void removeWorkplace(String cid, String wkpId, int year) {
		this.queryProxy().query("SELECT x FROM KshmtLegalTimeMWkp x "
				+ "WHERE x.pk.cid = :cid  AND x.pk.ym >= :start "
				+ "AND x.pk.ym <= :end AND c.pk.wkpId = :wkpId", KshmtLegalTimeMWkp.class)
					.setParameter("cid", cid)
					.setParameter("wkpId", wkpId)
					.setParameter("start", year * 100 + 01)
					.setParameter("end", year * 100 + 12)
					.getList().forEach(c -> commandProxy().remove(cid));
	}

	@Override
	public List<MonthlyWorkTimeSetEmp> findEmploymentbyCid(String cid) {
		List<KshmtLegalTimeMEmp> kshmtLegalTimeMEmps =  this.queryProxy().query(SELECT_EMP_BY_CID, KshmtLegalTimeMEmp.class)
				.setParameter("cid", cid)
				.getList();
		
		return null;
	}

	@Override
	public List<MonthlyWorkTimeSetCom> findCompanyByPeriod(String cid, LaborWorkTypeAttr laborAttr,
			YearMonthPeriod yearMonthPeriod) {
		
		String startMonth = yearMonthPeriod.start().month() < 9 
				? "0" + String.valueOf(yearMonthPeriod.start().month()) 
				: String.valueOf(yearMonthPeriod.start().month());
		
		String start = String.valueOf(yearMonthPeriod.start().year()) + startMonth;
		
		String endMonth = yearMonthPeriod.end().month() < 9 
				? "0" + String.valueOf(yearMonthPeriod.end().month()) 
				: String.valueOf(yearMonthPeriod.end().month());
		
		String end = String.valueOf(yearMonthPeriod.end().year()) + endMonth;
		
		
		return this.queryProxy().query(SELECT_YEAR_COM, KshmtLegalTimeMCom.class)
				.setParameter("cid", cid)
				.setParameter("type", laborAttr.value)
				.setParameter("start", Integer.parseInt(start))
				.setParameter("end", Integer.parseInt(end))
				.getList(c -> MonthlyWorkTimeSetCom.of(cid, laborAttr, new YearMonth(c.pk.ym), c.domain()));
	}
}
