package nts.uk.ctx.bs.employee.dom.employee.contact;

public interface EmployeeInfoContactRepository {
	
	void add(EmployeeInfoContact domain);
	
	void update(EmployeeInfoContact domain);
	
	void delete(String sid);
}
