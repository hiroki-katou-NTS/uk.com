package nts.uk.ctx.bs.company.app.find.share;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;

@Stateless
public class CompanyAdapterImpl implements CompanyAdapter {

	@Override
	public Optional<CompanyInfor> getCurrentCompany() {
		// TODO Auto-generated method stub
		return null;
	}

}
