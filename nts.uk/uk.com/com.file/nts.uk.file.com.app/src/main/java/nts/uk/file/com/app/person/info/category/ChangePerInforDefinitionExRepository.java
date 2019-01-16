package nts.uk.file.com.app.person.info.category;

import java.util.List;


public interface ChangePerInforDefinitionExRepository{
	
	List<Object[]> getChangePerInforDefinitionToExport(String cid, String contracCd,String companyIdRoot,int salaryUseAtr, int personnelUseAtr, int employmentUseAtr);
}
