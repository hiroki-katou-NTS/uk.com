package nts.uk.ctx.pr.proto.dom.personalwagename;

import java.util.List;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public interface PersonalWageNameRepository {
	
	/**
	 * getPersonalWageName
	 * @param companyCode
	 * @param categoryAtr
	 * @return
	 */
	List<PersonalWageNameMaster> getPersonalWageName(String companyCode, int categoryAtr);
}
