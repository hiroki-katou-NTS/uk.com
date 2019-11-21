package nts.uk.ctx.at.shared.dom.workrule.workday.export;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;

public class WorkDayServicesImpl implements IWorkDayServices {

	@Inject
	private ClosureRepository repo;
	@Inject
	private ClosureEmploymentRepository closureEmpRepo;

	@Override
	public List<ClosureDateOfEmployment> getClosureDate(String companyId) {
		List<ClosureDateOfEmployment> rersult = new ArrayList<ClosureDateOfEmployment>();
		// ドメインモデル「締め」を全て取得する(Lấy toàn bộ domain [closure])
		List<Closure> closureLst = this.repo.findAllActive(companyId, UseClassification.UseClass_Use);

		closureLst.forEach(closure -> {
			// 締めに紐付く雇用コード一覧を取得(Lấy EmploymentCodeList liên kết với Closure)
			List<ClosureEmployment> closureEmpLst = this.closureEmpRepo.findByClosureId(companyId,
					closure.getClosureId().value);

			closureEmpLst.forEach(emp -> {
				// 当月の締め日を取得する(Lấy ClosureDate của ClosureMonth )
				// 雇用コード、締め日のセットを作成(Tạo set cua employmentCode, ClosureDate)
				rersult.add(new ClosureDateOfEmployment(emp.getEmploymentCD(), closure.getClosureDateOfCurrentMonth()));
			});

		});
		// 雇用毎の締め日リストを返す(Trả về ClosureDateList của mỗi employment)
		return rersult;
	}

}
