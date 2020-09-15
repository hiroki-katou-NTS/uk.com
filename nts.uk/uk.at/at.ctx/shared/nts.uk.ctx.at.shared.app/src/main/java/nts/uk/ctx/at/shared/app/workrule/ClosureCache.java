package nts.uk.ctx.at.shared.app.workrule;

import java.util.Optional;

import nts.arc.layer.app.cache.MapCache;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

public class ClosureCache {
	
	public static final String DOMAIN_NAME = Closure.class.getName();
	
	private final MapCache<Integer, Closure> cache;
	
	public ClosureCache(ClosureRepository repo, String companyId) {
		super();
		this.cache = MapCache.incremental(closureId -> repo.findById(companyId, closureId));
	}
	
	public Optional<Closure> get(int closureId){
		return cache.get(closureId);
	}
}
