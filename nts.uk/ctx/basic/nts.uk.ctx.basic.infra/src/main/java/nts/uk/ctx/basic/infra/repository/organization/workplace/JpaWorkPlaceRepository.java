package nts.uk.ctx.basic.infra.repository.organization.workplace;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.ctx.basic.dom.organization.workplace.ParentChildAttribute;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlace;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceCode;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceGenericName;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceMemo;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceName;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceRepository;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceShortName;
import nts.uk.ctx.basic.infra.entity.organization.workplace.CmnmtWorkPlace;
import nts.uk.ctx.basic.infra.entity.organization.workplace.CmnmtWorkPlaceMemo;
import nts.uk.ctx.basic.infra.entity.organization.workplace.CmnmtWorkPlacePK;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaWorkPlaceRepository extends JpaRepository implements WorkPlaceRepository {

	private static final String FIND_SINGLE;

	private static final String CHECK_EXIST;

	private static final String FIND_HISTORIES;

	private static final String FIND_MEMO;

	private static final String FIND_ALL_BY_HISTORY;

	private static final String IS_DUPLICATE_WORK_PLACE_CODE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtWorkPlace e");
		builderString.append(" WHERE e.cmnmtWorkPlacePK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtWorkPlacePK.historyId = :historyId");
		builderString.append(" ORDER BY e.hierarchyId");
		FIND_ALL_BY_HISTORY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtWorkPlace e");
		builderString.append(" WHERE e.cmnmtWorkPlacePK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtWorkPlacePK.workPlaceCode = :workPlaceCode");
		builderString.append(" AND e.cmnmtWorkPlacePK.historyId = :historyId");
		FIND_SINGLE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(e)");
		builderString.append(" FROM CmnmtWorkPlace e");
		builderString.append(" WHERE e.cmnmtWorkPlacePK.companyCode = :companyCode");
		CHECK_EXIST = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e.cmnmtWorkPlacePK.historyId");
		builderString.append(" ,e.startDate");
		builderString.append(" ,e.endDate");
		builderString.append(" FROM CmnmtWorkPlace e");
		builderString.append(" WHERE e.cmnmtWorkPlacePK.companyCode = :companyCode");
		builderString.append(" GROUP BY e.cmnmtWorkPlacePK.historyId, e.startDate, e.endDate");
		builderString.append(" ORDER BY e.startDate DESC");
		FIND_HISTORIES = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtWorkPlaceMemo e");
		builderString.append(" WHERE e.cmnmtWorkPlaceMemoPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtWorkPlaceMemoPK.historyId = :historyId");
		FIND_MEMO = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(e)");
		builderString.append(" FROM CmnmtWorkPlace e");
		builderString.append(" WHERE e.cmnmtWorkPlacePK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtWorkPlacePK.workPlaceCode = :workPlaceCode");
		IS_DUPLICATE_WORK_PLACE_CODE = builderString.toString();
	}

	@Override
	public void add(WorkPlace workPlace) {
		this.commandProxy().insert(convertToDbType(workPlace));

	}

	@Override
	public void update(WorkPlace workPlace) {
		this.commandProxy().update(convertToDbType(workPlace));

	}

	@Override
	public void remove(String companyCode, WorkPlaceCode workPlaceCode, String historyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerMemo(String companyCode, String historyId, Memo memo) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<WorkPlace> findSingle(String companyCode, WorkPlaceCode workPlaceCode, String historyId) {
		return this.queryProxy().query(FIND_SINGLE, CmnmtWorkPlace.class).setParameter("companyCode", companyCode)
				.setParameter("workPlaceCode", workPlaceCode.toString()).setParameter("historyId", historyId)
				.getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public List<WorkPlace> findAllByHistory(String companyCode, String historyId) {
		List<CmnmtWorkPlace> resultList = this.queryProxy().query(FIND_ALL_BY_HISTORY, CmnmtWorkPlace.class)
				.setParameter("companyCode", companyCode )
				.setParameter("historyId", historyId ).getList();
		System.out.println(resultList);
		return !resultList.isEmpty() ? resultList.stream().map(item -> {
			return convertToDomain(item);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}

	@Override
	public List<WorkPlace> findHistories(String companyCode) {
		List<Object[]> resultList = this.queryProxy().query(FIND_HISTORIES).setParameter("companyCode", companyCode)
				.getList();
		
		return resultList.stream().map(e -> new WorkPlace((String) e[0], (GeneralDate) e[1], (GeneralDate) e[2]))
				.collect(Collectors.toList());
	}

	@Override
	public boolean checkExist(String companyCode) {
		
		return this.queryProxy().query(CHECK_EXIST, long.class).setParameter("companyCode", companyCode).getSingle()
				.get() > 0;
	}

	@Override
	public Optional<WorkPlaceMemo> findMemo(String companyCode, String historyId) {
		return this.queryProxy().query(FIND_MEMO, CmnmtWorkPlaceMemo.class).setParameter("companyCode", companyCode)
				.setParameter("historyId", historyId).getSingle().map(e -> {
					return Optional.of(new WorkPlaceMemo(e.getCmnmtWorkPlaceMemoPK().getCompanyCode(),
							e.getCmnmtWorkPlaceMemoPK().getHistoryId(), new Memo(e.getMemo())));
				}).orElse(Optional.empty());
	}

	private WorkPlace convertToDomain(CmnmtWorkPlace cmnmtWorkPlace) {
		return new WorkPlace(cmnmtWorkPlace.getCmnmtWorkPlacePK().getCompanyCode(),
				new WorkPlaceCode(cmnmtWorkPlace.getCmnmtWorkPlacePK().getWorkPlaceCode()),
				cmnmtWorkPlace.getCmnmtWorkPlacePK().getHistoryId(),
				cmnmtWorkPlace.getEndDate(),
				new WorkPlaceCode(cmnmtWorkPlace.getExternalCode()),
				new WorkPlaceGenericName(cmnmtWorkPlace.getGenericName()), new HierarchyCode(cmnmtWorkPlace.getHierarchyId()),
				new WorkPlaceName(cmnmtWorkPlace.getName()),
				new ParentChildAttribute(new BigDecimal(cmnmtWorkPlace.getParentChildAttribute1())),
				new ParentChildAttribute(new BigDecimal(cmnmtWorkPlace.getParentChildAttribute2())),
				new WorkPlaceCode(cmnmtWorkPlace.getParentWorkCode1()),
				new WorkPlaceCode(cmnmtWorkPlace.getParentWorkCode2()),
				new WorkPlaceShortName(cmnmtWorkPlace.getShortName()),
				cmnmtWorkPlace.getStartDate());
	}

	private CmnmtWorkPlace convertToDbType(WorkPlace workPlace) {
		CmnmtWorkPlace cmnmtWorkPlace = new CmnmtWorkPlace();
		CmnmtWorkPlacePK cmnmtWorkPlacePK = new CmnmtWorkPlacePK();
		cmnmtWorkPlacePK.setCompanyCode(workPlace.getCompanyCode());
		cmnmtWorkPlacePK.setHistoryId(workPlace.getHistoryId());
		cmnmtWorkPlacePK.setWorkPlaceCode(workPlace.getWorkPlaceCode().toString());
		cmnmtWorkPlace.setCmnmtWorkPlacePK(cmnmtWorkPlacePK);
		cmnmtWorkPlace.setEndDate(workPlace.getEndDate());
		cmnmtWorkPlace.setExternalCode(workPlace.getExternalCode().toString());
		cmnmtWorkPlace.setGenericName(workPlace.getGenericName().v());
		cmnmtWorkPlace.setHierarchyId(workPlace.getHierarchyCode().toString());
		cmnmtWorkPlace.setName(workPlace.getName().toString());
		cmnmtWorkPlace.setParentChildAttribute1(workPlace.getParentChildAttribute1().toString());
		cmnmtWorkPlace.setParentChildAttribute2(workPlace.getParentChildAttribute2().toString());
		cmnmtWorkPlace.setParentWorkCode1(workPlace.getParentWorkCode1().toString());
		cmnmtWorkPlace.setParentWorkCode2(workPlace.getParentWorkCode2().toString());
		cmnmtWorkPlace.setShortName(workPlace.getShortName().toString());
		cmnmtWorkPlace.setStartDate(workPlace.getStartDate());
		return cmnmtWorkPlace;
	}

	@Override
	public boolean isDuplicateWorkPlaceCode(String companyCode, WorkPlaceCode workPlaceCode) {
		return this.queryProxy().query(IS_DUPLICATE_WORK_PLACE_CODE, long.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("departmentCode", "'" + workPlaceCode.toString() + "'").getSingle().get() > 0;
	}

}
