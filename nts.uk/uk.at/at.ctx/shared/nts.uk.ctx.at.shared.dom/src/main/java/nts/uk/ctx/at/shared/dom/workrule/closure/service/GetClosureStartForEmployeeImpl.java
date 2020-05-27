package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

/**
 * 実装：社員に対応する締め開始日を取得する
 * @author shuichi_ishida
 */
@Stateless
public class GetClosureStartForEmployeeImpl implements GetClosureStartForEmployee {

	/** 締めアルゴリズム */
	@Inject
	private ClosureService closureService;
	/** 社員の取得 */
	@Inject
	private EmpEmployeeAdapter empEmployee;
	/** 所属雇用履歴の取得 */
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	/** 雇用に紐づく就業締めの取得 */
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	/*require用*/
	@Inject
	private ClosureRepository closureRepository;	

	/** 社員に対応する締め開始日を取得する */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<GeneralDate> algorithm(String employeeId) {
		val require = new GetClosureStartForEmployeeImpl.Require() {
			@Override
			public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD) {
				return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
			}
			@Override
			public List<Closure> findAllUse(String companyId) {
				return closureRepository.findAllUse(companyId);
			}
			@Override
			public Optional<Closure> findClosureById(String companyId, int closureId) {
				return closureRepository.findById(companyId, closureId);
			}
		};
		val cacheCarrier = new CacheCarrier();
		return algorithmRequire(require, cacheCarrier, employeeId);
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<GeneralDate> algorithmRequire(Require require, CacheCarrier cacheCarrier, String employeeId) {
		
		GetClosureStartForEmployeeProc proc = new GetClosureStartForEmployeeProc(
				this.closureService,
				this.empEmployee,
				this.shareEmploymentAdapter,
				this.closureEmploymentRepo);
		return proc.algorithm(require, cacheCarrier, employeeId);
	}

	public static interface Require extends GetClosureStartForEmployeeProc.Require{
	}
}
