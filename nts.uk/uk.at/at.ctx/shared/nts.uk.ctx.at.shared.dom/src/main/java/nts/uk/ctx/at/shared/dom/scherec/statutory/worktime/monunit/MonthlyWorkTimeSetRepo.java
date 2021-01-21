package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;

public interface MonthlyWorkTimeSetRepo {

	public Optional<MonthlyWorkTimeSetCom> findCompany(String cid, LaborWorkTypeAttr laborAttr, YearMonth ym);

	public Optional<MonthlyWorkTimeSetEmp> findEmployment(String cid, String empCode, LaborWorkTypeAttr laborAttr, YearMonth ym);

	public Optional<MonthlyWorkTimeSetSha> findEmployee(String cid, String sid, LaborWorkTypeAttr laborAttr, YearMonth ym);

	public Optional<MonthlyWorkTimeSetWkp> findWorkplace(String cid, String workplaceId, LaborWorkTypeAttr laborAttr, YearMonth ym);
	
	public List<MonthlyWorkTimeSetCom> findCompany(String cid, LaborWorkTypeAttr laborAttr, int year);

	public List<MonthlyWorkTimeSetEmp> findEmployment(String cid, String empCode, LaborWorkTypeAttr laborAttr, int year);

	public List<MonthlyWorkTimeSetSha> findEmployee(String cid, String sid, LaborWorkTypeAttr laborAttr, int year);

	public List<MonthlyWorkTimeSetWkp> findWorkplace(String cid, String workplaceId, LaborWorkTypeAttr laborAttr, int year);
	
	public List<String> findEmploymentCD(String cid, int year);
	
	public List<String> findWorkplaceID(String cid, int year);
	
	public List<MonthlyWorkTimeSetSha> findEmployee(String cid, String sid, LaborWorkTypeAttr laborAttr);
	
	public List<MonthlyWorkTimeSetEmp> findEmployment(String cid, String empCD, LaborWorkTypeAttr laborAttr);
	
	public List<MonthlyWorkTimeSetWkp> findWorkplace(String cid, String wkpId, LaborWorkTypeAttr laborAttr);
	
	public void add(MonthlyWorkTimeSetCom domain);
	
	public void add(MonthlyWorkTimeSetEmp domain);
	
	public void add(MonthlyWorkTimeSetSha domain);
	
	public void add(MonthlyWorkTimeSetWkp domain);

	public void update(MonthlyWorkTimeSetCom domain);
	
	public void update(MonthlyWorkTimeSetEmp domain);
	
	public void update(MonthlyWorkTimeSetSha domain);
	
	public void update(MonthlyWorkTimeSetWkp domain);
	
	public void removeCompany(String cid, int year);
	
	public void removeEmployee(String cid, String sid, int year);
	
	public void removeEmployment(String cid, String empCD, int year);
	
	public void removeWorkplace(String cid, String wkpId, int year);
}
