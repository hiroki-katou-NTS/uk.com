package nts.uk.ctx.at.shared.ac.era.name;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.era.name.EraNameDom;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomRepository;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseEras;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;

/**
 * The Class JapaneseErasAdapterImpl.
 */
@Stateless
public class JapaneseErasAdapterImpl implements JapaneseErasAdapter {

	@Inject
	private EraNameDomRepository repo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.shr.com.time.japanese.JapaneseErasAdapter#getAllEras()
	 */
	@Override
	public JapaneseEras getAllEras() {
		List<EraNameDom> eraDomList = this.repo.getAllEraName();

		// check if empty
		if (eraDomList.isEmpty()) {
			return new JapaneseEras(Collections.emptyList());
		}

		List<JapaneseEraName> jpEraNames = eraDomList.stream().map(e -> new JapaneseEraName(e.getEraName().toString(),
				e.getSymbol().toString(), e.getStartDate(), e.getEndDate())).collect(Collectors.toList());

		return new JapaneseEras(jpEraNames);
	}

}
