package nts.uk.ctx.sys.auth.infra.repository.grant.rolesetjob;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitleDetail;
import nts.uk.ctx.sys.auth.dom.grant.rolesetjob.RoleSetGrantedJobTitleDetailRepository;
import nts.uk.ctx.sys.auth.infra.entity.grant.rolesetjob.SaumtRoleSetGrantedJobTitleDetail;

public class JpaRoleSetGrantedJobTitleDetailRepository extends JpaRepository
		implements RoleSetGrantedJobTitleDetailRepository {

	private final String GET_All_BY_COMPANY_ID = "SELECT d FROM SaumtRoleSetGrantedJobTitleDetail rs WHERE rs.roleSetGrantedJobTitleDetailPK.companyId = :companyId ";

	private RoleSetGrantedJobTitleDetail toDomain(SaumtRoleSetGrantedJobTitleDetail entity) {
		return new RoleSetGrantedJobTitleDetail(entity.roleSetGrantedJobTitleDetailPK.roleSetCd,
				entity.roleSetGrantedJobTitleDetailPK.jobTitleId, entity.roleSetGrantedJobTitleDetailPK.companyId);
	}

	private SaumtRoleSetGrantedJobTitleDetail toEntity(RoleSetGrantedJobTitleDetail domain) {
		return new SaumtRoleSetGrantedJobTitleDetail(domain.getRoleSetCd().v(), domain.getJobTitleId(),
				domain.getCompanyId());
	}

	@Override
	public List<RoleSetGrantedJobTitleDetail> getAllByCompany(String companyId) {
		return this.queryProxy()
				.query(GET_All_BY_COMPANY_ID, SaumtRoleSetGrantedJobTitleDetail.class)
				.setParameter("companyId", companyId).getList(d -> toDomain(d));
	}
}
