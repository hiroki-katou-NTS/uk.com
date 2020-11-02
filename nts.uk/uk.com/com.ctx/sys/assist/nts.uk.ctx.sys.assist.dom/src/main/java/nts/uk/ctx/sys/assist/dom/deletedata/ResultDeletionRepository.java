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
    List<ResultDeletion> getByStartDatetimeDel(GeneralDateTime from, GeneralDateTime to);
    List<ResultDeletion> getByListCodes(List<String> delCodes);
    Optional<ResultDeletion> getResultDeletionById(String delId);
    void add(ResultDeletion data);
    /**
	 * 
	 * @param data
	 */
	void update(ResultDeletion data);
	void update(String fileId);
	void update(ResultDeletion resultDel, ManualSetDeletion manualSetDel);
}
