package nts.uk.ctx.at.record.infra.repository.worklocation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.record.infra.entity.worklocation.KwlmtWorkLocation;

@Stateless
/**
 * 
 * @author hieult
 *
 */
public class JpaWorkLocationRepository extends JpaRepository implements WorkLocationRepository {

	private static final String SELECT= "SELECT c FROM KwlmtWorkLocation c";
	private static final String SELECT_SINGLE = "SELECT c FROM KwlmtWorkLocation c WHERE c.kwlmtWorkLocationPK.companyID = :companyID AND c.kwlmtWorkLocationPK.workLocationCD = :workLocationCD";
	private static final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.kwlmtWorkLocationPK.companyID = :companyID";
	private static final String SELECT_CODE_AND_NAME = "SELECT c.kwlmtWorkLocationPK.workLocationCD, c.workLocationName FROM KwlmtWorkLocation c"
			+ " WHERE c.kwlmtWorkLocationPK.companyID = :companyID AND c.kwlmtWorkLocationPK.workLocationCD IN :workLocationCDs";
	
	
	@Override
	public List<WorkLocation> findAll(String companyID) {
		List<WorkLocation> test =  this.queryProxy()
				.query(SELECT_ALL_BY_COMPANY, KwlmtWorkLocation.class)
				.setParameter("companyID", companyID)
				.getList(c -> toDomain(c));
		return test;
	}

	@Override
	public Optional<WorkLocation> findByCode(String companyID, String workPlaceCD) {
		Optional<WorkLocation> test = this.queryProxy()
				.query(SELECT_SINGLE, KwlmtWorkLocation.class)
				.setParameter("companyID", companyID)
				.setParameter("workLocationCD", workPlaceCD)
				.getSingle(c -> toDomain(c));
		return test;
	}

	private WorkLocation toDomain(KwlmtWorkLocation entity) {
		return WorkLocation.createFromJavaType(
				entity.kwlmtWorkLocationPK.companyID,
				entity.kwlmtWorkLocationPK.workLocationCD,
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
	
	/*private KwlmtWorkLocation toEntity (WorkLocation domain){
		return new KwlmtWorkLocation(
				new KwlmtWorkLocationPK(domain.getCompanyID(), domain.getWorkLocationCD()),
				domain.getWorkLocationName(),
				domain.getHoriDistance(),
				domain.getVertiDistance(),
				domain.getLatitude().v(),
				domain.getLongitude().v()
				);
	}*/
	
	

}
