package nts.uk.ctx.at.record.infra.repository.worklocation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.record.infra.entity.worklocation.KrcmtWorkLocation;

@Stateless
/**
 * 
 * @author hieult
 *
 */
public class JpaWorkLocationRepository extends JpaRepository implements WorkLocationRepository {

	private static final String SELECT= "SELECT c FROM KrcmtWorkLocation c";
	private static final String SELECT_SINGLE = "SELECT c FROM KrcmtWorkLocation c WHERE c.krcmtWorkLocationPK.companyID = :companyID AND c.krcmtWorkLocationPK.workLocationCD = :workLocationCD";
	private static final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.krcmtWorkLocationPK.companyID = :companyID";
	private static final String SELECT_CODE_AND_NAME = "SELECT c.krcmtWorkLocationPK.workLocationCD, c.workLocationName FROM KrcmtWorkLocation c"
			+ " WHERE c.krcmtWorkLocationPK.companyID = :companyID AND c.krcmtWorkLocationPK.workLocationCD IN :workLocationCDs";
	private static final String SELECT_BY_CODES = "SELECT c FROM KrcmtWorkLocation c WHERE c.krcmtWorkLocationPK.companyID = :companyID";

	
	@Override
	public List<WorkLocation> findAll(String companyID) {
		List<WorkLocation> test =  this.queryProxy()
				.query(SELECT_ALL_BY_COMPANY, KrcmtWorkLocation.class)
				.setParameter("companyID", companyID)
				.getList(c -> toDomain(c));
		return test;
	}

	@Override
	public Optional<WorkLocation> findByCode(String companyID, String workPlaceCD) {
		Optional<WorkLocation> test = this.queryProxy()
				.query(SELECT_SINGLE, KrcmtWorkLocation.class)
				.setParameter("companyID", companyID)
				.setParameter("workLocationCD", workPlaceCD)
				.getSingle(c -> toDomain(c));
		return test;
	}

	private WorkLocation toDomain(KrcmtWorkLocation entity) {
		return WorkLocation.createFromJavaType(
				entity.krcmtWorkLocationPK.companyID,
				entity.krcmtWorkLocationPK.workLocationCD,
				entity.workLocationName,
				entity.horiDistance, 
				entity.vertiDistance,
				entity.latitude, 
				entity.longitude);
	}

	@Override
	public Map<String, String> getNameByCode(String companyId, List<String> listWorkLocationCd) {
		return this.queryProxy()
				.query(SELECT_CODE_AND_NAME, Object[].class)
				.setParameter("companyID", companyId)
				.setParameter("workLocationCDs", listWorkLocationCd)
				.getList().stream().collect(Collectors.toMap(s-> String.valueOf(s[0]), s-> String.valueOf(s[1])));
	}

	@Override
	public List<WorkLocation> findByCodes(String companyID, List<String> codes) {
		String queryString = SELECT_BY_CODES;
		
		TypedQueryWrapper<KrcmtWorkLocation> query = null;
		if (codes == null || codes.isEmpty()) {
			query = this.queryProxy()
					.query(queryString, KrcmtWorkLocation.class)
					.setParameter("companyID", companyID);
		} else {
			queryString += " AND c.krcmtWorkLocationPK.workLocationCD IN :workLocationCD";
			query = this.queryProxy()
					.query(queryString, KrcmtWorkLocation.class)
					.setParameter("companyID", companyID)
					.setParameter("workLocationCD", codes);
		}
				
		List<WorkLocation> result = query.getList(c -> toDomain(c));
		return result;
	}

}
