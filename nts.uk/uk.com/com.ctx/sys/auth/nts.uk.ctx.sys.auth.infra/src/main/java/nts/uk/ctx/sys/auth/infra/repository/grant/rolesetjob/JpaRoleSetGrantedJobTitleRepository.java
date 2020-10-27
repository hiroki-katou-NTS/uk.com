package nts.uk.ctx.sys.auth.infra.repository.grant.rolesetjob;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitle;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.infra.entity.grant.rolesetjob.SacmtRolesetGrantedJobTitle;
import nts.uk.ctx.sys.auth.infra.entity.grant.rolesetjob.SacmtRolesetGrantedJobTitleDetail;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class JpaRoleSetGrantedJobTitleRepository extends JpaRepository implements RoleSetGrantedJobTitleRepository {

	private static final String GET_All_BY_COMPANY_ID = "SELECT rs FROM SacmtRolesetGrantedJobTitle rs WHERE rs.companyId = :companyId ";

	private static final String SELECT_BY_JOBTITLECD = "SELECT c FROM SacmtRolesetGrantedJobTitleDetail c "
			+ " WHERE c.roleSetGrantedJobTitleDetailPK.companyId = :companyId"
			+ " AND c.roleSetGrantedJobTitleDetailPK.jobTitleId = :jobTitleId";
	
	private static final String FIND_BY_CID_JOBTITLES = "SELECT c FROM SacmtRolesetGrantedJobTitleDetail c "
			+ " WHERE c.roleSetGrantedJobTitleDetailPK.companyId = :companyId"
			+ " AND c.roleSetCd IN :roleCDLst";
	
	@Override
	public List<RoleSetGrantedJobTitle> getAllByCompanyId(String companyId) {
		return this.queryProxy().query(GET_All_BY_COMPANY_ID, SacmtRolesetGrantedJobTitle.class)
				.setParameter("companyId", companyId).getList(d -> SacmtRolesetGrantedJobTitle.toDomain(d));
	}

	@Override
	public Optional<RoleSetGrantedJobTitle> getOneByCompanyId(String companyId) {
		SacmtRolesetGrantedJobTitle entity = this.queryProxy()
				.query(GET_All_BY_COMPANY_ID, SacmtRolesetGrantedJobTitle.class).setParameter("companyId", companyId)
				.getSingleOrNull();
		if (entity == null) {
			return Optional.empty();
		} else {
			return Optional.of(SacmtRolesetGrantedJobTitle.toDomain(entity));
		}
	}

	@Override
	public void insert(RoleSetGrantedJobTitle domain) {
		this.commandProxy().insert(SacmtRolesetGrantedJobTitle.toEntity(domain));
	}

	@Override
	public void update(RoleSetGrantedJobTitle domain) {
		SacmtRolesetGrantedJobTitle entity = this.queryProxy()
				.find(domain.getCompanyId(), SacmtRolesetGrantedJobTitle.class).get();
		entity.applyToConcurrentPerson = domain.isApplyToConcurrentPerson();
		List<SacmtRolesetGrantedJobTitleDetail> oldDetails = entity.details;
		List<SacmtRolesetGrantedJobTitleDetail> newDetails = domain.getDetails().stream()
				.map(item -> new SacmtRolesetGrantedJobTitleDetail(item.getRoleSetCd().v(), item.getJobTitleId(),
						item.getCompanyId()))
				.collect(Collectors.toList());
		
		for (SacmtRolesetGrantedJobTitleDetail newDetail : newDetails){
			for (SacmtRolesetGrantedJobTitleDetail oldDetail : oldDetails){
				if (oldDetail.roleSetGrantedJobTitleDetailPK.equals(newDetail.roleSetGrantedJobTitleDetailPK)){
					newDetails.set(newDetails.indexOf(newDetail), newDetail);
					break;
				}
			}
		} 
		
		entity.details = newDetails;
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(String companyId) {
		this.commandProxy().remove(SacmtRolesetGrantedJobTitle.class, companyId);

	}

	@Override
	public boolean checkRoleSetCdExist(String roleSetCd, String companyId) {
		SacmtRolesetGrantedJobTitle entity = this.queryProxy()
				.query(GET_All_BY_COMPANY_ID, SacmtRolesetGrantedJobTitle.class).setParameter("companyId", companyId)
				.getSingleOrNull();
		if (entity == null) {
			return false;
		} else {
			return SacmtRolesetGrantedJobTitle.toDomain(entity).isRoleSetCdExist(roleSetCd);
		}
	}

	@Override
	public Optional<String> getRoleSetCd(String companyId, String jobTitleId) {
		return this.queryProxy().query(SELECT_BY_JOBTITLECD ,SacmtRolesetGrantedJobTitleDetail.class )
				.setParameter("companyId", companyId)
				.setParameter("jobTitleId", jobTitleId)
				.getSingle( c -> c.roleSetCd);
		
	}

	@Override
	public List<String> findJobTitleByRoleCDLst(String companyID, List<String> roleCDLst){
		if(roleCDLst.isEmpty()){
			return new ArrayList<>();
		}
		List<String> resultList = new ArrayList<>();
		CollectionUtil.split(roleCDLst, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(FIND_BY_CID_JOBTITLES ,SacmtRolesetGrantedJobTitleDetail.class )
				.setParameter("companyId", companyID)
				.setParameter("roleCDLst", subList)
				.getList( c -> c.roleSetGrantedJobTitleDetailPK.jobTitleId));
		});
		return resultList;
	}

}
