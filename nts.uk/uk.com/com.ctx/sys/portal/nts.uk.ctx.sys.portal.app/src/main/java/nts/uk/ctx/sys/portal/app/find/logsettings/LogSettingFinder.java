package nts.uk.ctx.sys.portal.app.find.logsettings;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.logsettings.LogSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LogSettingFinder {

	@Inject
	private LogSettingRepository logSettingRepository;

	/**
	 * find all LogSetting by companyId and System type
	 * 
	 * @param companyId
	 * @param SystemType
	 * @return List
	 */
	public List<LogSettingDto> findBySystem(int systemType) {
		String companyId = AppContexts.user().companyId();
		return this.logSettingRepository.findBySystem(companyId, systemType).stream()
				.map(c -> LogSettingDto.fromDomain(c)).collect(Collectors.toList());
	}
}
