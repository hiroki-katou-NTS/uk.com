package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.List;
import java.util.Optional;
import nts.arc.time.GeneralDateTime;

/**
* データ削除の保存結果
*/
public interface ResultDeletionRepository
{

    List<ResultDeletion> getAllResultDeletion();

    List<ResultDeletion> getResultOfDeletion(
   		 String cid,
   		 GeneralDateTime startDateOperator,
   		 GeneralDateTime endDateOperator,
   		 List<String>  listOperatorEmployeeId
   	);
    Optional<ResultDeletion> getResultDeletionById(String delId);
    void add(ResultDeletion data);
    /**
	 * 
	 * @param data
	 */
	void update(ResultDeletion data);
	
	void update(ResultDeletion resultDel, ManualSetDeletion manualSetDel);
}
