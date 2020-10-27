package nts.uk.ctx.sys.assist.infra.repository.storage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployees;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployeesRepository;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspdtSaveTarget;
import nts.uk.ctx.sys.assist.infra.entity.storage.SspdtSaveTargetPk;

@Stateless
public class JpaTargetEmployeesRepository extends JpaRepository implements TargetEmployeesRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspdtSaveTarget f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.targetEmployeesPk.storeProcessingId =:storeProcessingId AND  f.targetEmployeesPk.employeeId =:employeeId ";
	private static final String SELECT_BY_KEY_STRING_LIST = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.targetEmployeesPk.storeProcessingId =:storeProcessingId";
	private static final String REMOVE_BUSINESS_NAME = "UPDATE SspdtSaveTarget f SET f.businessname = NULL"
			+ " WHERE  f.targetEmployeesPk.storeProcessingId =:storeProcessingId";

	@Override
	public List<TargetEmployees> getAllTargetEmployees() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspdtSaveTarget.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<TargetEmployees> getTargetEmployeesById(String storeProcessingId, String employeeId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspdtSaveTarget.class)
				.setParameter("storeProcessingId", storeProcessingId).setParameter("employeeId", employeeId)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(TargetEmployees domain) {
		this.commandProxy().insert(SspdtSaveTarget.toEntity(domain));
	}

	@Override
	public void update(TargetEmployees domain) {
		this.commandProxy().update(SspdtSaveTarget.toEntity(domain));
	}

	@Override
	public void remove(String storeProcessingId, String employeeId) {
		this.commandProxy().remove(SspdtSaveTarget.class,
				new SspdtSaveTargetPk(storeProcessingId, employeeId));
	}

	@Override
	public void addAll(List<TargetEmployees> employees) {
		for (TargetEmployees targetEmployee : employees) {
			this.commandProxy().insert(SspdtSaveTarget.toEntity(targetEmployee));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.storage.TargetEmployeesRepository#
	 * getTargetEmployeesListById(java.lang.String)
	 */
	@Override
	public List<TargetEmployees> getTargetEmployeesListById(String storeProcessingId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING_LIST, SspdtSaveTarget.class)
				.setParameter("storeProcessingId", storeProcessingId).getList(c -> c.toDomain());
	}

	@Override
	public void removeBusinessName(String storeProcessingId) {
		this.getEntityManager().createQuery(REMOVE_BUSINESS_NAME)
		.setParameter("storeProcessingId", storeProcessingId)
		.executeUpdate();
	}

}
