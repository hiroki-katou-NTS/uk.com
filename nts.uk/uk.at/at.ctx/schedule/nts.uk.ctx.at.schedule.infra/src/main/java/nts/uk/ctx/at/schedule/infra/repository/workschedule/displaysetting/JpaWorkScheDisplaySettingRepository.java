package nts.uk.ctx.at.schedule.infra.repository.workschedule.displaysetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.WorkScheDisplaySetting;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.WorkScheDisplaySettingRepo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfo;
import nts.uk.ctx.at.schedule.infra.entity.workschedule.displaysetting.KscmtDispSetting;

/**
 * 勤務予定の表示設定 Repository	
 * @author HieuLt
 *
 */
@Stateless
public class JpaWorkScheDisplaySettingRepository extends JpaRepository implements WorkScheDisplaySettingRepo{

	
	private static final String SELECT_BY_KEY  = "SELECT k FROM KscmtDispSetting k WHERE k.pk.cid = :CID";
	@Override
	public Optional<WorkScheDisplaySetting> get(String companyID) {
		
		return this.queryProxy().query(SELECT_BY_KEY , KscmtDispSetting.class)
				.setParameter("CID", companyID)
				.getSingle(c->c.toDomain());
	}

	@Override
	public void insert(WorkScheDisplaySetting workScheDisplaySetting) {
		this.commandProxy().insert(KscmtDispSetting.toEntity(workScheDisplaySetting));
		
	}

	@Override
	public void update(WorkScheDisplaySetting workScheDisplaySetting) {
		this.commandProxy().update(KscmtDispSetting.toEntity(workScheDisplaySetting));
	}

}
