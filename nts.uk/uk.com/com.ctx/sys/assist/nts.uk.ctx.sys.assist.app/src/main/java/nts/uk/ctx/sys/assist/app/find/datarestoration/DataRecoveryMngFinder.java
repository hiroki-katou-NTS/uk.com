package nts.uk.ctx.sys.assist.app.find.datarestoration;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.find.datastoragemng.DataStorageMngDto;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;

@Stateless
public class DataRecoveryMngFinder {
	@Inject
    private DataRecoveryMngRepository finder;
	
	public DataRecoveryMngDto getDataRecoveryMngById(String recoveryProcessingId){
    	if(finder.getDataRecoveryMngById(recoveryProcessingId).isPresent()) {
    		return DataRecoveryMngDto.fromDomain(finder.getDataRecoveryMngById(recoveryProcessingId).get());
    	} else {
    		return null;
    	}
    }
}
