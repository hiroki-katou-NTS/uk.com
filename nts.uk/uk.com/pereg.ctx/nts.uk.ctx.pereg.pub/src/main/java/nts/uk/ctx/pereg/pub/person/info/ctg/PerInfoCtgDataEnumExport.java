package nts.uk.ctx.pereg.pub.person.info.ctg;

import java.util.List;

import lombok.Value;
import nts.arc.enums.EnumConstant;

@Value
public class PerInfoCtgDataEnumExport {
	private List<EnumConstant> historyTypes;
	private List<PerInfoCtgShowExport> categoryList;
}
