package nts.uk.ctx.sys.auth.infra.repository.grant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedJobTitle;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedJobTitleDetail;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedJobTitleRepository;
import nts.uk.ctx.sys.auth.infra.entity.grant.SaumtRoleSetGrantedJobTitle;
import nts.uk.ctx.sys.auth.infra.entity.grant.SaumtRoleSetGrantedJobTitleDetail;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class JpaRoleSetGrantedJobTitleRepository extends JpaRepository implements RoleSetGrantedJobTitleRepository {

	private final String GET_All_BY_COMPANY_ID = "SELECT d FROM SaumtRoleSetGrantedJobTitle rs WHERE rs.companyId = :companyId ";

	private RoleSetGrantedJobTitle toDomain(SaumtRoleSetGrantedJobTitle entity) {
		return new RoleSetGrantedJobTitle(entity.companyId, entity.applyToConcurrentPerson, entity.details.stream()
				.map(item -> new RoleSetGrantedJobTitleDetail(item.roleSetGrantedJobTitleDetailPK.roleSetCd,
						item.roleSetGrantedJobTitleDetailPK.jobTitleId, item.roleSetGrantedJobTitleDetailPK.companyId))
				.collect(Collectors.toList()));
	}

	private SaumtRoleSetGrantedJobTitle toEntity(RoleSetGrantedJobTitle domain) {
		return new SaumtRoleSetGrantedJobTitle(domain.getCompanyId(), domain.isApplyToConcurrentPerson(),
				domain.getDetails().stream().map(item -> new SaumtRoleSetGrantedJobTitleDetail(item.getRoleSetCd().v(),
						item.getJobTitleId(), item.getCompanyId())).collect(Collectors.toList()));
	}

	@Override
	public List<RoleSetGrantedJobTitle> getAllByCompanyId(String companyId) {
		return this.queryProxy().query(GET_All_BY_COMPANY_ID, SaumtRoleSetGrantedJobTitle.class)
				.setParameter("companyId", companyId).getList(d -> toDomain(d));
	}

	@Override
	public Optional<RoleSetGrantedJobTitle> getOneByCompanyId(String companyId) {
		SaumtRoleSetGrantedJobTitle entity = this.queryProxy()
				.query(GET_All_BY_COMPANY_ID, SaumtRoleSetGrantedJobTitle.class).setParameter("companyId", companyId)
				.getSingleOrNull();
		if (entity == null) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entity));
		}
	}

	@Override
	public void insert(RoleSetGrantedJobTitle domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(RoleSetGrantedJobTitle domain) {
		SaumtRoleSetGrantedJobTitle entity = this.queryProxy()
				.find(domain.getCompanyId(), SaumtRoleSetGrantedJobTitle.class).get();
		entity.applyToConcurrentPerson = domain.isApplyToConcurrentPerson();
		entity.details = domain.getDetails().stream()
				.map(item -> new SaumtRoleSetGrantedJobTitleDetail(item.getRoleSetCd().v(), item.getJobTitleId(),
						item.getCompanyId()))
				.collect(Collectors.toList());
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(String companyId) {
		this.commandProxy().remove(SaumtRoleSetGrantedJobTitle.class, companyId);

	}

}
