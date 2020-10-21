package nts.uk.ctx.sys.portal.app.screenquery.flowmenu;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenuRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG034_フローページの作成.A：フローメニューの作成.メニュー別OCD.フローメニュー作成を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetFlowMenuScreenQuery {

	@Inject
	private CreateFlowMenuRepository createFlowMenuRepository;
	
	public CreateFlowMenuDto getFlowMenu(String flowMenuCode) {
		return this.createFlowMenuRepository.findByPk(AppContexts.user().companyCode(), flowMenuCode)
				.map(domain -> {
					CreateFlowMenuDto dto = new CreateFlowMenuDto();
					domain.setMemento(dto, AppContexts.user().contractCode());
					return dto;
				}).orElse(null);
	}
}
