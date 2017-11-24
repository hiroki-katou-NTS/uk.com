package nts.uk.shr.pereg.app.find;

import java.util.Set;

public interface PeregFinderProcessorCollector {
	Set<PeregCtgSingleFinder<?, ?>> peregCtgSingleFinderCollect();
	Set<PeregCtgListFinder<?, ?>> peregCtgListFinderCollect();
}
