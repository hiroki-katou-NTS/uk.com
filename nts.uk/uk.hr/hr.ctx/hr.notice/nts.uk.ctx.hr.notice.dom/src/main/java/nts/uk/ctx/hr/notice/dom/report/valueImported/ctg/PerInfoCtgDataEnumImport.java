package nts.uk.ctx.hr.notice.dom.report.valueImported.ctg;

import java.util.List;

import lombok.Value;
import nts.arc.enums.EnumConstant;

@Value
public class PerInfoCtgDataEnumImport {
	private List<EnumConstant> historyTypes;
	private List<PerInfoCtgShowImport> categoryList;
}
