package nts.uk.ctx.bs.employee.app.cache.workplace;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.DateHistoryCache;
import nts.arc.layer.app.cache.KeyDateHistoryCache;
import nts.arc.layer.app.cache.NestedMapCache;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;

public class ParentsWorkplaceHierarchyCache {
	
	public static final String DOMAIN_NAME = Workplace.class.getName();
	
	private final KeyDateHistoryCache<String, List<WorkplaceHierarchy>> cache;

	public ParentsWorkplaceHierarchyCache(String companyId, WorkplaceConfigInfoRepository repo) {
		super();
		this.cache = KeyDateHistoryCache.incremental((workplaceId, date) -> createEntries(repo, companyId, workplaceId, date));
	}
	
	public List<WorkplaceHierarchy> get(String employeeId, GeneralDate date) {
		return cache.get(employeeId, date)
				.orElse(Collections.emptyList());
	}
	
	private static Optional<DateHistoryCache.Entry<List<WorkplaceHierarchy>>> createEntries(
			WorkplaceConfigInfoRepository repo,
			String companyId,
			String employeeId,
			GeneralDate date) {
		
		return repo.findAllParentByWkpId(companyId, date, employeeId)
				.map(conf -> new DateHistoryCache.Entry<List<WorkplaceHierarchy>>(conf.getPeriod(), conf.getLstWkpHierarchy()));
	}
	
}
