package nts.uk.shr.infra.permit.data;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.shr.com.permit.AvailabilityPermissionBase;
import nts.uk.shr.com.permit.AvailabilityPermissionRepositoryBase;

public abstract class JpaAvailablityPermissionRepositoryBase<D extends AvailabilityPermissionBase, E extends JpaEntityOfAvailabilityPermissionBase<D>>
		extends JpaRepository
		implements AvailabilityPermissionRepositoryBase<D> {

	@Override
	public Optional<D> find(String companyId, String roleId, int functionNo) {
		
		val cb = this.getEntityManager().getCriteriaBuilder();
		val query = cb.createQuery(this.getEntityClass());
		val root = query.from(this.getEntityClass());
		
		query.where(
				cb.equal(root.get("pk").get("companyId"), companyId),
				cb.equal(root.get("pk").get("roleId"), roleId),
				cb.equal(root.get("pk").get("functionNo"), functionNo));
		
		return this.getEntityManager().createQuery(query).getResultList().stream()
				.findFirst()
				.map(e -> e.toDomain());
	}
	
	@Override
	public List<D> find(String companyId, String roleId, List<String> functionNoList) {
		val cb = this.getEntityManager().getCriteriaBuilder();
		val query = cb.createQuery(this.getEntityClass());
		val root = query.from(this.getEntityClass());
		
		query.where(
				cb.equal(root.get("pk").get("companyId"), companyId),
				cb.equal(root.get("pk").get("roleId"), roleId),
				root.get("pk").get("functionNo").in(functionNoList));
		
		return this.getEntityManager().createQuery(query).getResultList().stream()
				.map(e -> (D)e.toDomain())
				.collect(Collectors.toList());
	}
	
	@Override
	public void add(D permission) {
		val entity = JpaEntityOfAvailabilityPermissionBase.entityFromDomain(permission, this.createEmptyEntity());
		this.commandProxy().insert(entity);
	}
	
	@Override
	public void update(D permission) {
		val entity = JpaEntityOfAvailabilityPermissionBase.entityFromDomain(permission, this.createEmptyEntity());
		this.commandProxy().update(entity);
	}
	
	protected abstract Class<E> getEntityClass();
	
	protected abstract E createEmptyEntity();
}
