package nts.uk.ctx.at.request.dom.setting.company.emailset;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface AppEmailSetRepository {
	
	AppEmailSet findByDivision(Division division);
	
	AppEmailSet findByCID(String companyID);

	void save(AppEmailSet domain);
	
}
