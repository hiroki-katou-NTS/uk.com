package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;

/**
* データ保存の保存結果
*/
public interface ResultOfSavingRepository
{

    List<ResultOfSaving> getAllResultOfSaving();

    List<ResultOfSavingDto> getResultOfSavingById(String storeProcessingId);

}
