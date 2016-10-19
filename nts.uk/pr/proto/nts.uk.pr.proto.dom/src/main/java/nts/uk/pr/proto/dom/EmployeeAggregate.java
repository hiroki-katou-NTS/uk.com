package nts.uk.pr.proto.dom;
public class EmployeeAggregate {
	private EmployeeRepository empRepo;
	public EmployeeAggregate(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}
	public void changeSalary(int id, double amout) {
		Employee employee = empRepo.findEmployee(id);
		employee.setSalary(employee.getSalary() + amout);
	}
	public void setJobs(int id, String job) {}
	//public Employee getEmployee(int id) {}
}