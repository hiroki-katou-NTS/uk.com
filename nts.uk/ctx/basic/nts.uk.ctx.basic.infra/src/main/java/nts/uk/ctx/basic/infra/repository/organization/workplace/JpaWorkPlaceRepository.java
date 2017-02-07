package nts.uk.ctx.basic.infra.repository.organization.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyLevelCd;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyLevelCode;
import nts.uk.ctx.basic.dom.organization.workplace.ParentChildAttribute;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlace;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceCode;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceGenericName;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceName;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceRepository;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceShortName;
import nts.uk.ctx.basic.infra.entity.organization.workplace.CmnmtWorkPlace;
import nts.uk.ctx.basic.infra.entity.organization.workplace.CmnmtWorkPlacePK;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaWorkPlaceRepository extends JpaRepository implements WorkPlaceRepository {

	private static final String FIND_SINGLE;

	private static final String FIND_ALL;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtWorkPlace");
		builderString.append(" WHERE e.cmnmtWorkPlacePK.companyCode = :companyCode");
		FIND_ALL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtWorkPlace");
		builderString.append(" WHERE e.cmnmtWorkPlacePK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtWorkPlacePK.workPlaceCode = :workPlaceCode");
		builderString.append(" AND e.cmnmtWorkPlacePK.historyId = :historyId");
		FIND_SINGLE = builderString.toString();
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
		return this.queryProxy().query(FIND_SINGLE, CmnmtWorkPlace.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("workPlaceCode", "'" + workPlaceCode.toString() + "'")
				.setParameter("historyId", historyId).getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public List<WorkPlace> findAll(String companyCode) {
		List<CmnmtWorkPlace> resultList = this.queryProxy().query(FIND_ALL, CmnmtWorkPlace.class)
				.setParameter("companyCode", companyCode).getList();
		return !resultList.isEmpty() ? resultList.stream().map(e -> {
			return convertToDomain(e);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}

	private WorkPlace convertToDomain(CmnmtWorkPlace cmnmtWorkPlace) {
		HierarchyLevelCd hierarchyLevelCd = new HierarchyLevelCd(
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId01()),
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId02()),
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId03()),
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId04()),
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId05()),
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId06()),
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId07()),
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId08()),
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId09()),
				new HierarchyLevelCode(cmnmtWorkPlace.getHierarchyId10()));
		return new WorkPlace(cmnmtWorkPlace.getCmnmtWorkPlacePK().getCompanyCode(),
				new WorkPlaceCode(cmnmtWorkPlace.getCmnmtWorkPlacePK().getWorkPlaceCode()),
				cmnmtWorkPlace.getCmnmtWorkPlacePK().getHistoryId(),
				GeneralDate.legacyDate(cmnmtWorkPlace.getEndDate()),
				new WorkPlaceCode(cmnmtWorkPlace.getExternalCode()),
				new WorkPlaceGenericName(cmnmtWorkPlace.getGenericName()),
				new HierarchyCode(cmnmtWorkPlace.getHierarchyId()), hierarchyLevelCd,
				new WorkPlaceName(cmnmtWorkPlace.getName()),
				new ParentChildAttribute(cmnmtWorkPlace.getParentChildAttribute1()),
				new ParentChildAttribute(cmnmtWorkPlace.getParentChildAttribute2()),
				new WorkPlaceCode(cmnmtWorkPlace.getParentWorkCode1()),
				new WorkPlaceCode(cmnmtWorkPlace.getParentWorkCode2()),
				new WorkPlaceShortName(cmnmtWorkPlace.getShortName()),
				GeneralDate.legacyDate(cmnmtWorkPlace.getStartDate()));
	}

	private CmnmtWorkPlace convertToDbType(WorkPlace workPlace) {
		CmnmtWorkPlace cmnmtWorkPlace = new CmnmtWorkPlace();
		CmnmtWorkPlacePK cmnmtWorkPlacePK = new CmnmtWorkPlacePK();
		cmnmtWorkPlacePK.setCompanyCode(workPlace.getCompanyCode());
		cmnmtWorkPlacePK.setHistoryId(workPlace.getHistoryId());
		cmnmtWorkPlacePK.setWorkPlaceCode(workPlace.getWorkPlaceCode().toString());
		cmnmtWorkPlace.setCmnmtWorkPlacePK(cmnmtWorkPlacePK);
		cmnmtWorkPlace.setEndDate(workPlace.getEndDate().date());
		cmnmtWorkPlace.setExternalCode(workPlace.getExternalCode().toString());
		cmnmtWorkPlace.setGenericName(workPlace.getGenericName().toString());
		cmnmtWorkPlace.setHierarchyId(workPlace.getHierarchyCode().toString());
		cmnmtWorkPlace.setHierarchyId01(workPlace.getHierarchyLevelCd().getHierarchyCd01().toString());
		cmnmtWorkPlace.setHierarchyId02(workPlace.getHierarchyLevelCd().getHierarchyCd02().toString());
		cmnmtWorkPlace.setHierarchyId03(workPlace.getHierarchyLevelCd().getHierarchyCd03().toString());
		cmnmtWorkPlace.setHierarchyId04(workPlace.getHierarchyLevelCd().getHierarchyCd04().toString());
		cmnmtWorkPlace.setHierarchyId05(workPlace.getHierarchyLevelCd().getHierarchyCd05().toString());
		cmnmtWorkPlace.setHierarchyId06(workPlace.getHierarchyLevelCd().getHierarchyCd06().toString());
		cmnmtWorkPlace.setHierarchyId07(workPlace.getHierarchyLevelCd().getHierarchyCd07().toString());
		cmnmtWorkPlace.setHierarchyId08(workPlace.getHierarchyLevelCd().getHierarchyCd08().toString());
		cmnmtWorkPlace.setHierarchyId09(workPlace.getHierarchyLevelCd().getHierarchyCd09().toString());
		cmnmtWorkPlace.setHierarchyId10(workPlace.getHierarchyLevelCd().getHierarchyCd10().toString());
		cmnmtWorkPlace.setName(workPlace.getName().toString());
		cmnmtWorkPlace.setParentChildAttribute1(workPlace.getParentChildAttribute1().v());
		cmnmtWorkPlace.setParentChildAttribute2(workPlace.getParentChildAttribute2().v());
		cmnmtWorkPlace.setParentWorkCode1(workPlace.getParentWorkCode1().toString());
		cmnmtWorkPlace.setParentWorkCode2(workPlace.getParentWorkCode2().toString());
		cmnmtWorkPlace.setShortName(workPlace.getShortName().toString());
		cmnmtWorkPlace.setStartDate(workPlace.getStartDate().date());
		return cmnmtWorkPlace;
	}

}
