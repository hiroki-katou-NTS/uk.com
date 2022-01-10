package nts.uk.smile.infra.repository;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSetting;
import nts.uk.smile.dom.smilelinked.cooperationoutput.SmileLinkageOutputSettingRepository;

@Stateless
public class JpaSmileLinkageOutputSettingRepository extends JpaRepository implements SmileLinkageOutputSettingRepository {

	@Override
	public void insert(SmileLinkageOutputSetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SmileLinkageOutputSetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String contractCode, String companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SmileLinkageOutputSetting get(String contractCode, String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
