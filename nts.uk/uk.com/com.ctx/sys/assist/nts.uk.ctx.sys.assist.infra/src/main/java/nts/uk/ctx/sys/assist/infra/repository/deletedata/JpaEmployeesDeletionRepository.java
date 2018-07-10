package nts.uk.ctx.sys.assist.infra.repository.deletedata;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.EmployeesDeletionRepository;
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspdtEmployeesDeletion;
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspdtEmployeesDeletionPK;

@Stateless
public class JpaEmployeesDeletionRepository extends JpaRepository implements EmployeesDeletionRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspdtEmployeesDeletion f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.sspdtEmployeesDeletionPK.delId =:delId AND  f.sspdtEmployeesDeletionPK.employeeId =:employeeId ";
	private static final String SELECT_BY_KEY_STRING_LIST = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.sspdtEmployeesDeletionPK.delId =:delId";

	@Override
	public List<EmployeeDeletion> getAllEmployeesDeletion() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspdtEmployeesDeletion.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<EmployeeDeletion> getEmployeesDeletionById(String delId, String employeeId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspdtEmployeesDeletion.class)
				.setParameter("delId", delId).setParameter("employeeId", employeeId)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(EmployeeDeletion domain) {
		this.commandProxy().insert(SspdtEmployeesDeletion.toEntity(domain));
	}

	@Override
	public void update(EmployeeDeletion domain) {
		this.commandProxy().update(SspdtEmployeesDeletion.toEntity(domain));
	}

	@Override
	public void remove(String delId, String employeeId) {
		this.commandProxy().remove(SspdtEmployeesDeletion.class,
				new SspdtEmployeesDeletionPK(delId, employeeId));
	}

	@Override
	public void addAll(List<EmployeeDeletion> employees) {
		for (EmployeeDeletion employeeDeletion : employees) {
			this.commandProxy().insert(SspdtEmployeesDeletion.toEntity(employeeDeletion));
		}
		this.getEntityManager().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public List<EmployeeDeletion> getEmployeesDeletionListById(String delId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING_LIST, SspdtEmployeesDeletion.class)
				.setParameter("delId", delId).getList(c -> c.toDomain());
	}

}
