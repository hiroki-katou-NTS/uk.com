package nts.uk.smile.infra.repository;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSetting;
import nts.uk.smile.dom.smilelinked.cooperationacceptance.SmileCooperationAcceptanceSettingRepository;

@Stateless
public class JpaSmileCooperationAcceptanceSettingRepository extends JpaRepository implements SmileCooperationAcceptanceSettingRepository {

	@Override
	public void insert(SmileCooperationAcceptanceSetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SmileCooperationAcceptanceSetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String contractCode, String companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAll(List<SmileCooperationAcceptanceSetting> smileCooperationAcceptanceSettings) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertAll(List<SmileCooperationAcceptanceSetting> smileCooperationAcceptanceSettings) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SmileCooperationAcceptanceSetting get(String contractCode, String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
