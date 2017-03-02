package nts.uk.ctx.basic.infra.repository.organization.positionhistory;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.department.Department;
import nts.uk.ctx.basic.dom.organization.department.DepartmentCode;
import nts.uk.ctx.basic.dom.organization.position.Position;
import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistory;
import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistoryRepository;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDep;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDepPK;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitle;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitlePK;
import nts.uk.ctx.basic.infra.entity.organization.positionhistory.CmnmtJobTitleHistory;
import nts.uk.ctx.basic.infra.entity.organization.positionhistory.CmnmtJobTitleHistoryPK;


@Stateless
public class JpaPositionHistoryReponsitory extends JpaRepository implements PositionHistoryRepository {

	
	private static final String FIND_ALL_HISTORY;

	private static final String FIND_SINGLE_HISTORY;
	
	private static final String CHECK_EXIST;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitleHistory e");
		builderString.append(" WHERE e.cmnmtJobTitleHistoryPK.companyCode = :companyCode");
		FIND_ALL_HISTORY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitleHistory e");
		builderString.append(" WHERE e.cmnmtJobTitleHistoryPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtJobTitleHistoryPK.historyID = :historyID");
		FIND_SINGLE_HISTORY = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitleHistory e");
		builderString.append(" WHERE e.cmnmtJobTitleHistoryPK.companyCode = :companyCode");
		CHECK_EXIST = builderString.toString();
		
	
		
	}
	
	@Override
	public Optional<PositionHistory> findSingleHistory(String companyCode,
			String historyID ) {
		return this.queryProxy().query(FIND_SINGLE_HISTORY, CmnmtJobTitleHistory.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("historyID", "'" + historyID + "'").getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public List<PositionHistory> findAllHistory(String companyCode) {
		List<CmnmtJobTitleHistory> resultList = this.queryProxy().query(FIND_ALL_HISTORY, CmnmtJobTitleHistory.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.getList();
		return !resultList.isEmpty() ? resultList.stream().map(item -> {
			return convertToDomain(item);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}

		
	private PositionHistory convertToDomain(CmnmtJobTitleHistory cmnmtJobTitleHistory) {
		return new PositionHistory(
				cmnmtJobTitleHistory.getCmnmtJobTitleHistoryPK().getCompanyCode(),
				cmnmtJobTitleHistory.getCmnmtJobTitleHistoryPK().getHistoryID(),
				cmnmtJobTitleHistory.getEndDate(),
				cmnmtJobTitleHistory.getStartDate());
	
	}
		
	private CmnmtJobTitleHistory convertToDbType(PositionHistory positionHistory) {
		CmnmtJobTitleHistory cmnmtJobTitleHistory = new CmnmtJobTitleHistory();
		CmnmtJobTitleHistoryPK cmnmtJobTitleHistoryPK = new CmnmtJobTitleHistoryPK(
				positionHistory.getCompanyCode(),
				positionHistory.getHistoryID());
				cmnmtJobTitleHistory.setEndDate(positionHistory.getEndDate());
				cmnmtJobTitleHistory.setStartDate(positionHistory.getStartDate());
				cmnmtJobTitleHistory.setCmnmtJobTitleHistoryPK(cmnmtJobTitleHistoryPK);
		return cmnmtJobTitleHistory;
	}

	@Override
	public void add(PositionHistory positionHistory) {
		this.commandProxy().insert(convertToDbType(positionHistory));

	}

	@Override
	public void update(PositionHistory positionHistory) {
		this.commandProxy().update(convertToDbType(positionHistory));

	}

	@Override
	public void remove(String companyCode, String historyId) {
		this.commandProxy().remove(CmnmtJobTitleHistory.class, new CmnmtJobTitleHistoryPK(companyCode, historyId));

	}

	
	@Override
	public boolean isExist(String companyCode, String historyID) {
		return this.queryProxy().query(CHECK_EXIST, long.class).setParameter("companyCode", "'" + companyCode + "'")
				.getSingle().get() > 0;
	}





}