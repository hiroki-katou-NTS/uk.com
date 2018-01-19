package nts.uk.ctx.at.request.infra.repository.setting.company.request.stamp;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.stamp.StampRequestSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.request.stamp.KrqstStampRequestSetting;

@Stateless
public class JpaStampRequestSettingRepository extends JpaRepository implements StampRequestSettingRepository {

	@Override
	public Optional<StampRequestSetting> findByCompanyID(String companyID) {
		return this.queryProxy().find(companyID, KrqstStampRequestSetting.class)
				.map(x -> x.toDomain());
	}
}
