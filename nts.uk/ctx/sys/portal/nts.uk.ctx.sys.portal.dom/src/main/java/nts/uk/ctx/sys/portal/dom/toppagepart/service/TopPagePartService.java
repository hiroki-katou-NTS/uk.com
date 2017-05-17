package nts.uk.ctx.sys.portal.dom.toppagepart.service;

public interface TopPagePartService {
	/**
	 * check data is exit with companyId, code and type
	 * @param companyId
	 * @param code
	 * @param type
	 * @return
	 * true: is data
	 * false: no data
	 */
	boolean isExit(String companyId, String flowMenuCD, int type);
}
