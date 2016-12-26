package nts.uk.ctx.pr.proto.dom.personalinfo.wage.wagename;

import java.util.List;

import javax.ejb.Stateless;


@Stateless
public interface PersonalWageNameRepository {
	
	/**
	 * getPersonalWageName
	 * @param companyCode
	 * @param categoryAtr
	 * @return
	 */
	List<PersonalWageNameMaster> getPersonalWageName(String companyCode, int categoryAtr);
	
}
