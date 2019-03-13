package nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.remaininggrantdata.checklastgrantnumber.calcumulativegrant;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

/**
 * 累積付与の計算
 * @author tutk
 *
 */
@Stateless
public class CalCumulativeGrant {
	
	@Inject
	private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;
	
	public CumulativeGrant getCalCumulativeGrant(AnnualLeaveGrantRemainingData annualLeaveGrantRemainingData,Double numberDayAward ) {
		
		//○付与数←INPUT．残数データ．明細．付与数．日数
		Double numberGrantOutput =  annualLeaveGrantRemainingData.getDetails().getGrantNumber().getDays().v();
		//INPUT．残数データ．付与日 -1年+1日
		GeneralDate startDate = annualLeaveGrantRemainingData.getGrantDate().addYears(-1).addDays(1);
		// INPUT．残数データ．付与日-1日
		GeneralDate endDate = annualLeaveGrantRemainingData.getGrantDate().addDays(-1);
		
		List<AnnualLeaveGrantRemainingData> listData = annLeaGrantRemDataRepo.findByPeriod(annualLeaveGrantRemainingData.getEmployeeId(), startDate, endDate);
		//sort DESC
		List<AnnualLeaveGrantRemainingData> listDataSort = listData.stream().sorted((x,y) -> y.getGrantDate().compareTo(x.getGrantDate())).collect(Collectors.toList());
		boolean checkExist = false;
		GeneralDate startPeriod = annualLeaveGrantRemainingData.getGrantDate();
		for(int i =0;i<listDataSort.size();i++) {
			if(i==10) {
				break;
			}
			numberGrantOutput = listDataSort.get(i).getDetails().getGrantNumber().getDays().v();
			if(numberDayAward<=numberGrantOutput) {
				checkExist = true;
				startPeriod = listDataSort.get(i).getGrantDate();
				break;
			}
		}
		//○年休累積付与情報を作成
		Period period = new Period();
		period.setStartDate(startPeriod);
		period.setEndDate(annualLeaveGrantRemainingData.getGrantDate().addYears(1).addDays(-1));
		if(!checkExist) {
			numberGrantOutput = annualLeaveGrantRemainingData.getDetails().getGrantNumber().getDays().v();
		}
		//○年休累積付与情報を返す
		CumulativeGrant cumulativeGrant = new CumulativeGrant(numberGrantOutput, period);
		
		return cumulativeGrant;
	}
	
}
