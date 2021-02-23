package nts.uk.ctx.office.infra.repository.reference.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiry;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiryRepository;
import nts.uk.ctx.office.infra.entity.reference.auth.SpecifyAuthInquiryEntity;
import nts.uk.ctx.office.infra.entity.reference.auth.SpecifyAuthInquiryEntityPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SpecifyAuthInquiryRepositoryImpl extends JpaRepository implements SpecifyAuthInquiryRepository{

	private static final String FIND_BY_CID_ROLEID = String.join(" ",
			"SELECT s FROM SpecifyAuthInquiryEntity s",
			"WHERE s.pk.cid = :cid AND s.pk.employmentRoleId = :roleId");
	
	private static final String FIND_BY_CID = String.join(" ",
			"SELECT s FROM SpecifyAuthInquiryEntity s",
			"WHERE s.pk.cid = :cid");
	
	@Override
	public void insert(SpecifyAuthInquiry domain) {
		List<SpecifyAuthInquiryEntity> entities = this.toNewListEntity(domain);
		this.commandProxy().insertAll(entities);
	}

	@Override
	public void update(SpecifyAuthInquiry domain) {
		this.delete(domain);
		this.getEntityManager().flush();
		this.insert(domain);
	}

	private void delete(SpecifyAuthInquiry domain) {
		List<SpecifyAuthInquiryEntity> entities = this.queryProxy()
				.query(FIND_BY_CID_ROLEID, SpecifyAuthInquiryEntity.class)
				.setParameter("cid", domain.getCid())
				.setParameter("roleId", domain.getEmploymentRoleId())
				.getList();
		this.commandProxy().removeAll(entities);
	}

	@Override
	public Optional<SpecifyAuthInquiry> getByCidAndRoleId(String cid, String roleId) {
		List<SpecifyAuthInquiryEntity> entities = this.queryProxy()
				.query(FIND_BY_CID_ROLEID, SpecifyAuthInquiryEntity.class)
				.setParameter("cid", cid)
				.setParameter("roleId", roleId)
				.getList();
		return Optional.ofNullable(this.toDomain(entities));
	}

	@Override
	public List<SpecifyAuthInquiry> getByCid(String cid) {
		List<SpecifyAuthInquiryEntity> entities = this.queryProxy().query(FIND_BY_CID, SpecifyAuthInquiryEntity.class)
			.setParameter("cid", cid)
			.getList();
		return this.toListDomain(entities);
	}
	
	private List<SpecifyAuthInquiryEntity> toNewListEntity(SpecifyAuthInquiry domain) {
		return domain.getPositionIdSeen().stream().map(mapper -> {
			SpecifyAuthInquiryEntity entity = new SpecifyAuthInquiryEntity();
			SpecifyAuthInquiryEntityPK pk = new SpecifyAuthInquiryEntityPK();
			pk.setCid(AppContexts.user().companyId());
			pk.setEmploymentRoleId(domain.getEmploymentRoleId());
			pk.setPositionIdSeen(mapper);
			entity.setVersion(0);
			entity.setContractCd(AppContexts.user().contractCode());
			entity.setPk(pk);
			return entity;
		}).collect(Collectors.toList());
	}
	
	private SpecifyAuthInquiry toDomain(List<SpecifyAuthInquiryEntity> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return SpecifyAuthInquiry.builder()
				.cid(entities.get(0).getPk().getCid())
				.employmentRoleId(entities.get(0).getPk().getEmploymentRoleId())
				.positionIdSeen(entities.stream()
						.map(mapper -> mapper.getPk().getPositionIdSeen())
						.collect(Collectors.toList()))
				.build();
	}
	
	private List<SpecifyAuthInquiry> toListDomain(List<SpecifyAuthInquiryEntity> entities) {
		if (entities.isEmpty()) {
			return new ArrayList<>();
		}
		Map<String, List<SpecifyAuthInquiryEntity>> entityMap = new HashMap<String, List<SpecifyAuthInquiryEntity>>();
		entities.stream().forEach(x -> {
			entityMap.computeIfPresent(x.getPk().getEmploymentRoleId(), (k, v) -> {
				v.add(x);
				return v;
			});
			entityMap.computeIfAbsent(x.getPk().getEmploymentRoleId(), k -> new ArrayList<>(Arrays.asList(x)));
		});
		List<SpecifyAuthInquiry> result = new ArrayList<>();
		entityMap.forEach((key, value) -> {
			result.add(this.toDomain(value));
		});
		return result;
	}

}
