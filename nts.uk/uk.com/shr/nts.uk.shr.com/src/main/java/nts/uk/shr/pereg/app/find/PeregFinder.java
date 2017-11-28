package nts.uk.shr.pereg.app.find;

import java.util.List;

import nts.uk.shr.pereg.app.find.dto.PeregDto;

public interface PeregFinder<T> {
	/**
	 * Returns ID of category that this handler can handle
	 * 
	 * @return category ID
	 */
	String targetCategoryCode();

	/**
	 * Returns class of command that is handled by this handler
	 * 
	 * @return class of command
	 */
	Class<T> dtoClass();

	PeregDto getSingleData(PeregQuery query);

	List<PeregDto> getListData(PeregQuery query);

	default PeregDto findSingle(PeregQuery query) {
		return this.getSingleData(query);
	}

	default List<PeregDto> findList(PeregQuery query) {
		return this.getListData(query);
	}

}
