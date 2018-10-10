package nts.uk.ctx.exio.app.find.exo.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.category.ExOutCtgRepository;

@Stateless
/**
 * 外部出力カテゴリ
 */
public class ExOutCtgFinder {

	@Inject
	private ExOutCtgRepository finder;

	public List<ExOutCtgDto> getAllExOutCtg() {
		return finder.getAllExOutCtg().stream().map(item -> ExOutCtgDto.fromDomain(item)).collect(Collectors.toList());
	}

}
