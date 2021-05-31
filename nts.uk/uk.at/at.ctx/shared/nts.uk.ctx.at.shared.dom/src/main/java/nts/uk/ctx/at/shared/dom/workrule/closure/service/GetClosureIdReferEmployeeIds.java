package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業締め日.アルゴリズム.Query.社員(list)に対応する処理締めを取得する.社員(list)に対応する処理締めを取得する
 * @author ThanhPV
 */
@Stateless
public class GetClosureIdReferEmployeeIds {

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;

	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	public List<EmployeeIdClosureIdDto> get(List<String> employeeIds) {

		List<EmployeeIdClosureIdDto> result = new ArrayList<EmployeeIdClosureIdDto>();
		
		val cacheCarrier = new CacheCarrier();
		
		for (String employeeId : employeeIds) {
			List<Closure> closures = ClosureService
					.getClosureDataByEmployees(
							ClosureService.createRequireM7(closureRepository, closureEmploymentRepo,
									shareEmploymentAdapter),
							cacheCarrier, Arrays.asList(employeeId), GeneralDate.today());
			result.add(new EmployeeIdClosureIdDto(employeeId, closures.get(0).getClosureId().value));
			
		}
		return result;
	}
}
