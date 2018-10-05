package nts.uk.ctx.exio.dom.exo.execlog;

import java.util.List;

/**
 * 外部出力結果ログ
 */
public interface ExternalOutLogRepository {

	List<ExternalOutLog> getAllExternalOutLog();

	List<ExternalOutLog> getExternalOutLogById(String cid, String outProcessId, int processingClassification);

	void add(ExternalOutLog domain);

	void update(ExternalOutLog domain);

}
