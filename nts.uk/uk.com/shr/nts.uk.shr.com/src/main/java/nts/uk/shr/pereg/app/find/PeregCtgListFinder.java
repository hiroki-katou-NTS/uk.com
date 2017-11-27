package nts.uk.shr.pereg.app.find;

import java.util.List;

import nts.uk.shr.pereg.app.find.dto.PeregDto;

public interface PeregCtgListFinder extends PeregFinder {

	List<PeregDto> getCtgListleData(PeregQuery query);

	default List<PeregDto> find(PeregQuery query) {
		return this.getCtgListleData(query);
	}
	
}
