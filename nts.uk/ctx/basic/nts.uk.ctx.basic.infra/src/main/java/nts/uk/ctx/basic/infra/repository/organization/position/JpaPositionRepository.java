package nts.uk.ctx.basic.infra.repository.organization.position;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.AuthorizationLevel;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.JobRef_Auth;
import nts.uk.ctx.basic.dom.organization.position.JobTitle;
import nts.uk.ctx.basic.dom.organization.position.JobTitleRef;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.ctx.basic.infra.entity.organization.position.CatmtAuth;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobHist;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobHistPK;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitle;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitlePK;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitleRef;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitleRefPK;

@Stateless
public class JpaPositionRepository extends JpaRepository implements PositionRepository {

	private static final String SELECT_ALL_POSITION;
	private static final String SELECT_ALL_HISTORY;
	private static final String FIND_SINGLE_HISTORY;
	private static final String FIND_SINGLE;
	private static final String SELECT_ALL;
	private static final String IS_EXISTED_HISTORY_BY_DATE;
	private static final String IS_UPDATE_HISTORY;
	private static final String SELECT_HISTORY_BY_END_DATE;
	private static final String IS_EXISTED_HISTORY;
	private static final String SELECT_AUTHLEVEL;
	private static final String SELECT_JOBTITLEREF;
	private static final String SELECT_AUTHNAME;
	private static final String SELECT_HISTORY_BY_START_DATE;
	
	
	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitleRef e");
		builderString.append(" WHERE e.cmnmtJobTitleRefPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtJobTitleRefPK.historyId = :historyId");
		builderString.append(" AND e.cmnmtJobTitleRefPK.jobCode = :jobCode");
		SELECT_JOBTITLEREF = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CatmtAuth e");
		builderString.append(" WHERE e.catmtAuthPK.companyCode = :companyCode");
		builderString.append(" AND e.catmtAuthPK.authCode = :authCode");
		SELECT_AUTHLEVEL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a.catmtAuthPk.authCode, a.authName, coalesce(b.referenceSettings,1) referenceSettings");
		builderString.append(" FROM CatmtAuth a LEFT JOIN CmnmtJobTitleRef b");
		builderString.append(" ON a.catmtAuthPk.authCode = b.cmnmtJobTitleRefPK.authCode");
		builderString.append(" AND a.catmtAuthPk.companyCode = b.cmnmtJobTitleRefPK.companyCode");
		builderString.append(" AND a.catmtAuthPk.authScopeAtr = :authScopeAtr");
		builderString.append(" AND a.catmtAuthPk.companyCode = :companyCode");
		builderString.append(" AND b.cmnmtJobTitleRefPK.jobCode = :jobCode");
		builderString.append(" AND b.cmnmtJobTitleRefPK.historyId = :historyId");
		SELECT_AUTHNAME = builderString.toString();
		
		builderString = new StringBuilder();
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
		builderString.append(" ORDER BY e.endDate DESC");
		SELECT_ALL_HISTORY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTitle e");
		builderString.append(" WHERE e.cmnmtJobTitlePK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtJobTitlePK.historyId = :historyId");
		builderString.append(" AND e.cmnmtJobTitlePK.jobCode = :jobCode");
		FIND_SINGLE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobHist e");
		builderString.append(" WHERE e.cmnmtJobHistPK.companyCode = :companyCode");
		builderString.append(" AND e.cmnmtJobHistPK.historyId = :historyId");
		FIND_SINGLE_HISTORY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT count(e)");
		builderString.append(" FROM CmnmtJobHist e");
		builderString.append(" WHERE e.cmnmtJobHistPK.companyCode = :companyCode");
		builderString.append(" AND e.startDate >= :startDate");
		IS_EXISTED_HISTORY_BY_DATE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobHist e");
		builderString.append(" WHERE e.cmnmtJobHistPK.companyCode = :companyCode");
		builderString.append(" AND e.startDate < :startDate");
		builderString.append(" ORDER BY e.startDate DESC");
		SELECT_HISTORY_BY_START_DATE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT  count(e)");
		builderString.append(" FROM CmnmtJobHist e");
		builderString.append(" WHERE e.cmnmtJobHistPK.historyId = :historyId");
		builderString.append(" AND e.startDate < :startDate");
		builderString.append(" AND e.endDate > :startDate");
		IS_UPDATE_HISTORY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT TOP 1 e");
		builderString.append(" FROM CmnmtJobHist e");
		builderString.append(" WHERE e.cmnmtJobHistPK.companyCode = :companyCode");
		builderString.append(" AND e.endDate = :endDate");
		builderString.append(" ORDER BY e.endDate DESC");
		SELECT_HISTORY_BY_END_DATE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT count(e)");
		builderString.append(" FROM CmnmtJobHist e");
		builderString.append(" WHERE e.cmnmtJobHistPK.historyId = :historyId");
		IS_EXISTED_HISTORY = builderString.toString();

	}

	@Override
	public List<JobRef_Auth> getAllAuth(String companyCode, String historyId, String jobCode, String authScopeAtr) {
		return this.queryProxy().query(SELECT_AUTHNAME, Object[].class).setParameter("companyCode", companyCode)
				.setParameter("historyId", historyId).setParameter("jobCode", jobCode)
				.setParameter("authScopeAtr", authScopeAtr).getList(c -> {
					String authCode = (String) c[0];
					String authName = (String) c[1];
					int referenceSettings = Integer.valueOf(c[2].toString());
					return JobRef_Auth.createSimpleFromJavaType(authCode, authName, referenceSettings);
				});
	}
	
	
	private JobTitle convertToDomain(CmnmtJobTitle cmnmtJobTittle) {
		JobTitle jobTittle = JobTitle.createFromJavaType(cmnmtJobTittle.getCmnmtJobTitlePK().getCompanyCode(),
				cmnmtJobTittle.getCmnmtJobTitlePK().getHistoryId(), cmnmtJobTittle.getCmnmtJobTitlePK().getJobCode(),
				cmnmtJobTittle.getJobName(), cmnmtJobTittle.getPresenceCheckScopeSet(),
				cmnmtJobTittle.getJobOutCode() != null ? cmnmtJobTittle.getJobOutCode() : "",
				cmnmtJobTittle.getMemo() != null ? cmnmtJobTittle.getMemo() : "",
				cmnmtJobTittle.getHierarchyOrderCode() != null ? cmnmtJobTittle.getHierarchyOrderCode() : "");
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
		CmnmtJobTitlePK cmnmtJobTitlePK = new CmnmtJobTitlePK(position.getCompanyCode(), position.getJobCode().v(),
				position.getHistoryId());
		cmnmtJobTitle.setMemo(position.getMemo() != null ? position.getMemo().v() : "");
		cmnmtJobTitle.setJobName(position.getJobName().v());
		cmnmtJobTitle.setJobOutCode(position.getJobOutCode() != null ? position.getJobOutCode().v() : "");
		cmnmtJobTitle.setHierarchyOrderCode(
				position.getHiterarchyOrderCode() != null ? position.getHiterarchyOrderCode().v() : "");
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

	@Override
	public boolean ExistedHistoryBydate(String companyCode, GeneralDate startDate) {
		return this.queryProxy().query(IS_EXISTED_HISTORY_BY_DATE, long.class).setParameter("companyCode", companyCode)
				.setParameter("startDate", startDate).getSingle().get() > 0;

	}

	@Override
	public Optional<JobHistory> getHistoryByEdate(String companyCode, GeneralDate endDate) {

		return this.queryProxy().query(SELECT_HISTORY_BY_END_DATE, CmnmtJobHist.class)
				.setParameter("companyCode", companyCode).setParameter("endDate", endDate).getSingle().map(e -> {
					return Optional.of(convertToDomain2(e));
				}).orElse(Optional.empty());
	}

	@Override
	public boolean CheckUpdateHistory(String historyId, GeneralDate startDate) {

		return this.queryProxy().query(IS_UPDATE_HISTORY, long.class).setParameter("historyId", historyId)
				.setParameter("startDate", startDate).getSingle().get() > 0;
	}

	@Override
	public boolean ExistedHistory(String historyId) {
		return this.queryProxy().query(IS_EXISTED_HISTORY, long.class).setParameter("historyId", historyId).getSingle()
				.get() > 0;

	}

	private JobTitleRef convertToDomainRef(CmnmtJobTitleRef cmnmtJobTitleRef) {
		JobTitleRef jobTitleRef = JobTitleRef.createFromJavaType(cmnmtJobTitleRef.getReferenceSettings(),
				cmnmtJobTitleRef.getCmnmtJobTitleRefPK().getCompanyCode(),
				cmnmtJobTitleRef.getCmnmtJobTitleRefPK().getHistoryId(),
				cmnmtJobTitleRef.getCmnmtJobTitleRefPK().getJobCode(),
				cmnmtJobTitleRef.getCmnmtJobTitleRefPK().getAuthCode()
				);
		return jobTitleRef;
	}

	@Override
	public List<JobTitleRef> findAllJobTitleRef(String companyCode, String historyId, String jobCode) {
		return this.queryProxy().query(SELECT_JOBTITLEREF, CmnmtJobTitleRef.class)
				.setParameter("companyCode", companyCode).setParameter("historyId", historyId)
				.setParameter("jobCode", jobCode).getList(c -> convertToDomainRef(c));
	}

	private static AuthorizationLevel toDomainAuth(CatmtAuth entity) {
		val domain = AuthorizationLevel.createFromJavaType(entity.catmtAuthPk.companyCode,
				entity.catmtAuthPk.authScopeAtr, entity.catmtAuthPk.authCode, entity.authName, entity.empScopeAtr,
				entity.inChargeAtr, entity.memo);
		return domain;
	}

	@Override
	public Optional<AuthorizationLevel> findAllAuth(String companyCode, String authCode, String authScopeAtr) {
		return this.queryProxy().query(SELECT_AUTHLEVEL, CatmtAuth.class).setParameter("companyCode", companyCode)
				.setParameter("authCode", authCode).getSingle(c -> toDomainAuth(c));
	}

	@Override
	public void addJobTitleRef(JobTitleRef jobTitleRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<JobTitle> getJobTitleByCode(String companyCode, String historyId, String jobCode) {
		return this.queryProxy().query(FIND_SINGLE, CmnmtJobTitle.class).setParameter("companyCode", companyCode)
				.setParameter("historyId", historyId).setParameter("jobCode", jobCode).getSingle().map(c -> {
					return Optional.of(convertToDomain(c));
				}).orElse(Optional.empty());
	}

	private CmnmtJobTitlePK toEntityTitlePk(JobTitle domain) {
		val entityPk = new CmnmtJobTitlePK();
		entityPk.companyCode = domain.getCompanyCode();
		entityPk.historyId = domain.getHistoryId();
		entityPk.jobCode = domain.getJobCode().toString();
		return entityPk;
	}

	@Override
	public void deleteJobTitleByHisId(String companyCode, String historyId) {
		List<JobTitle> lstAllJobByHisId = this.findAllPosition(companyCode, historyId);
		List<CmnmtJobTitlePK> detailEntitiesPk = lstAllJobByHisId.stream().map(detail -> {
			return this.toEntityTitlePk(detail);
		}).collect(Collectors.toList());
		this.commandProxy().removeAll(CmnmtJobTitle.class, detailEntitiesPk);

	}


	

}
