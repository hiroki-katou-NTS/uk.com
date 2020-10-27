package nts.uk.ctx.workflow.infra.repository.approvermanagement.jobtitlesearchset;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSet;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSetRepository;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtJobSearch;
import nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot.WwfmtJobSearchPK;

@Stateless
public class JpaJobtitleSearchSetRepository extends JpaRepository implements JobtitleSearchSetRepository {

	@Override
	public Optional<JobtitleSearchSet> finById(String cid, String jobtitleId) {
		WwfmtJobSearch entity = this.getEntityManager().find(WwfmtJobSearch.class,
				new WwfmtJobSearchPK(cid, jobtitleId));
		if (Objects.isNull(entity)) {
			return Optional.empty();
		}
		return Optional.of(this.toDomain(entity));
	}

	/**
	 * convert entity to domain
	 * @param entity
	 * @return
	 */
	private JobtitleSearchSet toDomain(WwfmtJobSearch entity) {
		return JobtitleSearchSet.createSimpleFromJavaType(entity.wwfmtJobSearchPK.companyId,
				entity.wwfmtJobSearchPK.jobId, entity.searchSetFlg);
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private WwfmtJobSearch toEntityJob(JobtitleSearchSet domain){
		val entity = new WwfmtJobSearch();
		entity.wwfmtJobSearchPK = new WwfmtJobSearchPK(domain.getCompanyId(), domain.getJobId());
		entity.searchSetFlg = domain.getSearchSetFlg().value;
		return entity;
	}
	
	/**
	 * update job title search set
	 * @author yennth
	 */
	@Override
	public void update(JobtitleSearchSet jobSearch) {
		WwfmtJobSearch entity = toEntityJob(jobSearch);
		WwfmtJobSearch oldEntity = this.queryProxy().find(entity.wwfmtJobSearchPK, WwfmtJobSearch.class).get();
		oldEntity.searchSetFlg = entity.searchSetFlg;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert job title search set
	 * @author yennth
	 */
	@Override
	public void insert(JobtitleSearchSet jobSearch) {
		WwfmtJobSearch entity = toEntityJob(jobSearch);
		this.commandProxy().insert(entity);
	}

	@Override
	public List<JobtitleSearchSet> findByListJob(String cid, List<String> jobtitleId) {
		List<JobtitleSearchSet> listJob = new ArrayList<>();
		for(String item: jobtitleId){
			WwfmtJobSearch entity = this.getEntityManager().find(WwfmtJobSearch.class,
					new WwfmtJobSearchPK(cid, item));
			if(entity != null){
				listJob.add(toDomain(entity));
			}
		}
		return listJob;
	}

}
