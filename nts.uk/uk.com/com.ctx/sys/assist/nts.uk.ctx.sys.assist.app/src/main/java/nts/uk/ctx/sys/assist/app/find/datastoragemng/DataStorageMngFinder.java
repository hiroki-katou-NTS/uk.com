package nts.uk.ctx.sys.assist.app.find.datastoragemng;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.storage.DataStorageMng;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMngRepository;

@Stateless
/**
* データ保存動作管理
*/
public class DataStorageMngFinder
{

    @Inject
    private DataStorageMngRepository finder;

    public List<DataStorageMngDto> getAllDataStorageMng(){
        return finder.getAllDataStorageMng().stream().map(item -> DataStorageMngDto.fromDomain(item))
                .collect(Collectors.toList());
    }
    
    public DataStorageMngDto getDataStorageMngById(String storeProcessingId){
    	Optional<DataStorageMng> data = finder.getDataStorageMngById(storeProcessingId); 
    	if(data.isPresent()) {
    		return DataStorageMngDto.fromDomain(data.get());
    	} else {
    		return null;
    	}
    }
}
