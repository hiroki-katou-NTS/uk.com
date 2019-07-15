package nts.uk.ctx.pereg.dom.person.setting.validatecheck;

import java.util.List;

public interface PerInfoValidChkCtgRepository {
	List<PerInfoValidateCheckCategory> getListPerInfoValidByListCtgId(List<String> listCategoryCd, String contracCd);
	
	List<PerInfoValidateCheckCategory> getListPerInfoValid();
}
