package nts.uk.shr.pereg.app.find;

public interface PeregCtgListFinder <R, Q> extends PeregFinder <R, Q>{

	R getCtgListleData(Q query);
	
	@SuppressWarnings("unchecked")
	default R handleCtgListProcessor(Object query) {
		return this.getCtgListleData((Q) query);
	}
}

