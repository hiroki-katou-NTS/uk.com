package nts.uk.ctx.sys.assist.dom.deletedata.result;

import java.util.List;
import java.util.Optional;

/**
* データ削除の保存結果
*/
public interface ResultDeletionRepository
{

    List<ResultDeletion> getAllResultDeletion();

    Optional<ResultDeletion> getResultDeletionById(String delId);
    void add(ResultDeletion data);
}
