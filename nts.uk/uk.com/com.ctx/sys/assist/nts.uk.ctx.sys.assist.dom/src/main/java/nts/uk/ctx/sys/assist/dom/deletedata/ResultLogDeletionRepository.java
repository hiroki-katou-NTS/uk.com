package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.List;
import java.util.Optional;

/**
* データ削除の結果ログ
*/
public interface ResultLogDeletionRepository
{

	int getMaxSeqId(String delId);
    List<ResultLogDeletion> getAllResultLogDeletion();

    Optional<ResultLogDeletion> getResultLogDeletionById(String delId);
    void add(ResultLogDeletion data);
}
