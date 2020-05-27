package nts.uk.ctx.bs.employee.app.cache.employment.history;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.KeyDateHistoryCache;
import nts.arc.layer.app.cache.MapCache;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public class EmploymentHistoryCache {
	
	public static final String DOMAIN_NAME = EmploymentHistory.class.getName();

//	private final MapCache<String, EmploymentHistory> cache;
//	
//	public EmploymentHistoryCache(EmploymentHistoryRepository repo) {
//		super();
//		this.cache = KeyDateHistoryCache.incremental();
//	}
//	
//	
//	
//	public EmploymentHistory get(String employeeId, DatePeriod period){
//		return cache.get(employeeId, date);
//	}
//	
//	public List<EmploymentHistory> gets(List<String>employeeId, DatePeriod period){
//		
//	}
}
