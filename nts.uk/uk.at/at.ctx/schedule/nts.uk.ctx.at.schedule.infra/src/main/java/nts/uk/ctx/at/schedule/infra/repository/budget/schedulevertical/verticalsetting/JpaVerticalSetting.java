package nts.uk.ctx.at.schedule.infra.repository.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;

@Stateless
public class JpaVerticalSetting extends JpaRepository implements VerticalSettingRepository {

	@Override
	public List<VerticalCalSet> findAllVerticalCalSet(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<VerticalCalSet> getVerticalCalSetByCode(String companyId, String verticalCalCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addVerticalCalSet(VerticalCalSet verticalCalSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVerticalCalSet(VerticalCalSet verticalCalSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteVerticalCalSet(String companyId, String verticalCalCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<VerticalCalItem> findAllVerticalCalItem(String companyId, String verticalCalCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVerticalCalItems(String companyId, String verticalCalCd) {
		// TODO Auto-generated method stub
		
	}
}
