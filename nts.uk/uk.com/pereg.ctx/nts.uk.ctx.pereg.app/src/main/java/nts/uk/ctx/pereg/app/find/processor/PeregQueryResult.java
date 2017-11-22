package nts.uk.ctx.pereg.app.find.processor;

import java.util.List;

import find.person.info.item.PerInfoItemDefForLayoutDto;
import lombok.Data;

@Data
public class PeregQueryResult {
	private List<PerInfoItemDefForLayoutDto> lstPerInfoItemDef;
	/**
	 * Object data by each domain
	 */
	private Object dto;
}
