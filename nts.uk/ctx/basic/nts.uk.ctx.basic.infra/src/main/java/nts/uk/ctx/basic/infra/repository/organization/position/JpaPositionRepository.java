package nts.uk.ctx.basic.infra.repository.organization.position;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.JobTitle;
import nts.uk.ctx.basic.dom.organization.position.JobTitleRef;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobHist;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobHistPK;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitle;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitlePK;

@Stateless
public class JpaPositionRepository extends JpaRepository implements PositionRepository {

	private static final String SELECT_ALL_POSITION;
	private static final String SELECT_ALL_HISTORY;
	private static final String FIND_SINGLE_HISTORY;
	private static final String FIND_SINGLE;
	private static final String SELECT_ALL;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitle e");
		builderString.append(" WHERE e.cmnmtJobTitlePK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtJobTitlePK.historyId = :historyId");
		SELECT_ALL_POSITION = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitle e");
		builderString.append(" WHERE e.cmnmtJobTitlePK.companyCode = :companyCode");
		SELECT_ALL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobHist e");
		builderString.append(" WHERE e.cmnmtJobHistPK.companyCode = :companyCode");
		SELECT_ALL_HISTORY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitle e");
		builderString.append(" WHERE e.cmnmtJobTitlePK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtJobTitlePK.historyID = :historyID");
		builderString.append(" AND e.cmnmtJobTitlePK.jobCode = :jobCode");
		FIND_SINGLE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobHist e");
		builderString.append(" WHERE e.cmnmtJobTitlePK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtJobTitlePK.historyID = :historyID");
		FIND_SINGLE_HISTORY = builderString.toString();

	}

	private JobTitle convertToDomain(CmnmtJobTitle cmnmtJobTittle) {
		JobTitle jobTittle = JobTitle.createFromJavaType(cmnmtJobTittle.getJobName(),
				cmnmtJobTittle.getCmnmtJobTitlePK().getJobCode(), cmnmtJobTittle.getJobOutCode() != null ? cmnmtJobTittle.getJobOutCode() : "",
				cmnmtJobTittle.getCmnmtJobTitlePK().getHistoryId(),
				cmnmtJobTittle.getCmnmtJobTitlePK().getCompanyCode(), cmnmtJobTittle.getMemo() != null ? cmnmtJobTittle.getMemo() : "",
				cmnmtJobTittle.getHierarchyOrderCode() != null ? cmnmtJobTittle.getHierarchyOrderCode() : "", cmnmtJobTittle.getPresenceCheckScopeSet());
		return jobTittle;

	}

	private JobHistory convertToDomain2(CmnmtJobHist cmnmtJobTitleHistory) {
		JobHistory jobHistory = JobHistory.createFromJavaType(cmnmtJobTitleHistory.getCmnmtJobHistPK().getCompanyCode(),
				cmnmtJobTitleHistory.getCmnmtJobHistPK().getHistoryId(), cmnmtJobTitleHistory.getEndDate(),
				cmnmtJobTitleHistory.getStartDate());
		return jobHistory;
	}

	private CmnmtJobTitle convertToDbType(JobTitle position) {
		CmnmtJobTitle cmnmtJobTitle = new CmnmtJobTitle();
		CmnmtJobTitlePK cmnmtJobTitlePK = new CmnmtJobTitlePK(position.getCompanyCode(), position.getJobCode().v(), position.getHistoryId());
		cmnmtJobTitle.setMemo(position.getMemo() != null ? position.getMemo().v() : "");
		cmnmtJobTitle.setJobName(position.getJobName().v());
		cmnmtJobTitle.setJobOutCode(position.getJobOutCode()!= null ? position.getJobOutCode().v() : "");
		cmnmtJobTitle.setHierarchyOrderCode(position.getHiterarchyOrderCode()!= null ? position.getHiterarchyOrderCode().v() : "");
		cmnmtJobTitle.setPresenceCheckScopeSet(0);
		cmnmtJobTitle.setCmnmtJobTitlePK(cmnmtJobTitlePK);
		return cmnmtJobTitle;
	}

	private CmnmtJobHist convertToDbType2(JobHistory positionHistory) {
		CmnmtJobHist cmnmtJobTitleHistory = new CmnmtJobHist();
		CmnmtJobHistPK cmnmtJobTitleHistoryPK = new CmnmtJobHistPK(positionHistory.getCompanyCode(),
				positionHistory.getHistoryId());
		cmnmtJobTitleHistory.setEndDate(positionHistory.getEndDate());
		cmnmtJobTitleHistory.setStartDate(positionHistory.getStartDate());
		cmnmtJobTitleHistory.setCmnmtJobHistPK(cmnmtJobTitleHistoryPK);
		return cmnmtJobTitleHistory;
	}

	// private static JobTitle toDomain(CmnmtJobTitle entity) {
	// val domain = JobTitle.createFromJavaType(
	// entity.cmnmtJobTitlePK.companyCode,
	// entity.cmnmtJobTitlePK.historyId,
	// entity.cmnmtJobTitlePK.jobCode,
	// entity.jobName,
	// entity.presenceCheckScopeSet,
	// entity.jobOutCode,
	// entity.memo);
	// return domain;
	// }
	//
	// private static JobHistory toDomain2(CmnmtJobHist entity) {
	// val domain = JobHistory.createFromJavaType(
	// entity.startDate,
	// entity.endDate,
	// entity.cmnmtJobHistPK.companyCode,
	// entity.cmnmtJobHistPK.historyId
	// );
	// return domain;
	// }

	// private static CmnmtJobTitle toEntity(JobTitle domain) {
	//
	// val entity = new CmnmtJobTitle();
	// entity.cmnmtJobTitlePK = new CmnmtJobTitlePK();
	// entity.jobName = domain.getJobName().v();
	// entity.presenceCheckScopeSet = domain.getPresenceCheckScopeSet().value;
	// entity.jobOutCode = domain.getJobOutCode().v();
	// entity.memo = domain.getMemo().v();
	//
	// return entity;
	//
	// }

	@Override
	public void add(JobTitle position) {
		this.commandProxy().insert(convertToDbType(position));
	}

	@Override
	public void update(JobTitle position) {
		this.commandProxy().update(convertToDbType(position));
	}

	// xoa position lua chon
	@Override
	public void delete(String companyCode, JobCode jobCode, String historyId) {
		CmnmtJobTitlePK cmnmtClassPK = new CmnmtJobTitlePK(companyCode, jobCode.toString(), historyId);
		this.commandProxy().remove(CmnmtJobTitle.class, cmnmtClassPK);
	}

	@Override
	public List<JobTitle> findAllPosition(String companyCode, String historyId) {

		List<JobTitle> lstJobTitle = this.queryProxy().query(SELECT_ALL_POSITION, CmnmtJobTitle.class)
				.setParameter("companyCode", companyCode).setParameter("historyId", historyId)
				.getList(c -> convertToDomain(c));
		System.out.println(lstJobTitle);
		return lstJobTitle;
	}

	@Override
	public List<JobTitle> findAll(String companyCode) {

		List<JobTitle> lstJobTitle = this.queryProxy().query(SELECT_ALL, CmnmtJobTitle.class)
				.setParameter("companyCode", companyCode)

				.getList(c -> convertToDomain(c));
		return lstJobTitle;
	}

	@Override
	public List<JobHistory> getAllHistory(String companyCode) {

		return this.queryProxy().query(SELECT_ALL_HISTORY, CmnmtJobHist.class).setParameter("companyCode", companyCode)
				.getList(c -> convertToDomain2(c));
	}

	@Override
	public List<JobTitleRef> findAllJobTitleRef(String companyCode, String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteHist(String companyCode, String historyId) {
		CmnmtJobHistPK cmnmtJobTitleHistoryPK = new CmnmtJobHistPK(companyCode, historyId);
		this.commandProxy().remove(CmnmtJobHist.class, cmnmtJobTitleHistoryPK);

	}

	@Override
	public void addHistory(JobHistory history) {
		this.commandProxy().insert(convertToDbType2(history));

	}

	@Override
	public void updateHistory(JobHistory history) {
		this.commandProxy().update(convertToDbType2(history));

	}

	@Override
	public Optional<JobTitle> findSingle(String companyCode, String historyId, JobCode jobCode) {
		return this.queryProxy().query(FIND_SINGLE, CmnmtJobTitle.class)
				.setParameter("companyCode", "'" + companyCode + "'").setParameter("historyId", "'" + historyId + "'")
				.setParameter("jobCode", "'" + jobCode.toString() + "'").getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}

	@Override
	public Optional<JobHistory> findSingleHistory(String companyCode, String historyId) {
		return this.queryProxy().query(FIND_SINGLE_HISTORY, CmnmtJobHist.class)
				.setParameter("companyCode", "'" + companyCode + "'").setParameter("historyId", "'" + historyId + "'")
				.getSingle().map(e -> {
					return Optional.of(convertToDomain2(e));
				}).orElse(Optional.empty());
	}

	// @Override
	// public Optional<Position> find(String companyCode, String jobCode, String
	// historyId) {
	// return this.queryProxy().query(SELECT_DETAIL, CmnmtJobTitle.class)
	// .setParameter("companyCode", companyCode)
	// .setParameter("jobCode", jobCode)
	// .setParameter("historyId", historyId)
	// .getSingle().map(e -> {
	// return Optional.of(toDomain(e));
	// }).orElse(Optional.empty());
	// }

}
