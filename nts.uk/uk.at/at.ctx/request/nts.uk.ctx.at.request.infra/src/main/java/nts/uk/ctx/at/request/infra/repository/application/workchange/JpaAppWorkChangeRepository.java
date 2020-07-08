package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.logging.log4j.util.Strings;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange_Old;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChange;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChangePk;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChangePk_Old;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChange_Old;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 4
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppWorkChangeRepository extends JpaRepository implements AppWorkChangeRepository {

	public static final String FIND_BY_ID = "SELECT *  FROM KRQDT_APP_WORK_CHANGE WHERE APP_ID = @appID";

	@Override
	public Optional<AppWorkChange> findbyID(String appID) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy()).paramString("appID", appID)
				.getSingle(res -> KrqdtAppWorkChange.MAPPER.toEntity(res).toDomain());
	}

	@Override
	public void add(AppWorkChange appWorkChange) {
		this.commandProxy().insert(toEntity(appWorkChange));

	}

	@Override
	public void update(AppWorkChange appWorkChange) {
		KrqdtAppWorkChange krqdtAppWorkChange = toEntity(appWorkChange);
		Optional<KrqdtAppWorkChange> updateWorkChange = this.queryProxy().find(krqdtAppWorkChange.appWorkChangePk, KrqdtAppWorkChange.class);
    	if (!updateWorkChange.isPresent()) {
			return;
		}
    	updateWorkChange.get().goWorkAtr = krqdtAppWorkChange.goWorkAtr;
    	updateWorkChange.get().backHomeAtr = krqdtAppWorkChange.backHomeAtr;
    	updateWorkChange.get().workTypeCd = krqdtAppWorkChange.workTypeCd;
    	updateWorkChange.get().workTimeCd = krqdtAppWorkChange.workTimeCd;
    	updateWorkChange.get().workTimeStart1 = krqdtAppWorkChange.workTimeStart1;
    	updateWorkChange.get().workTimeEnd1 = krqdtAppWorkChange.workTimeEnd1;
    	updateWorkChange.get().workTimeStart2 = krqdtAppWorkChange.workTimeStart2;
    	updateWorkChange.get().workTimeEnd2 = krqdtAppWorkChange.workTimeEnd2;
    	this.commandProxy().update(updateWorkChange.get());
	}

	@Override
	public void remove(AppWorkChange appWorkChange) {
		this.commandProxy().remove(KrqdtAppWorkChange.class, new KrqdtAppWorkChangePk(AppContexts.user().companyId(), appWorkChange.getAppID())); 

	}

	private KrqdtAppWorkChange toEntity(AppWorkChange domain) {
		TimeZoneWithWorkNo timeZoneWithWorkNo1 = null;
		TimeZoneWithWorkNo timeZoneWithWorkNo2 = null;
		for (TimeZoneWithWorkNo item : domain.getTimeZoneWithWorkNoLst()) {
			if(item.getWorkNo().v() == 1) {
				timeZoneWithWorkNo1 = item;
			}else if (item.getWorkNo().v() == 2){
				timeZoneWithWorkNo2 = item;
			}
		}
		
		return new KrqdtAppWorkChange(
				// do have value of companyID
				new KrqdtAppWorkChangePk(AppContexts.user().companyId(), domain.getAppID()),
				domain.getOpWorkTypeCD().get().v(),
				domain.getOpWorkTimeCD().get().v(),
				domain.getStraightGo().value,
				domain.getStraightBack().value,
				timeZoneWithWorkNo1.getTimeZone().getStartTime().v(),
				timeZoneWithWorkNo1.getTimeZone().getEndTime().v(),
				timeZoneWithWorkNo2.getTimeZone().getStartTime().v(),
				timeZoneWithWorkNo2.getTimeZone().getEndTime().v());
	}

}
