package nts.uk.ctx.sys.auth.infra.repository.grant;

import java.util.List;

import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedJobTitle;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedJobTitleDetailRepository;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.infra.entity.grant.SaumtRoleSetGrantedJobTitle;
import nts.uk.ctx.sys.auth.infra.entity.grant.SaumtRoleSetGrantedJobTitleDetail;

/**
 * 
 * @author HungTT
 *
 */

public class JpaRoleSetGrantedJobTitleRepository extends JpaRepository implements RoleSetGrantedJobTitleRepository {

	@Inject
	private RoleSetGrantedJobTitleDetailRepository detailRepo;
	
	private final String GET_All_BY_COMPANY_ID = "SELECT d FROM SaumtRoleSetGrantedJobTitle rs WHERE rs.companyId = :companyId ";
	
	private RoleSetGrantedJobTitle toDomain(SaumtRoleSetGrantedJobTitle entity){
		RoleSetGrantedJobTitle domain = new RoleSetGrantedJobTitle(entity.companyId, entity.applyToConcurrentPerson);
		domain.setDetails(detailRepo.getAllByCompany(entity.companyId));
		return domain;
	}
	
	private SaumtRoleSetGrantedJobTitle toEntity(RoleSetGrantedJobTitle domain){
		return new SaumtRoleSetGrantedJobTitle(domain.getCompanyId(), domain.isApplyToConcurrentPerson());
	}
	
	@Override
	public List<RoleSetGrantedJobTitle> getAllByCompanyId(String companyId) {
		return this.queryProxy()
				.query(GET_All_BY_COMPANY_ID, SaumtRoleSetGrantedJobTitle.class)
				.setParameter("companyId", companyId).getList(d -> toDomain(d));
	}

}
