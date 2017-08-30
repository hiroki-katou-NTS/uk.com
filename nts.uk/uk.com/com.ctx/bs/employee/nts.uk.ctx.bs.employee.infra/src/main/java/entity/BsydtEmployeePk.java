package entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;

public class BsydtEmployeePk implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name = "EMPLOYEE_ID")
	public String EmployeeId;

}
