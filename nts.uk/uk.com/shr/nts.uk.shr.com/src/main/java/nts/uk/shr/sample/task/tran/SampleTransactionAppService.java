package nts.uk.shr.sample.task.tran;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.task.AsyncTaskInfoRepository;
import nts.arc.task.data.AsyncTaskData;
import nts.arc.task.tran.TransactionService;
import nts.uk.shr.infra.file.storage.info.StoredPackInfoRepository;

@Stateless
public class SampleTransactionAppService {
	
	@Inject
	private SampleOtherTran otherTran;

	@Inject
	private AsyncTaskInfoRepository testRepo;
	@Inject
	private StoredPackInfoRepository testRepo2;
	
	public void test() {
		
		this.testRepo.insertTaskData("1", new AsyncTaskData("X", "XYZ"));

		//this.otherTran.goOtherTran();
		TransactionService.newTran(() -> {
			this.testRepo2.add("x", "aaa", "test");
		});
		
		this.testRepo.insertTaskData("1", new AsyncTaskData("a", "hello"));
		this.testRepo.insertTaskData("1", new AsyncTaskData("b", "bbbb"));
		
		boolean x = true;
		if (x) {
			throw new RuntimeException("hoge");
		}
		
		
	}
}
