package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.service;

import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;

public interface WidgetService {

	boolean isExist(String companyID, String toppagePartID);


	void addWidget(OptionalWidget widget);


	void updateWidget(OptionalWidget widget);

	
	void deleteWidget(String companyID, String toppagePartID);

	
}