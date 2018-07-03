package nts.uk.ctx.exio.dom.exo.execlog;

import java.util.List;
import java.util.Optional;

/**
 * 外部出力結果ログ
 */
public interface ExternalOutLogRepository {

	List<ExternalOutLog> getAllExternalOutLog();

	Optional<ExternalOutLog> getExternalOutLogById(String cid, String outProcessId);

	void add(ExternalOutLog domain);

	void update(ExternalOutLog domain);

	void remove(String cid, String outProcessId);

}
