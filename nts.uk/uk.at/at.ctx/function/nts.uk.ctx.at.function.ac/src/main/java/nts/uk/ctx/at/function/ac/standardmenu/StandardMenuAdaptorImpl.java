package nts.uk.ctx.at.function.ac.standardmenu;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuAdaptor;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuNameImport;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuNameQueryImport;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuNameExport;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuNameQuery;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuPub;

@Stateless
public class StandardMenuAdaptorImpl implements StandardMenuAdaptor {

	@Inject
	private StandardMenuPub standardMenuPub;

	@Override
	public List<StandardMenuNameImport> getMenuDisplayName(String companyId, List<StandardMenuNameQueryImport> query) {
		return this.standardMenuPub
				.getMenuDisplayName(companyId,
						query.stream().map(x -> toStandardMenuNameQuery(x)).collect(Collectors.toList()))
				.stream().map(x -> toStandardMenuNameImport(x)).collect(Collectors.toList());
	}

	private StandardMenuNameImport toStandardMenuNameImport(StandardMenuNameExport export) {
		return new StandardMenuNameImport(export.getProgramId(), export.getScreenId(), export.getQueryString(),
				export.getDisplayName());
	}

	private StandardMenuNameQuery toStandardMenuNameQuery(StandardMenuNameQueryImport query) {
		return new StandardMenuNameQuery(query.getProgramId(), query.getScreenId(), query.getQueryString());
	}
}
