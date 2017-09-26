package nts.uk.ctx.workflow.dom.approvermanagement.workroot.person;
/**
 * 社員IDに該当する社員を取得する
 * @author dudt
 *
 */
public interface PersonInforExportAdapter {
	/**
	 * get person name
	 * @param sID
	 * @return
	 */
	PersonInforExportDto getPersonInfo(String sID);
}
