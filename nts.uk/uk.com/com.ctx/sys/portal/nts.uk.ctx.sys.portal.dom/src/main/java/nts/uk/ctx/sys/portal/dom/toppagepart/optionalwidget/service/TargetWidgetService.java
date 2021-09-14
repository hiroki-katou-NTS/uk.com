package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.enums.UseDivision;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;

@Stateless
public class TargetWidgetService implements WidgetService{

	@Inject
	private MyPageSettingRepository	myPageSettingRepository;
	
	@Override
	public boolean isExist(String companyID, String toppagePartID) {
		return false;
	}

	@Override
	public void deleteWidget(String companyID, String toppagePartID) {
		// Do nothing
	}

	@Override
	public void addWidget(OptionalWidget widget) {
		TopPagePartUseSetting topPagePartUseSetting = TopPagePartUseSetting.createFromJavaType(
				widget.getCompanyID(), "",
				"", "",
				UseDivision.Use.value, TopPagePartType.OptionalWidget.value);
		myPageSettingRepository.addTopPagePartUseSetting(topPagePartUseSetting);
		
	}

	@Override
	public void updateWidget(OptionalWidget widget) {
		// Do nothing
	}

}
