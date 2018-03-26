package nts.uk.ctx.sys.portal.app.find.widget;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidgetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class OptionalWidgetFinder {
	@Inject
	private OptionalWidgetRepository repository;

	public List<OptionalWidgetDto> findAll() {
		String companyId = AppContexts.user().companyId();
		return this.repository.findByCompanyId(companyId).stream().map(x -> OptionalWidgetDto.fromDomain(x))
				.collect(Collectors.toList());
	}
}
