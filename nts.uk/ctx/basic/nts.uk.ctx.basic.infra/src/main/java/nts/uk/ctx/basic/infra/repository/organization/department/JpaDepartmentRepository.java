package nts.uk.ctx.basic.infra.repository.organization.department;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	private static final String FIND_ALL_BY_HISTORY;

	private static final String FIND_SINGLE;

	private static final String CHECK_EXIST;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtDep e");
		builderString.append(" WHERE e.cmnmtDepPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtDepPK.historyId = :historyId");
		FIND_ALL_BY_HISTORY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtDep e");
		builderString.append(" WHERE e.cmnmtDepPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtDepPK.departmentCode = :departmentCode");
		builderString.append(" AND e.cmnmtDepPK.historyId = :historyId");
		FIND_SINGLE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(e)");
		builderString.append(" FROM CmnmtDep e");
		builderString.append(" WHERE e.cmnmtDepPK.companyCode = :companyCode");
		CHECK_EXIST = builderString.toString();
	}

	@Override
	public void add(Department department) {
		this.commandProxy().insert(convertToDbType(department));

	}

	@Override
	public void update(Department department) {
		this.commandProxy().update(convertToDbType(department));

	}

	@Override
	public void remove(String companyCode, DepartmentCode departmentCode, String historyId) {
		this.commandProxy().remove(CmnmtDep.class, new CmnmtDepPK(companyCode, historyId, departmentCode.toString()));

	}

	@Override
	public void registerMemo(String companyCode, String historyId, Memo memo) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Department> findSingleDepartment(String companyCode, DepartmentCode departmentCode,
			String historyId) {
		return this.queryProxy().query(FIND_SINGLE, CmnmtDep.class).setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("workPlaceCode", "'" + departmentCode.toString() + "'")
				.setParameter("historyId", historyId).getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public List<Department> findAllByHistory(String companyCode, String historyId) {
		List<CmnmtDep> resultList = this.queryProxy().query(FIND_ALL_BY_HISTORY, CmnmtDep.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("historyId", "'" + historyId + "'")
				.getList();
		return !resultList.isEmpty() ? resultList.stream().map(item -> {
			return convertToDomain(item);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}

	@Override
	public boolean checkExist(String companyCode) {
		return this.queryProxy().query(CHECK_EXIST, long.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.getSingle().get() > 0;
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
