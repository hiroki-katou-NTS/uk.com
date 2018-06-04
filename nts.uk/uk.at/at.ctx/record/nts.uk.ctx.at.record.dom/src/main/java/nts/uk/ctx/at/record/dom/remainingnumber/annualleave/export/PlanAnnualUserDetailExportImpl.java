package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.comfirmdata.AnnualHolidayPlanManRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.comfirmdata.AnnualHolidayPlanMana;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
@Stateless
public class PlanAnnualUserDetailExportImpl implements PlanAnnualUserDetailExport{
	@Inject
	private AnnualHolidayPlanManRepository planManaRepo;
	@Inject
	private TempAnnualLeaveMngRepository tempMngRepo;
	@Override
	public List<GeneralDate> lstPlanDetail(String sId, String workTypeCd, Period dateData) {
		List<GeneralDate> outData = new ArrayList<>();
		//ドメインモデル「計画年休管理データ」を取得する
		List<AnnualHolidayPlanMana> lstPlanMana = planManaRepo.getDataBySidWorkTypePeriod(sId, workTypeCd, dateData);
		lstPlanMana.stream().forEach(x -> {
			outData.add(x.getYmd());
		});
		//ドメインモデル「暫定年休管理データ」を取得する
		List<TempAnnualLeaveManagement> lstLeaveMng = tempMngRepo.findBySidWorkTypePeriod(sId, workTypeCd, dateData);
		lstLeaveMng.stream().forEach(x -> {
			if(!outData.contains(x.getYmd())) {
				outData.add(x.getYmd());
			}
		});
		return outData;
	}

}
