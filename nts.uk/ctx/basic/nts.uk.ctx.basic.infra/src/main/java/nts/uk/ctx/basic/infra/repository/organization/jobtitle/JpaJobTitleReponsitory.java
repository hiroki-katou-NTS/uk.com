package nts.uk.ctx.basic.infra.repository.organization.jobtitle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.jobtitle.HiterarchyOrderCode;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobCode;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobName;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobTitle;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobTitleRepository;
import nts.uk.ctx.basic.dom.organization.jobtitle.PresenceCheckScopeSet;
import nts.uk.ctx.basic.infra.entity.organization.jobtitle.CmnmtJobTitle;
import nts.uk.ctx.basic.infra.entity.organization.jobtitle.CmnmtJobTitlePK;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaJobTitleReponsitory extends JpaRepository implements JobTitleRepository {

	

	private static final String FIND_ALL;

	private static final String FIND_SINGLE;
	
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTittle e");
		builderString.append(" WHERE e.cmnmtJobTittlePK.companyCode =: companyCode");
		FIND_ALL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM CmnmtJobTittle e");
		builderString.append(" WHERE e.cmnmtJobTittlePK.companyCode =: companyCode");
		builderString.append(" AND e.cmnmtJobTittlePK.jobCode =: jobCode");
		builderString.append(" AND e.cmnmtJobTittlePK.historyID =: historyID");
		FIND_SINGLE = builderString.toString();
	}
	
	
	@Override
	public Optional<JobTitle> findSingle(String companyCode,
			String historyID,JobCode jobCode ) {
		return this.queryProxy().query(FIND_SINGLE, CmnmtJobTitle.class)
				.setParameter("companyCode", "'" + companyCode + "'")
				.setParameter("historyID", "'" + historyID + "'")
				.setParameter("jobCode", "'" + jobCode.toString() + "'").getSingle().map(e -> {
					return Optional.of(convertToDomain(e));
				}).orElse(Optional.empty());
	}
	

	private JobTitle convertToDomain(CmnmtJobTitle cmnmtJobTittle) {
		return new JobTitle(
				new JobName(cmnmtJobTittle.getJobName()),
				GeneralDate.localDate(cmnmtJobTittle.getEndDate()),
				new JobCode(cmnmtJobTittle.getCmnmtJobTittlePK().getJobCode()),
				new JobCode(cmnmtJobTittle.getJobOutCode()),
				GeneralDate.localDate(cmnmtJobTittle.getStartDate()),
				cmnmtJobTittle.getCmnmtJobTittlePK().getHistoryID(),
				cmnmtJobTittle.getCmnmtJobTittlePK().getCompanyCode(),
				new Memo(cmnmtJobTittle.getMemo()),
				new HiterarchyOrderCode(cmnmtJobTittle.getHiterarchyOrderCode()),
				PresenceCheckScopeSet.valueOf(Integer.toString(cmnmtJobTittle.getPresenceCheckScopeSet())));
	
	}
	
	
	private CmnmtJobTitle convertToDbType(JobTitle position) {
		CmnmtJobTitle cmnmtJobTittle = new CmnmtJobTitle();
		CmnmtJobTitlePK cmnmtJobTittlePK = new CmnmtJobTitlePK(
				position.getJobCode().v(),
				position.getHistoryID(),
				position.getCompanyCode());
		cmnmtJobTittle.setMemo(position.getMemo().toString());
		cmnmtJobTittle.setEndDate(position.getEndDate().localDate());
		cmnmtJobTittle.setStartDate(position.getStartDate().localDate());
		cmnmtJobTittle.setJobName(position.getJobName().v());
		cmnmtJobTittle.setJobOutCode(position.getJobOutCode().v());
		cmnmtJobTittle.setHiterarchyOrderCode(position.getHiterarchyOrderCode().v());
		cmnmtJobTittle.setPresenceCheckScopeSet(position.getPresenceCheckScopeSet().value);
		cmnmtJobTittle.setCmnmtJobTittlePK(cmnmtJobTittlePK);
		return cmnmtJobTittle;
	}
	
	

	private final JobTitle toDomain(CmnmtJobTitle entity) {
		val domain = JobTitle.createFromJavaType(entity.jobName,GeneralDate.localDate(entity.endDate),  entity.cmnmtJobTittlePK.jobCode,entity.jobOutCode ,GeneralDate.localDate(entity.startDate), entity.cmnmtJobTittlePK.historyID, entity.cmnmtJobTittlePK.companyCode, entity.memo,entity.hiterarchyOrderCode,entity.presenceCheckScopeSet);

		return domain;
	}
	
	
	
	private CmnmtJobTitle toEntityPK(JobTitle domain) {
		val entity = new CmnmtJobTitle();

		entity.cmnmtJobTittlePK = new CmnmtJobTitlePK();
		entity.cmnmtJobTittlePK.companyCode = domain.getCompanyCode();
		entity.cmnmtJobTittlePK.jobCode = domain.getJobCode().v();
		entity.cmnmtJobTittlePK.historyID = domain.getHistoryID();
		
		return entity;
	}

//	private CmnmtJobTittle toEntity(Position domain) {
//		val entity = new CmnmtJobTittle();
//
//		entity.cmnmtJobTittlePK = new CmnmtJobTittlePK();
//		entity.cmnmtJobTittlePK.companyCode = domain.getCompanyCode();
//		entity.cmnmtJobTittlePK.jobCode = domain.getJobCode().v();
//		entity.cmnmtJobTittlePK.historyID = domain.getHistoryID();
//		entity.endDate = domain.getEndDate().localDate();
//		entity.startDate = domain.getStartDate().localDate();
//
//		
//		return entity;
//	}


	@Override
	public void remove(String companyCode) {
		val objectKey = new CmnmtJobTitlePK();
		objectKey.companyCode = companyCode;

		this.commandProxy().remove(CmnmtJobTitle.class, objectKey);
	}
	
	@Override
	public void add(JobTitle position) {
		this.commandProxy().insert(convertToDbType(position));

	}

	@Override
	public void update(JobTitle position) {
		this.commandProxy().update(convertToDbType(position));

	}


	@Override
	public void remove(List<JobTitle> details) {
		List<CmnmtJobTitle> cmnmtJobTittlePKs = details.stream().map(
					detail -> {return this.toEntityPK(detail);}
				).collect(Collectors.toList());
		this.commandProxy().removeAll(CmnmtJobTitlePK.class, cmnmtJobTittlePKs);
	}

	@Override
	public List<JobTitle> getPositions(String companyCode) {
		return this.queryProxy().query(FIND_ALL, CmnmtJobTitle.class)
				.setParameter("companyCd", companyCode)

				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<JobTitle> getPosition(String companyCode, String jobCode, String historyID) {
		try {
			return this.queryProxy().find(new CmnmtJobTitlePK(companyCode, jobCode ,historyID), CmnmtJobTitle.class)
					.map(c -> toDomain(c));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean isExist(String companyCode, LocalDate startDate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<JobTitle> findAll(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExisted(String companyCode, JobCode jobCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void remove(String companyCode, JobCode jobCode) {
		// TODO Auto-generated method stub
		
	}

}
