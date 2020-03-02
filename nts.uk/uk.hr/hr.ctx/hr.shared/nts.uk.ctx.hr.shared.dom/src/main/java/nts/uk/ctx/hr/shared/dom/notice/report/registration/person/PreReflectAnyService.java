package nts.uk.ctx.hr.shared.dom.notice.report.registration.person;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;

@Stateless
public class PreReflectAnyService {
	
	@Inject
	private PreReflectAnyDataRepository preReflectAnyDataRepo;
	
	@Inject
	private PreReflectAnyItemRepository preReflectAnyItemRepo;
	
	
	public void preprareReflectData(PreReflectAnyData anyData, List<PreReflectAnyItem> anyItems) {
		
		if(anyData != null) {
			
			this.preReflectAnyDataRepo.insert(anyData);
			
		}
		
		if(!CollectionUtil.isEmpty(anyItems)) {
			
			this.preReflectAnyItemRepo.insertAll(anyItems);
		}
	}

}
