package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidgetRepository;

/**
 * 
 * @author tanlv
 *
 */
@Stateless
public class OptionalWidgetServiceImpl implements OptionalWidgetService {

	@Inject
	private OptionalWidgetRepository optionalWidgetRepository;
	
	@Override
	public Optional<OptionalWidget> getSelectedWidget(String companyId, String topPagePartCode) {
		// ドメインモデル「選択ウィジェット」を取得する
		Optional<OptionalWidget> data = optionalWidgetRepository.getSelectedWidget(companyId, topPagePartCode);
		return data;
	}

}
