package nts.uk.ctx.sys.portal.dom.toppagepart.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartRepository;
@Stateless
public class TopPagePartServiceImpl implements TopPagePartService{

	@Inject
	private TopPagePartRepository repository;
	@Override
	public boolean isExit(String companyId, String code, int type) {
		Optional<TopPagePart> topPage = repository.dataByCodeAndType(companyId, code, type);
		if(topPage.isPresent()){
			return true;
		}else{
			return false;
		}
	}

}
