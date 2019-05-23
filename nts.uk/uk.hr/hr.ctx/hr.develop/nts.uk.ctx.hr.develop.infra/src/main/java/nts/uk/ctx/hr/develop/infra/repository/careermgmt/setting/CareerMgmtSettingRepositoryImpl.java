package nts.uk.ctx.hr.develop.infra.repository.careermgmt.setting;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.careermgmt.setting.CareerMgmtSetting;
import nts.uk.ctx.hr.develop.dom.careermgmt.setting.CareerMgmtSettingRepository;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.setting.JhcmtCareerMgmtSetting;

@Stateless
public class CareerMgmtSettingRepositoryImpl extends JpaRepository implements CareerMgmtSettingRepository {

	@Override
	public CareerMgmtSetting getCareerMgmtSetting(String cId) {
		return this.getEntityManager().find(JhcmtCareerMgmtSetting.class, cId).toDomain();
	}

}
