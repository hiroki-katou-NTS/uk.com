package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;
import java.util.List;

/**
* データ保存動作管理
*/
public interface DataStorageMngRepository
{

    List<DataStorageMng> getAllDataStorageMng();

    Optional<DataStorageMng> getDataStorageMngById(String storeProcessingId);

}
