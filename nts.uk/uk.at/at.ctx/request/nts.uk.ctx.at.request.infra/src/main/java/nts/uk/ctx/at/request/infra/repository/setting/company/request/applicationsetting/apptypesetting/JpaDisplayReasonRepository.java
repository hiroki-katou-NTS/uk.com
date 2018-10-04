package nts.uk.ctx.at.request.infra.repository.setting.company.request.applicationsetting.apptypesetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.request.applicationsetting.apptypesetting.KrqstDisplayReason;
import nts.uk.ctx.at.request.infra.entity.setting.company.request.applicationsetting.apptypesetting.KrqstDisplayReasonPK;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class JpaDisplayReasonRepository extends JpaRepository implements DisplayReasonRepository{
	private static final String SELECT_NO_WHERE = "SELECT c FROM KrqstDisplayReason c ";
	private static final String SELECT_BY_COM = SELECT_NO_WHERE + " WHERE c.krqstDisplayReasonPK.companyId = :companyId ";
	
	// convert from entity to domain
	private DisplayReason toDomain(KrqstDisplayReason entity){
		return DisplayReason.toDomain(entity.krqstDisplayReasonPK.companyId, 
										entity.krqstDisplayReasonPK.typeOfLeaveApp, 
										entity.displayFixedReason, 
										entity.displayAppReason);
	}
	
	// convert from domain to entity
	private KrqstDisplayReason toEntity(DisplayReason domain){
		val entity = new KrqstDisplayReason(new KrqstDisplayReasonPK(domain.getCompanyId(), domain.getTypeOfLeaveApp().value), 
											domain.getDisplayFixedReason().value, 
											domain.getDisplayAppReason().value);
		return entity;
	}

	@Override
	public List<DisplayReason> findDisplayReason(String companyId) {
		return this.queryProxy().query(SELECT_BY_COM, KrqstDisplayReason.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomain(c));
	}

	@Override
	public void update(DisplayReason update) {
		this.commandProxy().update(toEntity(update));
	}

	@Override
	public void insert(DisplayReason insert) {
		this.commandProxy().insert(toEntity(insert));
	}

	@Override
	public Optional<DisplayReason> findDpReasonHd(String companyId, int typeOfLeaveApp) {
		Optional<KrqstDisplayReason> getData = this.queryProxy().find(new KrqstDisplayReasonPK(companyId, typeOfLeaveApp), KrqstDisplayReason.class);
		if(getData != null){
			return getData.map(c -> toDomain(c));
		}
		return null;
	}
}
