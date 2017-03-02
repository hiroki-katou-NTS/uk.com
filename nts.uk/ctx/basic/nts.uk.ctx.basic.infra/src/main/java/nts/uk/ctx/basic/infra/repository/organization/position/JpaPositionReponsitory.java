package nts.uk.ctx.basic.infra.repository.organization.position;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.HiterarchyOrderCode;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.JobName;
import nts.uk.ctx.basic.dom.organization.position.Position;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.ctx.basic.dom.organization.position.PresenceCheckScopeSet;
import nts.uk.ctx.basic.dom.organization.positionhistory.PositionHistory;
import nts.uk.ctx.basic.dom.organization.positionreference.PositionReference;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitle;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitlePK;
import nts.uk.ctx.basic.infra.entity.organization.positionhistory.CmnmtJobTitleHistory;
import nts.uk.ctx.basic.infra.entity.organization.positionhistory.CmnmtJobTitleHistoryPK;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaPositionReponsitory extends JpaRepository implements PositionRepository {

	

	private static final String FIND_ALL_POSITION;

	private static final String FIND_SINGLE;
	
	private static final String CHECK_EXIST;
	
	private static final String FIND_ALL_HISTORY;

	private static final String FIND_SINGLE_HISTORY;

	
	 
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTittle e");
		builderString.append(" WHERE e.cmnmtJobTittlePK.companyCode = :companyCode");
		FIND_ALL_POSITION = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTittle e");
		builderString.append(" WHERE e.cmnmtJobTittlePK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtJobTittlePK.historyID = :historyID");
		builderString.append(" AND e.cmnmtJobTittlePK.jobCode = :jobCode");
		FIND_SINGLE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTittle e");
		builderString.append(" WHERE e.cmnmtJobTittlePK.companyCode = :companyCode");
		CHECK_EXIST = builderString.toString();
		
		builderString = new StringBuilder();
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
		
	
		
	}
	
	
	@Override
	public Optional<Position> findSingle(String companyCode,
			String historyID,JobCode jobCode ) {
		return this.queryProxy().query(FIND_SINGLE, CmnmtJobTitle.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("historyID", "'" + historyID + "'")
				.setParameter("jobCode", "'" + jobCode.toString() + "'").getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}
	
	@Override
	public Optional<PositionHistory> findSingleHistory(String companyCode,
			String historyID ) {
		return this.queryProxy().query(FIND_SINGLE_HISTORY, CmnmtJobTitleHistory.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("historyID", "'" + historyID + "'").getSingle().map(e -> {
					return Optional.of(convertToDomain2(e));
				}).orElse(Optional.empty());
	}
	

	private Position convertToDomain(CmnmtJobTitle cmnmtJobTittle) {
		return new Position(
				new JobName(cmnmtJobTittle.getJobName()),
				new JobCode(cmnmtJobTittle.getCmnmtJobTitlePK().getJobCode()),
				new JobCode(cmnmtJobTittle.getJobOutCode()),
				cmnmtJobTittle.getCmnmtJobTitlePK().getHistoryID(),
				cmnmtJobTittle.getCmnmtJobTitlePK().getCompanyCode(),
				new Memo(cmnmtJobTittle.getMemo()),
				new HiterarchyOrderCode(cmnmtJobTittle.getHiterarchyOrderCode()),
				PresenceCheckScopeSet.valueOf(Integer.toString(cmnmtJobTittle.getPresenceCheckScopeSet())));
	
	}
	
	private PositionHistory convertToDomain2(CmnmtJobTitleHistory cmnmtJobTitleHistory) {
		return new PositionHistory(
				cmnmtJobTitleHistory.getCmnmtJobTitleHistoryPK().getCompanyCode(),
				cmnmtJobTitleHistory.getCmnmtJobTitleHistoryPK().getHistoryID(),
				cmnmtJobTitleHistory.getEndDate(),
				cmnmtJobTitleHistory.getStartDate());
	
	}
	
	
	private CmnmtJobTitle convertToDbType(Position position) {
		CmnmtJobTitle cmnmtJobTittle = new CmnmtJobTitle();
		CmnmtJobTitlePK cmnmtJobTittlePK = new CmnmtJobTitlePK(
				position.getJobCode().v(),
				position.getHistoryID(),
				position.getCompanyCode());
		cmnmtJobTittle.setMemo(position.getMemo().toString());
		cmnmtJobTittle.setJobName(position.getJobName().v());
		cmnmtJobTittle.setJobOutCode(position.getJobOutCode().v());
		cmnmtJobTittle.setHiterarchyOrderCode(position.getHiterarchyOrderCode().v());
		cmnmtJobTittle.setPresenceCheckScopeSet(position.getPresenceCheckScopeSet().value);
		cmnmtJobTittle.setCmnmtJobTittlePK(cmnmtJobTittlePK);
		return cmnmtJobTittle;
	}
	
	
	private CmnmtJobTitleHistory convertToDbType2(PositionHistory positionHistory) {
		CmnmtJobTitleHistory cmnmtJobTitleHistory = new CmnmtJobTitleHistory();
		CmnmtJobTitleHistoryPK cmnmtJobTitleHistoryPK = new CmnmtJobTitleHistoryPK(
				positionHistory.getCompanyCode(),
				positionHistory.getHistoryID());
				cmnmtJobTitleHistory.setEndDate(positionHistory.getEndDate());
				cmnmtJobTitleHistory.setStartDate(positionHistory.getStartDate());
				cmnmtJobTitleHistory.setCmnmtJobTitleHistoryPK(cmnmtJobTitleHistoryPK);
		return cmnmtJobTitleHistory;
	}
	

	
	
	
	private CmnmtJobTitle toEntityPK(Position domain) {
		val entity = new CmnmtJobTitle();

		entity.cmnmtJobTitlePK = new CmnmtJobTitlePK();
		entity.cmnmtJobTitlePK.companyCode = domain.getCompanyCode();
		entity.cmnmtJobTitlePK.jobCode = domain.getJobCode().v();
		entity.cmnmtJobTitlePK.historyID = domain.getHistoryID();
		
		return entity;
	}




//	@Override
//	public void remove(String companyCode,String historyID) {
//		val objectKey = new CmnmtJobTitlePK();
//		objectKey.companyCode = companyCode;
//		objectKey.historyID = historyID;
//
//		this.commandProxy().remove(CmnmtJobTitle.class, objectKey);
//	}
	
	@Override
	public void add(Position position) {
		this.commandProxy().insert(convertToDbType(position));

	}

	@Override
	public void update(Position position) {
		this.commandProxy().update(convertToDbType(position));

	}


	@Override
	public List<Position> findAllPosition(String companyCode,String historyID) {
		return this.queryProxy().query(FIND_ALL_POSITION, CmnmtJobTitle.class)
		.setParameter("companyCd", companyCode)
		.setParameter("historyID", historyID)

		.getList(c -> convertToDomain(c));
}


	@Override
	public void delete(String companyCode, JobCode jobCode, String historyID) {
		CmnmtJobTitlePK cmnmtJobTitlePK = new CmnmtJobTitlePK(companyCode, jobCode.toString(),historyID);
		this.commandProxy().remove(CmnmtJobTitle.class, cmnmtJobTitlePK);
		
	}


	@Override
	public void addHistory(PositionHistory history) {
		this.commandProxy().insert(convertToDbType2(history));
		
	}


	@Override
	public void updateHistory(PositionHistory history) {
		this.commandProxy().update(convertToDbType2(history));		
	}


	@Override
	public void deleteHist(String companyCode, String historyID) {
		CmnmtJobTitleHistoryPK cmnmtJobTitleHistoryPK = new CmnmtJobTitleHistoryPK(companyCode,historyID);
		this.commandProxy().remove(CmnmtJobTitleHistory.class, cmnmtJobTitleHistoryPK);
		
	}


	@Override
	public List<PositionHistory> getAllHistory(String companyCode) {
		List<CmnmtJobTitleHistory> resultList = this.queryProxy().query(FIND_ALL_HISTORY, CmnmtJobTitleHistory.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.getList();
		return !resultList.isEmpty() ? resultList.stream().map(item -> {
			return convertToDomain2(item);
		}).collect(Collectors.toList()) : new ArrayList<>();
	}


	@Override
	public List<PositionReference> findAllJobTitleRef(String companyCode, String historyID) {
		// TODO Auto-generated method stub
		return null;
	}


//	@Override
//	public void removes(List<Position> details) {
//		List<CmnmtJobTitle> cmnmtJobTittlePKs = details.stream().map(
//					detail -> {return this.toEntityPK(detail);}
//				).collect(Collectors.toList());
//		this.commandProxy().removeAll(CmnmtJobTitlePK.class, cmnmtJobTittlePKs);
//	}

//	
//	@Override
//	public List<Position> getPositions(String companyCode,String historyID) {
//		return this.queryProxy().query(FIND_ALL_POSITION, CmnmtJobTitle.class)
//				.setParameter("companyCd", companyCode)
//				.setParameter("historyID", historyID)
//
//				.getList(c -> convertToDomain(c));
//	}
//
//	@Override
//	public Optional<Position> getPosition(String companyCode, String jobCode, String historyID) {
//		try {
//			return this.queryProxy().find(new CmnmtJobTitlePK(companyCode, jobCode ,historyID), CmnmtJobTitle.class)
//					.map(c -> convertToDomain(c));
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	@Override
//	public boolean isExist(String companyCode, LocalDate startDate) {
//		return this.queryProxy().query(CHECK_EXIST, long.class).setParameter("companyCode", "'" + companyCode + "'")
//				.getSingle().get() > 0;
//	}
//
//
//
	@Override
	public boolean isExisted(String companyCode, JobCode jobCode,String historyID) {
		return this.queryProxy().query(CHECK_EXIST, long.class).setParameter("companyCode", "'" + companyCode + "'")
			.getSingle().get() > 0;
	}
//
//
//	@Override
//	public void remove(String companyCode, JobCode jobCode) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public boolean isExist(String companyCode, GeneralDate startDate) {
		return this.queryProxy().query(CHECK_EXIST, long.class).setParameter("companyCode", "'" + companyCode + "'")
				.getSingle().get() > 0;
	}

	

}
