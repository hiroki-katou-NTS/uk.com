package nts.uk.pr.proto.infra;
import nts.uk.pr.proto.dom.EmployeeRepository;
public class EmployeeRepositoryMySQLImpl implements EmployeeRepository {
	public EmployeeRepositoryMySQLImpl(String host, int port, String config) {
		//init access configuration
	}
	public Employee findEmployee(int id) {return new Employee(int id);//rough result}
	public void insert(Employee emp) {}
	public void update(Employee emp){}
	public void delete(Employee emp){}
}