package nts.uk.shr.sample.task.tran;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import nts.uk.shr.infra.file.storage.info.StoredPackInfoRepository;

@Stateless
@Transactional(value = TxType.REQUIRES_NEW)
public class SampleOtherTran {

	@Inject
	private StoredPackInfoRepository repo;
	
	public void goOtherTran() {
		this.repo.add("x", "aaa", "test");
		
	}
}
