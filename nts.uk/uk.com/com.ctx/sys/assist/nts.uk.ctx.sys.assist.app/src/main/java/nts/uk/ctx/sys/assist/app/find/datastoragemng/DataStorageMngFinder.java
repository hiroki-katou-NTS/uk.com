package nts.uk.ctx.sys.assist.app.find.datastoragemng;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.app.find.params.LogDataParams;
import nts.uk.ctx.sys.assist.app.find.resultofsaving.ResultOfSavingDto;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMngRepository;
import nts.uk.shr.com.context.AppContexts;

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
    	if(finder.getDataStorageMngById(storeProcessingId).isPresent()) {
    		return DataStorageMngDto.fromDomain(finder.getDataStorageMngById(storeProcessingId).get());
    	} else {
    		return null;
    	}
    }
}
