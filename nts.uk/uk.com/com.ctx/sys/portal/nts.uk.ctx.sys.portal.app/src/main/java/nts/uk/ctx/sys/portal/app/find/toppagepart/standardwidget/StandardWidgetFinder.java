package nts.uk.ctx.sys.portal.app.find.toppagepart.standardwidget;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetRepository;
@Stateless
public class StandardWidgetFinder {

	@Inject
	private StandardWidgetRepository standardRepo;

	public StandardWidgetDto getStanddardWidget(String toppagePartID) {
		Optional<StandardWidget> standardWidget =standardRepo.getByID(toppagePartID);
		return StandardWidgetDto.fromDomain(standardWidget.get());
	}
}
