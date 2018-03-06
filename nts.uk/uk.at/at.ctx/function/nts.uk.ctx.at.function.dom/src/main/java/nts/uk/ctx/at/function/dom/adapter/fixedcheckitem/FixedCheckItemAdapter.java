package nts.uk.ctx.at.function.dom.adapter.fixedcheckitem;

import java.util.List;

import nts.arc.time.GeneralDate;
public interface FixedCheckItemAdapter {
	
		public boolean  checkWorkTypeNotRegister(String employeeID,GeneralDate date,String workTypeCD);
		
		public boolean  checkWorkTimeNotRegister(String employeeID,GeneralDate date,String workTimeCD);
		
		public List<ValueExtractAlarmWRAdapterDto> checkPrincipalUnconfirm(String workplaceID,String employeeID,GeneralDate startDate,GeneralDate endDate);
		
		public List<ValueExtractAlarmWRAdapterDto> checkAdminUnverified(String workplaceID,String employeeID,GeneralDate startDate,GeneralDate endDate);
		
		public List<ValueExtractAlarmWRAdapterDto> checkingData(String employeeID,GeneralDate startDate,GeneralDate endDate);
}
