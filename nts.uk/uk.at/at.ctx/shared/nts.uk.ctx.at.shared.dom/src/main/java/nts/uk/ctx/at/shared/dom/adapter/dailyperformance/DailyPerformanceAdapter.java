package nts.uk.ctx.at.shared.dom.adapter.dailyperformance;

import nts.arc.time.GeneralDate;

public interface DailyPerformanceAdapter {
  PresenceDataApprovedImport findByIdDateAndType (String employeeID , GeneralDate startDate , GeneralDate endDate , int rootType);
  
  boolean checkDataApproveed(GeneralDate startDate, GeneralDate endDate, String approverID, Integer rootType, String companyID);
}
