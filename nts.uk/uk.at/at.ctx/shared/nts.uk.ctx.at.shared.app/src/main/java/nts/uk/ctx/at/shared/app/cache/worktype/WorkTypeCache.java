package nts.uk.ctx.at.shared.app.cache.worktype;

import java.util.Optional;

import nts.arc.layer.app.cache.MapCache;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

public class WorkTypeCache {
	public static final String DOMAIN_NAME = WorkType.class.getName();
	
	private final MapCache<String, WorkType> cache;
	
	public WorkTypeCache(WorkTypeRepository repo, String companyId) {
		this.cache = MapCache.incremental(cd -> repo.findByPK(companyId, cd));
	}
	
	public Optional<WorkType> get(String workTypeCd){
		return cache.get(workTypeCd);
	}
}
