package nts.uk.ctx.pereg.app.find.processor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;
@Getter
@AllArgsConstructor
public class PeregMatrixByEmp {
	private List<ItemValue> items;
	private PeregEmpInfoQuery query;
	private PeregDto dto;
}
