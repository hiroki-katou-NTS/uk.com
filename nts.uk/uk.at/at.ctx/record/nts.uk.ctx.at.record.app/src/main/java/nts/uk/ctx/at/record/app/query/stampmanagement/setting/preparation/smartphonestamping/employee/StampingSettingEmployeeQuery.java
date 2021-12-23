package nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRepository;



@Stateless
public class StampingSettingEmployeeQuery {
	
	@Inject
	private StampingAreaRepository stampingAreaReposiroty;  
	
	public List<String> getStatuEmployee(List<String> listEmplId) {
		List<String> resultList = stampingAreaReposiroty.getStatusStampingEmpl(listEmplId);
		return resultList;
	}
	
}
