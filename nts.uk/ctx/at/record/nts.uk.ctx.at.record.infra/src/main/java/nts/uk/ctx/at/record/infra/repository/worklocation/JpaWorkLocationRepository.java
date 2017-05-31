package nts.uk.ctx.at.record.infra.repository.worklocation;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.worklocation.KwlmtWorkLocation;
import worklocation.WorkLocation;
import worklocation.WorkLocationRepository;

@Stateless
/**
 * 
 * @author hieult
 *
 */
public class JpaWorkLocationRepository extends JpaRepository implements WorkLocationRepository {

	private final String SELECT= "SELECT c FROM KwlmtWorkLocation c";
	private final String SELECT_SINGLE = "SELECT c FROM KwlmtWorkLocation c WHERE c.kwlmtWorkLocationPK.companyID = :companyID AND c.kwlmtWorkLocationPK.workLocationCD = :workLocationCD";
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.kwlmtWorkLocationPK.companyID = :companyID";
	
	
	@Override
	public List<WorkLocation> findAll(String companyID) {
		return this.queryProxy()
				.query(SELECT_ALL_BY_COMPANY, KwlmtWorkLocation.class)
				.setParameter("companyID", companyID)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<WorkLocation> findByCode(String companyID, String workPlaceCD) {
		return this.queryProxy()
				.query(SELECT_SINGLE, KwlmtWorkLocation.class)
				.setParameter("companyID", companyID)
				.setParameter("workPlaceCD", workPlaceCD)
				.getSingle(c -> toDomain(c));
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
