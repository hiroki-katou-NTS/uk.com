package nts.uk.ctx.pereg.app.find.processor;

import java.util.Set;

import nts.uk.shr.pereg.app.find.PeregFinder;

public interface PeregFinderProcessorCollector {
	
	Set<PeregFinder<?>> peregFinderCollect();
	
}
