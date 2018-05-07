package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;

@Stateless
public class JpaMailDestinationFunctionRepository implements MailDestinationFunctionRepository {

	@Override
	public MailDestinationFunction findByCidAndSettingItem(String companyId, UserInfoItem userInfoItem) {
		return null;
	}

}
