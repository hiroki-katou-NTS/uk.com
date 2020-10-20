package nts.uk.ctx.at.record.app.find.optitem.language;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNameOtherRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class OptionalItemNameOtherFinder {
	
	@Inject
	private OptionalItemNameOtherRepository itemNameRepository;
	
	public List<OptionalItemNameOther> findAllNameLangguage(String langId) {
		return itemNameRepository.findAll(AppContexts.user().companyId(), langId).stream()
				.map(x -> new OptionalItemNameOther(x.getCompanyId().v(), x.getOptionalItemNo().v(), x.getLangId(),
						x.getOptionalItemName().v()))
				.collect(Collectors.toList());
	}

}
