package nts.uk.ctx.sys.portal.ac.find.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.sys.portal.dom.adapter.person.PersonInfoAdapter;

@Stateless
public class PersonInfoPortalAdapterImpl implements PersonInfoAdapter {

	@Inject
	private IPersonInfoPub personInfoPub;
	
	@Override
	public String getBusinessName(String sId) {
		PersonInfoExport info = personInfoPub.getPersonInfo(sId);
		return info != null ? info.getBusinessName() : "";
	}

}
