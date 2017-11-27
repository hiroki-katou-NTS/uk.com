package nts.uk.shr.pereg.app.find;

import nts.uk.shr.pereg.app.find.dto.PeregDto;

public interface PeregCtgSingleFinder extends PeregFinder {

	PeregDto getCtgSingleData(PeregQuery query);

	default PeregDto find(PeregQuery query) {
		return this.getCtgSingleData(query);
	}

}
