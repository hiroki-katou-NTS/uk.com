package nts.uk.ctx.sys.portal.dom.toppagesetting;

import java.util.Optional;

import nts.uk.ctx.sys.portal.dom.layout.Layout;

/**
 * 
 * @author hoatt
 *
 */
public interface TopPageSelfSetRepository {
	 
	/**
	 * Add the Top Page Self Setting.
	 *
	 * @param topPageSelfSet the TopPageSelfSet
	 */
	 void addTopPageSelfSet(TopPageSelfSet topPageSelfSet);
 	/**
 	 * get top page self set
 	 * @param employeeId
 	 * @return
 	 */
 	Optional<TopPageSelfSet> getTopPageSelfSet(String employeeId);
 	/**
 	 * update top page self set
 	 * @param topPageSelfSet
 	 */
 	void updateTopPageSelfSet(TopPageSelfSet topPageSelfSet);
	/**
	 * Find a Layout
	 *
	 * @param layoutID
	 * @return Optional Layout
	 */
	Optional<Layout> find(String layoutID, int pgType);
}

