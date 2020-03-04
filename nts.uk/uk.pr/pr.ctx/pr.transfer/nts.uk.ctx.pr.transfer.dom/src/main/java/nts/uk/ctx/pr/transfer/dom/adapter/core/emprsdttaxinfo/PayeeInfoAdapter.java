package nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo;

import java.util.List;

public interface PayeeInfoAdapter {
	
    List<PayeeInfoImport> getListPayeeInfo(List<String> listHistId);
    
    void updateResidentTaxPayeeCode(String historyId, String code);
    
}
