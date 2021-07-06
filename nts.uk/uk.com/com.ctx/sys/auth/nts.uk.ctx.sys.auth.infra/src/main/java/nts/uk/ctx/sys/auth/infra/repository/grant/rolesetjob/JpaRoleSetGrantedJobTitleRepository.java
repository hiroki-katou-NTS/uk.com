package nts.uk.ctx.sys.auth.infra.repository.grant.rolesetjob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitle;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetCode;
import nts.uk.ctx.sys.auth.infra.entity.grant.rolesetjob.SacmtRoleSetGrantedJobTitleDetail;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class JpaRoleSetGrantedJobTitleRepository extends JpaRepository implements RoleSetGrantedJobTitleRepository {

	private static final String FIND_BY_CID_JOBTITLES = "SELECT c FROM SacmtRoleSetGrantedJobTitleDetail c "
			+ " WHERE c.roleSetGrantedJobTitleDetailPK.companyId = :companyId"
			+ " AND c.roleSetCd IN :roleCDLst";
	
	@Override
	// TODO implement please
	public List<RoleSetGrantedJobTitle> getByCompanyId(String companyId) {
		return Collections.emptyList();
	}
	
	@Override
	// TODO implement please
	public Optional<RoleSetGrantedJobTitle> getByJobTitleId(String companyId, String jobTitleId) {
		return Optional.empty();
	}

	@Override
	// TODO implement please
	public void insert(RoleSetGrantedJobTitle domain) {
	}

	@Override
	// TODO implement please
	public void update(RoleSetGrantedJobTitle domain) {
	}

	@Override
	// TODO implement please
	public boolean checkRoleSetCdExist(String companyId, RoleSetCode roleSetCd) {
		return false;
	}

	@Override
	public List<String> findJobTitleByRoleCDLst(String companyID, List<String> roleCDLst){
		if(roleCDLst.isEmpty()){
			return new ArrayList<>();
		}
		List<String> resultList = new ArrayList<>();
		CollectionUtil.split(roleCDLst, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(FIND_BY_CID_JOBTITLES ,SacmtRoleSetGrantedJobTitleDetail.class )
				.setParameter("companyId", companyID)
				.setParameter("roleCDLst", subList)
				.getList( c -> c.roleSetGrantedJobTitleDetailPK.jobTitleId));
		});
		return resultList;
	}

}
