package nts.uk.ctx.at.request.dom.setting.company.emailset;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public interface AppEmailSetRepository {
	
	public AppEmailSet findByDivision(Division division);
	
	public AppEmailSet findByCID(String companyID);
	
}
