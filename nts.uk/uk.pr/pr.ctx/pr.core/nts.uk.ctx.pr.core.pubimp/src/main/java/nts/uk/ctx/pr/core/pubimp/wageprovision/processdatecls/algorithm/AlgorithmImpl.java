package nts.uk.ctx.pr.core.pubimp.wageprovision.processdatecls.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.algorithm.ClosureDateExport;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.algorithm.IAlgorithm;

@Stateless
public class AlgorithmImpl implements IAlgorithm {

	@Inject
	private EmpTiedProYearRepository empRepo;

	@Inject
	private ValPayDateSetRepository valRepo;

	@Override
	public List<ClosureDateExport> GetClosingSalaryEmploymentList(String companyId) {

		List<ClosureDateExport> result = new ArrayList<ClosureDateExport>();

		// ドメインモデル「処理年月に紐づく雇用」を取得する(Lấy domain [employment liên kết với
		// ProcessYearMonth])

		List<EmpTiedProYear> empTieds = this.empRepo.getByCid(companyId);

		// 締め日リストをNULLで作成する(Tạo ClosuredateList bằng null)
		if (empTieds.isEmpty()) {
			return Collections.emptyList();
		}
		// ドメインモデル「支払日の設定の既定値」を取得する(Lấy domain [giá trị mặc định của setting
		// ngày thanh toán ])
		List<ValPayDateSet> valDays = this.valRepo.getById(companyId);

		// 締め日リストをNULLで作成する(Tạo ClosuredateList bằng null)
		if (valDays.isEmpty()) {
			return Collections.emptyList();
		}

		// 取得した雇用リストと参照日リストを処理日区分NOで紐付けたリストを作成するTạo list kết hợp 2 list
		// "employment list" đã lấy và "referencedate list" bằng "processCateNO

		empTieds.stream().forEach(tied -> {

			List<String> employmentCodes = tied.getEmploymentCodes().stream().map(x -> x.v())
					.collect(Collectors.toList());

			List<Integer> referenceDates = valDays.stream()
					.filter(valDay -> valDay.getProcessCateNo() == tied.getProcessCateNo())
					.map(valDay -> valDay.getBasicSetting().getEmployeeExtractionReferenceDate().getRefeDate().value)
					.collect(Collectors.toList());

			result.add(new ClosureDateExport(employmentCodes, referenceDates));
		});

		return result;
	}

}
