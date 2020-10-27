package nts.uk.ctx.at.request.infra.repository.setting.company.request.applicationsetting.apptypesetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.request.applicationsetting.apptypesetting.KrqmtDisplayReason;
import nts.uk.ctx.at.request.infra.entity.setting.company.request.applicationsetting.apptypesetting.KrqmtDisplayReasonPK;

/**
 * 
 * @author yennth
 *
 */
@Stateless
public class JpaDisplayReasonRepository_Old extends JpaRepository implements DisplayReasonRepository{
	private static final String SELECT_NO_WHERE = "SELECT c FROM KrqmtDisplayReason c ";
	private static final String SELECT_BY_COM = SELECT_NO_WHERE + " WHERE c.krqmtDisplayReasonPK.companyId = :companyId ";
	
	// convert from entity to domain
	private DisplayReason toDomain(KrqmtDisplayReason entity){
		return DisplayReason.toDomain(entity.krqmtDisplayReasonPK.companyId, 
										entity.krqmtDisplayReasonPK.typeOfLeaveApp, 
										entity.displayFixedReason, 
										entity.displayAppReason);
	}
	
	// convert from domain to entity
	private KrqmtDisplayReason toEntity(DisplayReason domain){
		val entity = new KrqmtDisplayReason(new KrqmtDisplayReasonPK(domain.getCompanyId(), domain.getTypeOfLeaveApp().value), 
											domain.getDisplayFixedReason().value, 
											domain.getDisplayAppReason().value);
		return entity;
	}

	@Override
	public List<DisplayReason> findDisplayReason(String companyId) {
		return this.queryProxy().query(SELECT_BY_COM, KrqmtDisplayReason.class)
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
		Optional<KrqmtDisplayReason> getData = this.queryProxy().find(new KrqmtDisplayReasonPK(companyId, typeOfLeaveApp), KrqmtDisplayReason.class);
		if(getData != null){
			return getData.map(c -> toDomain(c));
		}
		return null;
	}
	/**
	 * ドメインモデル「休暇申請」を取得する
	 * @param 会社ID companyId
	 * @param 休暇申請の種類 hdType
	 * @return
	 */
	@Override
	public Optional<DisplayReason> findByHdType(String companyId, int hdType) {
		return this.queryProxy().find(new KrqmtDisplayReasonPK(companyId, hdType), KrqmtDisplayReason.class)
				.map(c -> toDomain(c));
	}
}
