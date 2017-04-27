package nts.uk.ctx.sys.portal.infra.repository.toppage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;

@Stateless
public class JpaTopPageRepository implements TopPageRepository {

	@Override
	public List<TopPage> findAll(String companyId) {
		// Mock data
		List<TopPage> lstTopPage = new ArrayList<TopPage>();
		lstTopPage.add(TopPage.createFromJavaType("1", "001", "id","no", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "002", "id","2", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "003", "id","4", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "004", "id","5", 0));
		return lstTopPage;
	}

	@Override
	public Optional<TopPage> findByCode(String companyId, String topPageCode) {
		List<TopPage> lstTopPage = new ArrayList<TopPage>();
		lstTopPage.add(TopPage.createFromJavaType("1", "001", "id","no", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "002", "id","2", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "003", "id","4", 0));
		lstTopPage.add(TopPage.createFromJavaType("1", "004", "id","5", 0));
		TopPage tp = lstTopPage.stream().filter(item -> {
			return item.getTopPageCode().v().equals(topPageCode);
		}).findAny().orElse(null);
		;
		return Optional.of(tp);
	}

	@Override
	public void add(TopPage topPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TopPage topPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String CompanyId, String topPageCode) {
		// TODO Auto-generated method stub
		
	}
}
