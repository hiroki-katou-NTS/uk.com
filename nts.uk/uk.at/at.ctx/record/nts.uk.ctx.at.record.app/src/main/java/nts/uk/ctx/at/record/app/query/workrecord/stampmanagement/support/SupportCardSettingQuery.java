package nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardEditRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Query: 初期表示を行う
 *
 * @author : NWS_namnv
 */
@Stateless
public class SupportCardSettingQuery {
	
	@Inject
	private SupportCardEditRepository supportCardEditRepository;
	
	/**
	 * Gets the support card setting.
	 *
	 * @return the support card setting
	 */
	public SupportCardSettingDto getSupportCardSetting() {
		String cid = AppContexts.user().companyId();
		return this.supportCardEditRepository.get(cid)
				.map(t -> new SupportCardSettingDto(t.getEditMethod().value))
				.orElse(null);
	}

}
