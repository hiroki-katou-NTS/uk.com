package nts.uk.ctx.hr.notice.dom.report.valueImported.ctg;

import java.util.List;
import java.util.Optional;

public interface HumanCategoryPub {
	PerInfoCtgDataEnumImport getAllPerInfoCtgHumanByCompany();	
	
	List<PerInfoCtgShowImport> getInfoCtgByCtgIdsAndCid(String cid, List<String> ctgIds);
	
	Optional<PerInfoCtgShowImport> getCategoryByCidAndContractCd(String ctgId, String contractCd);
}
