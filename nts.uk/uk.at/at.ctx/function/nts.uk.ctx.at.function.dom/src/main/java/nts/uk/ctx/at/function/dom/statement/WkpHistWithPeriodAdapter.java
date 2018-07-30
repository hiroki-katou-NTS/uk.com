package nts.uk.ctx.at.function.dom.statement;

import java.util.List;

import nts.uk.ctx.at.function.dom.statement.dtoimport.WkpHistWithPeriodImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface WkpHistWithPeriodAdapter {
	public WkpHistWithPeriodImport getLstHistByWkpsAndPeriod(List<String> wkpIds, DatePeriod period); 
}
