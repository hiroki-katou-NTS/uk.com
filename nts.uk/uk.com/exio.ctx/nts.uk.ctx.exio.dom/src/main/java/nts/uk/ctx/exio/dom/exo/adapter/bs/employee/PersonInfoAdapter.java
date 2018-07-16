package nts.uk.ctx.exio.dom.exo.adapter.bs.employee;

import java.util.List;

public interface PersonInfoAdapter {
	PersonInfoImport getPersonInfo(String sID);
	List<PersonInfoImport> listPersonInfor(List<String> listSID);
}
