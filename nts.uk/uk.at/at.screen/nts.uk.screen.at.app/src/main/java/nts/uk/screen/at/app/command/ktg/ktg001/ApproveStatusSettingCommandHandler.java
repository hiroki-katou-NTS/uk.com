package nts.uk.screen.at.app.command.ktg.ktg001;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApproveStatusSettingCommandHandler {

	@Inject
	private ApproveWidgetRepository approveWidgetRepository;

	public void updateSetting(KTG001SettingParam param) {

		String companyId = AppContexts.user().companyId();
		StandardWidget standardWidget = new StandardWidget(companyId, "", null, null, null, null);

		standardWidget.setName(param.topPagePartName);
		standardWidget.setApprovedAppStatusDetailedSettingList(param.getApprovedAppStatusDetailedSettings());

		approveWidgetRepository.updateApproveStatus(standardWidget, companyId);
	}
}