package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;

@Stateless
public class DataRecoveryMngFinder {
	@Inject
    private DataRecoveryMngRepository finder;
	
	public DataRecoveryMngDto getDataRecoveryMngById(String recoveryProcessingId){
		Optional<DataRecoveryMng> dataRecoveryMngDto = finder.getDataRecoveryMngById(recoveryProcessingId);
		if (dataRecoveryMngDto.isPresent()){
			return DataRecoveryMngDto.fromDomain(dataRecoveryMngDto.get());
		}
		return null;
    }
}
