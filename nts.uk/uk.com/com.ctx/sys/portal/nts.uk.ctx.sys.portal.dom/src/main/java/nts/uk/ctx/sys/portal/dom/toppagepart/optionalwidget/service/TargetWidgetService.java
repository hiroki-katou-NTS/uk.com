package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.enums.UseDivision;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;
import nts.uk.ctx.sys.portal.dom.mypage.setting.TopPagePartUseSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.service.TopPagePartService;

@Stateless
public class TargetWidgetService implements WidgetService{

	@Inject
	private OptionalWidgetRepository widgetRepository;
	
	@Inject
	private TopPagePartRepository topPagePartRepository;
	
	@Inject
	private TopPagePartService topPagePartService;
	
	@Inject
	private MyPageSettingRepository	myPageSettingRepository;
	
	@Override
	public boolean isExist(String companyID, String toppagePartID) {
		Optional<OptionalWidget> widget = widgetRepository.findByCode(companyID, toppagePartID);
		return widget.isPresent();
	}

	@Override
	public void deleteWidget(String companyID, String toppagePartID) {
		if (isExist(companyID, toppagePartID)) {
			topPagePartRepository.remove(companyID, toppagePartID);
			topPagePartService.deleteTopPagePart(companyID, toppagePartID);
			
		}
		
	}

	@Override
	public void addWidget(OptionalWidget widget) {
		if (topPagePartService.isExist(widget.getCompanyID(), widget.getCode().v(), TopPagePartType.OptionalWidget.value)) {
			throw new BusinessException("Msg_3");
		}
		widgetRepository.add(widget);
		topPagePartRepository.add(widget);
		// TopPagePart Setting
		TopPagePartUseSetting topPagePartUseSetting = TopPagePartUseSetting.createFromJavaType(
				widget.getCompanyID(), widget.getToppagePartID(),
				widget.getCode().v(), widget.getName().v(),
				UseDivision.Use.value, TopPagePartType.OptionalWidget.value);
		myPageSettingRepository.addTopPagePartUseSetting(topPagePartUseSetting);
		
	}

	@Override
	public void updateWidget(OptionalWidget widget) {
		widgetRepository.update(widget);
		topPagePartRepository.update(widget);
		
	}

}
