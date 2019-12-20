package nts.uk.ctx.hr.notice.dom.report.valueImported.ctg;

import java.util.List;

public interface HumanCategoryPub {
	PerInfoCtgDataEnumImport getAllPerInfoCtgHumanByCompany();	
	
	List<PerInfoCtgShowImport> getInfoCtgByCtgIdsAndCid(String cid, List<String> ctgIds);
}
