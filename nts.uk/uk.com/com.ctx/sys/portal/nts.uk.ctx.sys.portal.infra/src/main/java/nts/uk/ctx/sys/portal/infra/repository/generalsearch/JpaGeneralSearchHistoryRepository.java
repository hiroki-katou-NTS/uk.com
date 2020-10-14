package nts.uk.ctx.sys.portal.infra.repository.generalsearch;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.generalsearch.GeneralSearchHistory;
import nts.uk.ctx.sys.portal.dom.generalsearch.GeneralSearchRepository;
import nts.uk.ctx.sys.portal.infra.entity.generalsearch.SptdtGenericSearchHist;

/**
 * The Class JpaGeneralSearchHistoryRepository.
 */
@Stateless
public class JpaGeneralSearchHistoryRepository extends JpaRepository implements GeneralSearchRepository {

	/** The Constant QUERY_SELECT_ALL. */
	private static final String QUERY_SELECT_ALL = "SELECT f FROM SptdtGenericSearchHist f";
	
	/** The Constant QUERY_SELECT_LIST_ALL. */
	private static final String QUERY_SELECT_LIST_ALL = QUERY_SELECT_ALL
			+ " WHERE f.pk.companyID = :companyID"
			+ " AND f.pk.userID = :userID"
			+ " AND f.pk.searchCategory = :searchCategory"
			+ " ORDER BY f.searchDate ASC";
	
	/** The Constant QUERY_SELECT_LAST_10_RESULTS. */
	private static final String QUERY_SELECT_LAST_10_RESULTS = QUERY_SELECT_ALL
			+ " WHERE f.pk.companyID = :companyID"
			+ " AND f.pk.userID = :userID"
			+ " AND f.pk.searchCategory = :searchCategory"
			+ " ORDER BY f.searchDate ASC"
			+ "LIMIT 10";
	
	/** The Constant QUERY_SELECT_BY_CONTENT. */
	private static final String QUERY_SELECT_BY_CONTENT = QUERY_SELECT_ALL
			+ " WHERE f.pk.companyID = :companyID"
			+ " AND f.pk.userID = :userID"
			+ " AND f.pk.searchCategory = :searchCategory"
			+ " AND f.contents = :contents"
			+ " ORDER BY f.searchDate ASC";
	
	/**
	 * Insert.
	 *
	 * @param domain the domain
	 */
	@Override
	public void insert(GeneralSearchHistory domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the object
	 */
	private Object toEntity(GeneralSearchHistory domain) {
		SptdtGenericSearchHist entity = new SptdtGenericSearchHist();
		domain.setMemento(entity);
		return entity;
	}

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	@Override
	public void update(GeneralSearchHistory domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/**
	 * Delete.
	 *
	 * @param domain the domain
	 */
	@Override
	public void delete(GeneralSearchHistory domain) {
		this.commandProxy().remove(this.toEntity(domain));
	}

	/**
	 * Gets the.
	 * [4] 取得する
	 * @param userID the user ID
	 * @param companyID the company ID
	 * @param searchCategory the search category
	 * @return the list
	 */
	@Override
	public List<GeneralSearchHistory> get(String userID, String companyID, int searchCategory) {
		return this.queryProxy()
				.query(QUERY_SELECT_LIST_ALL, SptdtGenericSearchHist.class)
				.setParameter("companyID", companyID)
				.setParameter("userID", userID)
				.setParameter("searchCategory", searchCategory)
				.getList(GeneralSearchHistory::createFromMemento);
	}

	/**
	 * Gets the last 10 used searches.
	 * [5]最近１０使った検索を 取得する
	 * @param userID the user ID
	 * @param companyID the company ID
	 * @param searchCategory the search category
	 * @return the last 10 used searches
	 */
	@Override
	public List<GeneralSearchHistory> getLast10UsedSearches(String userID, String companyID,
			int searchCategory) {
		return this.queryProxy()
				.query(QUERY_SELECT_LAST_10_RESULTS, SptdtGenericSearchHist.class)
				.setParameter("companyID", companyID)
				.setParameter("userID", userID)
				.setParameter("searchCategory", searchCategory)
				.getList(GeneralSearchHistory::createFromMemento);
	}

	/**
	 * Gets the by contents.
	 * [6] 内容で取得する
	 * @param userID the user ID
	 * @param companyID the company ID
	 * @param searchCategory the search category
	 * @param searchContent the search content
	 * @return the by contents
	 */
	@Override
	public List<GeneralSearchHistory> getByContents(String userID, String companyID, int searchCategory,
			String searchContent) {
		return this.queryProxy()
				.query(QUERY_SELECT_BY_CONTENT, SptdtGenericSearchHist.class)
				.setParameter("companyID", companyID)
				.setParameter("userID", userID)
				.setParameter("searchCategory", searchCategory)
				.setParameter("contents", searchContent)
				.getList(GeneralSearchHistory::createFromMemento);
	}

}
