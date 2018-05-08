package nts.uk.ctx.sys.assist.dom.storage;

import java.util.Optional;
import java.util.List;

/**
* データ保存の保存結果
*/
public interface ResultOfSavingRepository
{

    List<ResultOfSaving> getAllResultOfSaving();

    Optional<ResultOfSaving> getResultOfSavingById(String storeProcessingId);

}
