package nts.uk.ctx.sys.portal.app.find.toppagepart.standardwidget;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class StandardWidgetFinder {

	@Inject
	private StandardWidgetRepository standardRepo;

	public StandardWidgetDto getStanddardWidget(String toppagePartID) {
		String companyID = AppContexts.user().companyId();
		Optional<StandardWidget> standardWidget =standardRepo.getByID(toppagePartID, companyID);
		return StandardWidgetDto.fromDomain(standardWidget.get());
	}
}
