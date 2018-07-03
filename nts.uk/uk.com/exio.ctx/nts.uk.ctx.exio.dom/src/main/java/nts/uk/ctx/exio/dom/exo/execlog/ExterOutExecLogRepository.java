package nts.uk.ctx.exio.dom.exo.execlog;

import java.util.List;
import java.util.Optional;

/**
 * 外部出力実行結果ログ
 */
public interface ExterOutExecLogRepository {

	List<ExterOutExecLog> getAllExterOutExecLog();

	Optional<ExterOutExecLog> getExterOutExecLogById(String cid, String outProcessId);

	void add(ExterOutExecLog domain);

	void update(ExterOutExecLog domain);

	void remove(String cid, String outProcessId);

}