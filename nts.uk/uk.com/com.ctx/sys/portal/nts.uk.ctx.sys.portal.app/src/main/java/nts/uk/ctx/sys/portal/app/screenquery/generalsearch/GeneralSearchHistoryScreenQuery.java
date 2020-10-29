package nts.uk.ctx.sys.portal.app.screenquery.generalsearch;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.generalsearch.GeneralSearchRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class GeneralSearchHistoryFinder.
 */
@Stateless
public class GeneralSearchHistoryScreenQuery {

	/** The repo. */
	@Inject
	private GeneralSearchRepository repo;
	
	/**
	 * Gets the.
	 *
	 * @param searchCategory the search category
	 * @return the list
	 */
	public List<GeneralSearchHistoryDto> get(int searchCategory) {
		return this.repo.get(AppContexts.user().userId(), AppContexts.user().companyId(), searchCategory).stream()
				.map(item -> GeneralSearchHistoryDto.builder()
						.companyID(item.getCompanyID())
						.searchCategory(item.getSearchCategory().value)
						.searchDate(item.getSearchDate())
						.userID(item.getUserID())
						.contents(item.getContents().toString())
						.build())
				.collect(Collectors.toList());
	}
	
	/**
	 * Gets the last 10 used searches.
	 * 最近10使った検索を取得する
	 *
	 * @param searchCategory the search category
	 * @return the last 10 used searches
	 */
	public List<GeneralSearchHistoryDto> getLast10UsedSearches(int searchCategory) {
		return this.repo.getLast10UsedSearches(AppContexts.user().userId(), AppContexts.user().companyId(), searchCategory).stream()
				.map(item -> GeneralSearchHistoryDto.builder()
						.companyID(item.getCompanyID())
						.searchCategory(item.getSearchCategory().value)
						.searchDate(item.getSearchDate())
						.userID(item.getUserID())
						.contents(item.getContents().toString())
						.build())
				.collect(Collectors.toList());
	}
	

	/**
	 * Gets the by contents.
	 *
	 * @param searchCategory the search category
	 * @param searchContent the search content
	 * @return the by contents
	 */
	public List<GeneralSearchHistoryDto> getByContents(int searchCategory, String searchContent) {
		return this.repo.getByContents(AppContexts.user().userId(), AppContexts.user().companyId(), searchCategory, searchContent.trim()).stream()
				.map(item -> GeneralSearchHistoryDto.builder()
						.companyID(item.getCompanyID())
						.searchCategory(item.getSearchCategory().value)
						.searchDate(item.getSearchDate())
						.userID(item.getUserID())
						.contents(item.getContents().toString())
						.build())
				.collect(Collectors.toList());
	}
}
