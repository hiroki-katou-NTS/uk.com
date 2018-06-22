package nts.uk.ctx.at.record.dom.workrecord.erroralarm.subjecttoextracted.collapsetargetperson;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CollapseTargetPerson {
	
	@Inject
	private EmployeeGeneralInfoAdapter employeeGeneralInfoAdapter;
	/**
	 * 対象者をしぼり込む(レスポンス改善)
	 */
	public List<String> reduceTargetResponseImprovement(List<String> employeeIds, DatePeriod period){
		
		/**Imported「社員の履歴情報」を取得する  */
		EmployeeGeneralInfoImport employeeGeneralInfoImport = employeeGeneralInfoAdapter.getEmployeeGeneralInfo(employeeIds, period);
		
		
		
		return null;
	}
	
	public List<String> getListEmployeeID(GeneralDate date) {
		return Collections.emptyList();
	}
}
