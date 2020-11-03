package nts.uk.screen.at.app.command.ktg.ktg001;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApplicationStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedAppStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedApplicationStatusItem;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.DetailedWorkStatusSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class ApproveStatusSettingCommandHandler {

	@Inject
	private ApproveWidgetRepository approveWidgetRepository;

	public void updateSetting(ApproveStatusSettingCommand param) {

		String companyId = AppContexts.user().companyId();
		
		List<ApprovedAppStatusDetailedSetting>settings = param.getApprovedAppStatusDetailedSettings().stream()
				.map(m-> new ApprovedAppStatusDetailedSetting(EnumAdaptor.valueOf(m.getDisplayType(), NotUseAtr.class), 
						EnumAdaptor.valueOf(m.getItem(), ApprovedApplicationStatusItem.class))).collect(Collectors.toList());
		
		StandardWidget standardWidget = new StandardWidget(companyId, "", null, new TopPagePartName(param.topPagePartName), null, null, new ArrayList<DetailedWorkStatusSetting>(), settings, StandardWidgetType.APPROVE_STATUS, new ArrayList<ApplicationStatusDetailedSetting>() );
		
		approveWidgetRepository.updateApproveStatus(standardWidget, companyId);
	}
}