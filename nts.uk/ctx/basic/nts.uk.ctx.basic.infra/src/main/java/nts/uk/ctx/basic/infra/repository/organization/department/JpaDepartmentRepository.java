package nts.uk.ctx.basic.infra.repository.organization.department;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentCode;
import nts.uk.ctx.basic.dom.organization.department.DepartmentFullName;
import nts.uk.ctx.basic.dom.organization.department.DepartmentMemo;
import nts.uk.ctx.basic.dom.organization.department.DepartmentName;
import nts.uk.ctx.basic.dom.organization.department.DepartmentRepository;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDep;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDepMemo;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDepPK;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaDepartmentRepository extends JpaRepository implements DepartmentRepository {

	private static final String FIND_ALL_BY_HISTORY;

	private static final String FIND_SINGLE;

	private static final String CHECK_EXIST;

	private static final String FIND_HISTORIES;

	private static final String FIND_MEMO;

	private static final String IS_DUPLICATE_DEPARTMENT_CODE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtDep e");
		builderString.append(" WHERE e.cmnmtDepPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtDepPK.historyId = :historyId");
		builderString.append(" ORDER BY e.hierarchyId");
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

		builderString = new StringBuilder();
		builderString.append("SELECT e.cmnmtDepPK.historyId");
		builderString.append(" ,e.startDate");
		builderString.append(" ,e.endDate");
		builderString.append(" FROM CmnmtDep e");
		builderString.append(" WHERE e.cmnmtDepPK.companyCode = :companyCode");
		builderString.append(" GROUP BY e.cmnmtDepPK.historyId, e.startDate, e.endDate");
		builderString.append(" ORDER BY e.startDate ASC");
		FIND_HISTORIES = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtDepMemo e");
		builderString.append(" WHERE e.cmnmtDepMemoPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtDepMemoPK.historyId = :historyId");
		FIND_MEMO = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(e)");
		builderString.append(" FROM CmnmtDep e");
		builderString.append(" WHERE e.cmnmtDepPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtDepPK.departmentCode = :departmentCode");
		IS_DUPLICATE_DEPARTMENT_CODE = builderString.toString();
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
				.setParameter("departmentCode", "'" + departmentCode.toString() + "'")
				.setParameter("historyId", historyId).getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public List<Department> findAllByHistory(String companyCode, String historyId) {
		List<CmnmtDep> resultList = this.queryProxy().query(FIND_ALL_BY_HISTORY, CmnmtDep.class)
				.setParameter("companyCode", "'" + companyCode + "'").setParameter("historyId", "'" + historyId + "'")
				.getList();
		return !resultList.isEmpty() ? resultList.stream().map(item -> {
			return convertToDomain(item);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}

	@Override
	public boolean checkExist(String companyCode) {
		return this.queryProxy().query(CHECK_EXIST, long.class).setParameter("companyCode", "'" + companyCode + "'")
				.getSingle().get() > 0;
	}

	@Override
	public List<Department> findHistories(String companyCode) {
		List<Object[]> resultList = this.queryProxy().query(FIND_HISTORIES)
				.setParameter("companyCode", "'" + companyCode + "'").getList();
		return resultList.stream().map(e -> new Department((String) e[0], GeneralDate.legacyDate((Date) e[2]),
				GeneralDate.legacyDate((Date) e[1]))).collect(Collectors.toList());
	}

	@Override
	public Optional<DepartmentMemo> findMemo(String companyCode, String historyId) {
		return this.queryProxy().query(FIND_MEMO, CmnmtDepMemo.class)
				.setParameter("companyCode", "'" + companyCode + "'").setParameter("historyId", "'" + historyId + "'")
				.getSingle().map(e -> {
					return Optional.of(new DepartmentMemo(e.getCmnmtDepMemoPK().getCompanyCode(),
							e.getCmnmtDepMemoPK().getHistoryId(), new Memo(e.getMemo())));
				}).orElse(Optional.empty());
	}

	private Department convertToDomain(CmnmtDep cmnmtDep) {
		return new Department(cmnmtDep.getCmnmtDepPK().getCompanyCode(),
				new DepartmentCode(cmnmtDep.getCmnmtDepPK().getDepartmentCode()),
				cmnmtDep.getCmnmtDepPK().getHistoryId(), GeneralDate.legacyDate(cmnmtDep.getEndDate()),
				new DepartmentCode(cmnmtDep.getExternalCode()), new DepartmentFullName(cmnmtDep.getFullName()),
				new HierarchyCode(cmnmtDep.getHierarchyId()), new DepartmentName(cmnmtDep.getName()),
				GeneralDate.legacyDate(cmnmtDep.getStartDate()));

	}

	private CmnmtDep convertToDbType(Department department) {
		CmnmtDep cmnmtDep = new CmnmtDep();
		CmnmtDepPK cmnmtDepPK = new CmnmtDepPK(department.getCompanyCode(), department.getHistoryId(),
				department.getDepartmentCode().toString());
		cmnmtDep.setCmnmtDepPK(cmnmtDepPK);
		cmnmtDep.setEndDate(department.getEndDate().date());
		cmnmtDep.setExternalCode(department.getExternalCode().toString());
		cmnmtDep.setFullName(department.getFullName().toString());
		cmnmtDep.setHierarchyId(department.getHierarchyCode().toString());
		return cmnmtDep;
	}

	@Override
	public boolean isDuplicateDepartmentCode(String companyCode, DepartmentCode departmentCode) {
		return this.queryProxy().query(IS_DUPLICATE_DEPARTMENT_CODE, long.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("departmentCode", "'" + departmentCode.toString() + "'").getSingle().get() > 0;
	}

}
