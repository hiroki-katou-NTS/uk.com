package nts.uk.ctx.at.record.app.find.dailyperform.midnight;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.actualworkinghours.daily.midnight.MidnightTimeSheetRepo;
import nts.uk.ctx.at.record.dom.daily.midnight.MidNightTimeSheet;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author yennh
 *
 */
@Stateless
public class MidnightTimeSheetFinder {
	@Inject
	private MidnightTimeSheetRepo repo;
	
	/**
	 * Find all MidNightTimeSheet
	 * 
	 * @return
	 */
	public List<MidNightTimeSheet> findAllMidnightTimeSheet() {
		String companyId = AppContexts.user().companyId();
		return repo.findByCompanyId(companyId);
	}
}
