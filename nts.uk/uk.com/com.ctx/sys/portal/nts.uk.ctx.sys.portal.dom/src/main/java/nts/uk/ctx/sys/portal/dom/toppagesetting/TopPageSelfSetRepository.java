package nts.uk.ctx.sys.portal.dom.toppagesetting;

import java.util.Optional;

/**
 * The Interface TopPageSelfSetRepository.
 */
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
 	 * find top page self set by code
 	 * @param employeeId
 	 * @param code
 	 * @return
 	 */
 	Optional<TopPageSelfSet> findTopPageSelfSetbyCode(String employeeId,String code);
 	/**
 	 * update top page self set
 	 * @param topPageSelfSet
 	 */
 	void updateTopPageSelfSet(TopPageSelfSet topPageSelfSet);
}

