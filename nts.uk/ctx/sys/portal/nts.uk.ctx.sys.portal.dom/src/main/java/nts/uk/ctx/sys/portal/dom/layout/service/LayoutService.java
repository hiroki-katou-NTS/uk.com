package nts.uk.ctx.sys.portal.dom.layout.service;

/**
 * @author LamDT
 */
public interface LayoutService {

	/**
	 * Check Layout is existed
	 * 
	 * @param
	 * @return True if existed
	 */
	boolean isExist(String layoutID);
	
	/**
	 * Copy Layout with given ID
	 * 
	 * @param
	 * @return Copied layout ID
	 */
	String copyTopPageLayout(String layoutID);

	 /**
	  * Copy Layout with given ID
	  * 
	  * @param
	  * @return Copied layout ID
	  */
	 String copyTitleMenuLayout(String layoutID);
	 
	 /**
	  * Copy Layout with given ID
	  * 
	  * @param
	  * @return Copied layout ID
	  */
	 String copyMyPageLayout(String layoutID);
}