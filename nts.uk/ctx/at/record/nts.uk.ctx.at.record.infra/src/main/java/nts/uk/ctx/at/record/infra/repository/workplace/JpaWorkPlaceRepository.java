package nts.uk.ctx.at.record.infra.repository.workplace;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.workplace.KwlmtWorkLocation;
import nts.uk.ctx.at.record.infra.entity.workplace.KwlmtWorkLocationPK;
import workplace.WorkPlace;
import workplace.WorkPlaceRepository;

@Stateless
/**
 * 
 * @author hieult
 *
 */
public class JpaWorkPlaceRepository extends JpaRepository implements WorkPlaceRepository {

	private final String SELECT= "SELECT c FROM KwlmtWorkLocation c";
	private final String SELECT_SINGLE = "SELECT c FROM KwlmtWorkLocation c WHERE c.kwlmtWorkLocationPK.companyID = :companyID AND c.kwlmtWorkLocationPK.workLocationCD = :workLocationCD";
	private final String SELECT_ALL_BY_COMPANY = SELECT + " WHERE c.kwlmtWorkLocationPK.companyID = :companyID";
	
	
	@Override
	public List<WorkPlace> findAll(String companyID) {
		return this.queryProxy()
				.query(SELECT_ALL_BY_COMPANY, KwlmtWorkLocation.class)
				.setParameter("companyID", companyID)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<WorkPlace> findByCode(String companyID, String workPlaceCD) {
		return this.queryProxy()
				.query(SELECT_SINGLE, KwlmtWorkLocation.class)
				.setParameter("companyID", companyID)
				.setParameter("workPlaceCD", workPlaceCD)
				.getSingle(c -> toDomain(c));
	}

	private WorkPlace toDomain(KwlmtWorkLocation entity) {
		return WorkPlace.createFromJavaType(
				entity.kwlmtWorkLocationPK.companyID,
				entity.kwlmtWorkLocationPK.workLocationCD,
				entity.workLocationName,
				entity.horiDistance, 
				entity.vertiDistance,
				entity.latitude, 
				entity.longitude);
	}
	
	/*private KwlmtWorkLocation toEntity (WorkPlace domain){
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
