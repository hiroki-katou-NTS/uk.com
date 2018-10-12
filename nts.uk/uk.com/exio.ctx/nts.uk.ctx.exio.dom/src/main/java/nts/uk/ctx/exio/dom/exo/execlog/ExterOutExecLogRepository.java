package nts.uk.ctx.exio.dom.exo.execlog;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;

/**
 * 外部出力実行結果ログ
 */
public interface ExterOutExecLogRepository {

	List<ExterOutExecLog> getAllExterOutExecLog();

	Optional<ExterOutExecLog> getExterOutExecLogById(String cid, String outProcessId);

	void add(ExterOutExecLog domain);

	void update(ExterOutExecLog domain);

	void remove(String cid, String outProcessId);
	
	void update(String cid, String outProcessId, long fileSize);

	List<ExterOutExecLog> searchExterOutExecLogGeneral(String cid, GeneralDateTime startDate, GeneralDateTime endDate, String userId,
			Optional<String> condSetCd);
	
	List<ExterOutExecLog> searchExterOutExecLogInchage(String cid, GeneralDateTime startDate, GeneralDateTime endDate, String userId,
			Optional<String> condSetCd, List<Integer> exOutCtgIdList);
}