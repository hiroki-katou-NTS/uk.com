package nts.uk.ctx.bs.employee.infra.repository.holidaysetting.configuration;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository;

@Stateless
public class JpaPublicHolidaySettingRepository extends JpaRepository implements PublicHolidaySettingRepository{

	@Override
	public Optional<PublicHolidaySetting> findByCID(String companyId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void update(PublicHolidaySetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(PublicHolidaySetting domain) {
		// TODO Auto-generated method stub
		
	}

}
