package nts.uk.ctx.sys.log.dom.datacorrectionlog;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogWriter;

@Stateless
public class DataCorrectionLogWriterImpl implements DataCorrectionLogWriter{

	@Inject
	private DataCorrectionLogRepository dataLogRepository;
	
	@Override
	public void save(List<DataCorrectionLog> dataCorrectionLog) {
		dataLogRepository.save(dataCorrectionLog);
	}

}
