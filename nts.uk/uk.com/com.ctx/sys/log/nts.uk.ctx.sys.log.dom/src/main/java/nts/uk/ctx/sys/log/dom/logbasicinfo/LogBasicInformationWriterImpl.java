package nts.uk.ctx.sys.log.dom.logbasicinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.processor.LogBasicInformationWriter;

@Stateless
public class LogBasicInformationWriterImpl implements LogBasicInformationWriter{

	@Inject
	private LogBasicInfoRepository logBasicInfoRepository;
	
	@Override
	public void save(LogBasicInformation basicInfo) {
		logBasicInfoRepository.save(basicInfo);
	}

}
