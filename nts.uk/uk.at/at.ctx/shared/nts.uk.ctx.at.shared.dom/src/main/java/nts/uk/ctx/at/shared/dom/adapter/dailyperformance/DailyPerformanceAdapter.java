package nts.uk.ctx.at.shared.dom.adapter.dailyperformance;

import nts.arc.time.GeneralDate;

public interface DailyPerformanceAdapter {
  
  boolean checkDataApproveed(GeneralDate startDate, GeneralDate endDate, String approverID, Integer rootType, String companyID);
}
