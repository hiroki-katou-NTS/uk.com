package nts.uk.screen.at.app.kdw013.query;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.ManHrInputDisplayFormatRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 */
@Stateless
public class GetManHrInputDisplayFormat {

	@Inject
	private ManHrInputDisplayFormatRepository repo;

	/***
	 * 表示フォーマットを取得する
	 */
	public ManHrInputDisplayFormatDto getManHrInputDisplayFormat() {
		return this.repo.get(AppContexts.user().companyId()).map(x -> new ManHrInputDisplayFormatDto(x)).orElse(null);
	}
}
