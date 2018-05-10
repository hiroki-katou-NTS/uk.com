package nts.uk.ctx.at.function.ac.widgetKtg;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetAdapter;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.WidgetDisplayItemImport;
import nts.uk.ctx.at.request.pub.application.recognition.HolidayInstructPub;
import nts.uk.ctx.at.request.pub.application.recognition.OverTimeInstructPub;
import nts.uk.ctx.sys.portal.pub.toppagepart.optionalwidget.OptionalWidgetExport;
import nts.uk.ctx.sys.portal.pub.toppagepart.optionalwidget.OptionalWidgetPub;

@Stateless
public class OptionalWidgetImplementFinder implements OptionalWidgetAdapter {

	@Inject
	OverTimeInstructPub overTimeInstructPub;
	
	@Inject 
	HolidayInstructPub holidayInstructPub;
	
	@Inject
	private OptionalWidgetPub optionalWidgetPub;
	
	
	@Override
	public int getNumberOT(String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return overTimeInstructPub.acquireOverTimeWorkInstruction(employeeId, startDate, endDate).size();
	}

	@Override
	public int getNumberBreakIndication(String employeeId, GeneralDate startDate, GeneralDate endDate) {
		return holidayInstructPub.acquireBreakIndication(employeeId, startDate, endDate).size();
	}

	@Override
	public Optional<OptionalWidgetImport> getSelectedWidget(String companyId, String topPagePartCode) {
		Optional<OptionalWidgetExport> optionalWidgetExport = optionalWidgetPub.getSelectedWidget(companyId,
				topPagePartCode);
		if (!optionalWidgetExport.isPresent())
			return Optional.empty();

		List<WidgetDisplayItemImport> widgetDisplayItemImport = optionalWidgetExport.get().getWidgetDisplayItemExport()
				.stream().map(c -> new WidgetDisplayItemImport(c.getDisplayItemType(), c.getNotUseAtr()))
				.collect(Collectors.toList());

		Optional<OptionalWidgetImport> optionalWidgetImport = Optional.ofNullable(new OptionalWidgetImport(
				optionalWidgetExport.get().getTopPagePartID(), optionalWidgetExport.get().getTopPageCode(),
				optionalWidgetExport.get().getTopPageName(), optionalWidgetExport.get().getWidth(),
				optionalWidgetExport.get().getHeight(), widgetDisplayItemImport));
		return optionalWidgetImport;
	}

	


}
