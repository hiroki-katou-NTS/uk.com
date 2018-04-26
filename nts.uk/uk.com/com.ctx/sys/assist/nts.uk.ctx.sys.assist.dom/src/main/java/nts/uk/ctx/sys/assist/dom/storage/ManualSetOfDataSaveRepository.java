package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;
import java.util.List;

/**
* データ保存の手動設定
*/
public interface ManualSetOfDataSaveRepository
{

    List<ManualSetOfDataSave> getAllManualSetOfDataSave();

    Optional<ManualSetOfDataSave> getManualSetOfDataSaveById(String cid, String storeProcessingId);

}
