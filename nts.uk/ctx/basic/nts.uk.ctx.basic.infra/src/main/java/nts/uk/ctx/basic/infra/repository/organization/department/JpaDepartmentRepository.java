package nts.uk.ctx.basic.infra.repository.organization.department;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentCode;
import nts.uk.ctx.basic.dom.organization.department.DepartmentGenericName;
import nts.uk.ctx.basic.dom.organization.department.DepartmentName;
import nts.uk.ctx.basic.dom.organization.department.DepartmentRepository;
import nts.uk.ctx.basic.dom.organization.department.DepartmentShortName;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyLevelCd;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyLevelCode;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDep;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDepPK;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaDepartmentRepository extends JpaRepository implements DepartmentRepository {

	@Override
	public void add(Department department) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Department department) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyCode, DepartmentCode departmentCode, String historyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerMemo(String companyCode, String historyId, Memo memo) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Department> findSingleDepartment(String companyCode, DepartmentCode departmentCode,
			String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findAll(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	private Department convertToDomain(CmnmtDep cmnmtDep) {
		return new Department(cmnmtDep.getCmnmtDepPK().getCompanyCode(),
				new DepartmentCode(cmnmtDep.getCmnmtDepPK().getDepartmentCode()),
				cmnmtDep.getCmnmtDepPK().getHistoryId(), GeneralDate.legacyDate(cmnmtDep.getEndDate()),
				new DepartmentCode(cmnmtDep.getExternalCode()), new DepartmentGenericName(cmnmtDep.getGenericName()),
				new HierarchyCode(cmnmtDep.getHierarchyId()), new DepartmentName(cmnmtDep.getName()),
				new DepartmentShortName(cmnmtDep.getShortName()), GeneralDate.legacyDate(cmnmtDep.getStartDate()),
				new HierarchyLevelCd(new HierarchyLevelCode(cmnmtDep.getHierarchyId01()),
						new HierarchyLevelCode(cmnmtDep.getHierarchyId02()),
						new HierarchyLevelCode(cmnmtDep.getHierarchyId03()),
						new HierarchyLevelCode(cmnmtDep.getHierarchyId04()),
						new HierarchyLevelCode(cmnmtDep.getHierarchyId05()),
						new HierarchyLevelCode(cmnmtDep.getHierarchyId06()),
						new HierarchyLevelCode(cmnmtDep.getHierarchyId07()),
						new HierarchyLevelCode(cmnmtDep.getHierarchyId08()),
						new HierarchyLevelCode(cmnmtDep.getHierarchyId09()),
						new HierarchyLevelCode(cmnmtDep.getHierarchyId10())));

	}

	private CmnmtDep convertToDbType(Department department) {
		CmnmtDep cmnmtDep = new CmnmtDep();
		CmnmtDepPK cmnmtDepPK = new CmnmtDepPK(department.getCompanyCode(), department.getHistoryId(),
				department.getDepartmentCode().toString());
		cmnmtDep.setCmnmtDepPK(cmnmtDepPK);
		cmnmtDep.setEndDate(department.getEndDate().date());
		cmnmtDep.setExternalCode(department.getExternalCode().toString());
		cmnmtDep.setGenericName(department.getGenericName().toString());
		cmnmtDep.setHierarchyId(department.getHierarchyCode().toString());
		cmnmtDep.setHierarchyId01(department.getHierarchyLevelCd().getHierarchyCd01().toString());
		cmnmtDep.setHierarchyId02(department.getHierarchyLevelCd().getHierarchyCd02().toString());
		cmnmtDep.setHierarchyId03(department.getHierarchyLevelCd().getHierarchyCd03().toString());
		cmnmtDep.setHierarchyId04(department.getHierarchyLevelCd().getHierarchyCd04().toString());
		cmnmtDep.setHierarchyId05(department.getHierarchyLevelCd().getHierarchyCd05().toString());
		cmnmtDep.setHierarchyId06(department.getHierarchyLevelCd().getHierarchyCd06().toString());
		cmnmtDep.setHierarchyId07(department.getHierarchyLevelCd().getHierarchyCd07().toString());
		cmnmtDep.setHierarchyId08(department.getHierarchyLevelCd().getHierarchyCd08().toString());
		cmnmtDep.setHierarchyId09(department.getHierarchyLevelCd().getHierarchyCd09().toString());
		cmnmtDep.setHierarchyId10(department.getHierarchyLevelCd().getHierarchyCd10().toString());
		return cmnmtDep;
	}

}
