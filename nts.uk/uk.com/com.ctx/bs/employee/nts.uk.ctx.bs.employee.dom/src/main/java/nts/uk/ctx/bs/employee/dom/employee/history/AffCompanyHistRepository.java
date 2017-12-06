package nts.uk.ctx.bs.employee.dom.employee.history;

public interface AffCompanyHistRepository {
	/** add new affiliation history */
	void add(AffCompanyHist domain);

	/** update one affiliation history */
	void update(AffCompanyHist domain);

	/** remove all affiliation history of one person */
	void remove(AffCompanyHist domain);

	/** remove one affiliation by personId & employeeId */
	void remove(String pId, String employeeId, String hisId);

	/** remove affiliation history of one employee */
	void remove(String pId, String employeeId);

	/** remove all affiliation history of one person */
	void remove(String pId);

	AffCompanyHist getAffCompanyHistoryOfPerson(String personId);

	AffCompanyHist getAffCompanyHistoryOfEmployee(String employeeId);

	AffCompanyHist getAffCompanyHistoryOfHistInfo(String histId);

	/** Hop.NT */

	/** Add new affiliation history */
	void add(String sid, String pId, AffCompanyHistItem item);

	/** Update one affiliation history */
	void update(AffCompanyHistItem item);

	/** End */
}
