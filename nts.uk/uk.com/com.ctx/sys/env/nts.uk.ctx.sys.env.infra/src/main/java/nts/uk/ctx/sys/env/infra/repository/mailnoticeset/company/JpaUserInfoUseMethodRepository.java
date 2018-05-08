package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository;

@Stateless
public class JpaUserInfoUseMethodRepository implements UserInfoUseMethodRepository{

	@Override
	public List<UserInfoUseMethod> findByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(UserInfoUseMethod userInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(UserInfoUseMethod userInfo) {
		// TODO Auto-generated method stub
		
	}

}
