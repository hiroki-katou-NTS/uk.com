package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship;

import java.util.List;
import java.util.Optional;

public interface GrantDayRelationshipRepository {

	Optional<GrantDayRelationship> findByCd(String companyId, int sHENo, String relpCd);

	Optional<GrantDayPerRelationship> findPerByCd(String companyId, int sHENo);
	
	List<GrantDayRelationship> findBySHENo(String companyId, int sHENo);

	void insertPerRelp(GrantDayPerRelationship domain);

	void insertRelp(GrantDayRelationship domain);

	void updateRelp(String companyId, GrantDayRelationship domain);

	void deletePerRelp(String companyId, int sHENo);

	void deleteRelp(int sHENo, String relationshipCd, String companyId);
	/**
	 * get Grand Day Full By FrameNo
	 * @author hoatt
	 * @param comapyId
	 * @param frameNo
	 * @return 
	 */
	public Optional<GrantDayPerRelationship> getGrandDayFullByFrameNo(String comapyId, Integer frameNo);

}
