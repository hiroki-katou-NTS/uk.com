package nts.uk.ctx.sys.portal.dom.titlemenu.service;

public interface TitleMenuService {
	
	boolean isExist(String companyID, String titleMenuCD);
	
	void deleteTitleMenu(String companyID, String titleMenuCD);
	
	void copyTitleMenu(String companyID, String sourceTitleMenuCD, String targetTitleMenuCD, boolean overwrite);
	
}