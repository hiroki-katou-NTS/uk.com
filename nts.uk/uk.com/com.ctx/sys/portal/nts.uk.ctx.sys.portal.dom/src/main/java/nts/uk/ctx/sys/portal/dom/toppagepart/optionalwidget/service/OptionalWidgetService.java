package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.service;

import java.util.List;

import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;

/**
 * 
 * @author tanlv
 *
 */
public interface OptionalWidgetService {
	/**
	 * RequestList365
	 * 選択ウィジェットを取得する
	 * @param companyId
	 * @param topPagePartCode
	 * @param TopPagePartType
	 * @return
	 */
	List<OptionalWidget> getSelectedWidget(String companyId, String topPagePartCode, int topPagePartType);
}
