package nts.uk.shr.pereg.app.find;

import nts.uk.shr.pereg.app.find.dto.PeregDto;

public interface PeregSingleFinder<T> extends PeregFinder<T> {

	PeregDto getSingleData(PeregQuery query);

	default PeregDto find(PeregQuery query) {
		return this.getSingleData(query);
	}

}
