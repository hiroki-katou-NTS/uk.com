package nts.uk.ctx.at.record.dom.workrecord.log;

import java.util.List;

public interface ErrMessageInfoRepository {
	
	/**
	 * get all ErrMessageInfo by empCalAndSumExecLogID,executionContent
	 * @param empCalAndSumExecLogID
	 * @return
	 */
	List<ErrMessageInfo> getAllErrMessageInfoByID(String empCalAndSumExecLogID,int executionContent );
	/**
	 * get all ErrMessageInfo by empCalAndSumExecLogID
	 * @param empCalAndSumExecLogID
	 * @return
	 */
	List<ErrMessageInfo> getAllErrMessageInfoByEmpID(String empCalAndSumExecLogID);
	
	void add(ErrMessageInfo errMessageInfo);

}
