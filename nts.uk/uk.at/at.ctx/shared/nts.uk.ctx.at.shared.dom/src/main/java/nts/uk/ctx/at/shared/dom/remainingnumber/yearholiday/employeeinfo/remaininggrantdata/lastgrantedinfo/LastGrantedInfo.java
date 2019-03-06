package nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.remaininggrantdata.lastgrantedinfo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;

/**
 * 前回付与情報を取得
 * @author tutk
 *
 */
@Stateless
public class LastGrantedInfo {
	
	@Inject
	private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;

	public Optional<AnnualLeaveGrantRemainingData> getLastGrantedInfo(String employeeId,GeneralDate baseDate) {
		//ドメインモデル「年休付与残数データ」を取得
		List<AnnualLeaveGrantRemainingData> listdata = annLeaGrantRemDataRepo.find(employeeId,baseDate);
		if(listdata.isEmpty())
			return Optional.empty();
		//sort DESC
		List<AnnualLeaveGrantRemainingData> listdataSort = listdata.stream()
				.sorted((x,y) -> y.getGrantDate().compareTo(x.getGrantDate())).collect(Collectors.toList());
		return Optional.of(listdataSort.get(0));
	}
}
