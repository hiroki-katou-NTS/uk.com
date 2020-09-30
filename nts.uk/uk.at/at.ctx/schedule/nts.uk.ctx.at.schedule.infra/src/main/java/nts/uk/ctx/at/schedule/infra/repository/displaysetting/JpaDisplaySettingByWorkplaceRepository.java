package nts.uk.ctx.at.schedule.infra.repository.displaysetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByWorkplaceRepository;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.KscmtDispSetBywkp;

/**
 * スケジュール修正職場別の表示設定Repository	
 * @author HieuLt
 *
 */
@Stateless
public class JpaDisplaySettingByWorkplaceRepository extends JpaRepository implements DisplaySettingByWorkplaceRepository{

	
	private static final String SELECT_BY_KEY  = "SELECT k FROM KscmtDispSetting k WHERE k.pk.cid = :CID";
	@Override
	public Optional<DisplaySettingByWorkplace> get(String companyID) {
		
		return this.queryProxy().query(SELECT_BY_KEY , KscmtDispSetBywkp.class)
				.setParameter("CID", companyID)
				.getSingle(c->c.toDomain());
	}

	@Override
	public void insert(DisplaySettingByWorkplace workScheDisplaySetting) {
		this.commandProxy().insert(KscmtDispSetBywkp.toEntity(workScheDisplaySetting));
		
	}

	@Override
	public void update(DisplaySettingByWorkplace workScheDisplaySetting) {
		this.commandProxy().update(KscmtDispSetBywkp.toEntity(workScheDisplaySetting));
	}

}
