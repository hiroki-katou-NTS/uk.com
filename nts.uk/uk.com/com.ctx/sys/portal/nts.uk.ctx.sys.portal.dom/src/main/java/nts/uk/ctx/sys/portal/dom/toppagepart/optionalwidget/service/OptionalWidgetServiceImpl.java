package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.service;

import java.util.Collections;
import java.util.List;

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
	public List<OptionalWidget> getSelectedWidget(String companyId, String topPagePartCode, int topPagePartType) {
		// ドメインモデル「選択ウィジェット」を取得する
		List<OptionalWidget> data = optionalWidgetRepository.getSelectedWidget(companyId, topPagePartCode, topPagePartType);
		
		if(data != null && data.size() > 0) {
			return data;
		}
		
		return Collections.emptyList();
	}

}
