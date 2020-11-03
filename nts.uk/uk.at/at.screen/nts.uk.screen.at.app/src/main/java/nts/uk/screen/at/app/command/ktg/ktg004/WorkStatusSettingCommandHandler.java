package nts.uk.screen.at.app.command.ktg.ktg004;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.DetailedWorkStatusSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.WorkStatusItem;
import nts.uk.screen.at.app.command.ktg.ktg001.ApproveStatusSettingCommand;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class WorkStatusSettingCommandHandler {

	@Inject
	private ApproveWidgetRepository approveWidgetRepository;

	public void updateSetting(ApproveStatusSettingCommand param) {

		String companyId = AppContexts.user().companyId();
		
		List<DetailedWorkStatusSetting> settings = param.getApprovedAppStatusDetailedSettings().stream().map(m -> 
																new DetailedWorkStatusSetting(EnumAdaptor.valueOf(m.getDisplayType(), NotUseAtr.class), 
																EnumAdaptor.valueOf(m.getItem(), WorkStatusItem.class))
															).collect(Collectors.toList());
		
		StandardWidget standardWidget = new StandardWidget(
												companyId, 
												"", 
												null, 
												new TopPagePartName(param.getTopPagePartName()), 
												null, 
												null, 
												settings, 
												new ArrayList<>(), 
												StandardWidgetType.WORK_STATUS, 
												new ArrayList<>());
		
		approveWidgetRepository.saveWorkStatus(standardWidget);
	}
}