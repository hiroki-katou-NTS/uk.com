package nts.uk.ctx.at.function.app.find.indexreconstruction;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.indexreconstruction.repository.IndexReorgCatRepository;

/**
 * The class Index reorganization category finder.<br>
 * インデックス再構成カテゴリ
 * 
 * @author nws-minhnb
 */
@Stateless
public class IndexReorgCateFinder {

	/**
	 * The Index reorganization category repository.
	 */
	@Inject
	private IndexReorgCatRepository indexReorgCatRepo;

	/**
	 * Gets all index reorganization categories.
	 * 
	 * @return the <code>IndexReorgCateDto</code> list
	 */
	public List<IndexReorgCateDto> getAllIndexReorgCates() {
		return this.indexReorgCatRepo.findAll()
									 .stream()
									 .map(IndexReorgCateDto::createFromDomain)
									 .collect(Collectors.toList());
	}

}
