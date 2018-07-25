package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.deletetempdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT -<<Work>> 振休暫定データ削除
 *
 */

@Stateless
public class SubstitutionTempDataDeleting {

	@Inject
	private InterimRecAbasMngRepository tempDataRepo;

	@Inject
	private InterimRemainRepository tempRemainRepo;

	// 暫定データ削除
	public void deleteTempSubstitutionData(AggrPeriodEachActualClosure period, String empId) {
		deleteTempPayoutMngData(period.getPeriod(), empId);
		deleteTempSubstitutionData(period.getPeriod(), empId);
	}

	// 振出暫定データの削除
	private void deleteTempPayoutMngData(DatePeriod period, String employeeId) {
		List<InterimRemain> listTempRemain = tempRemainRepo.getRemainBySidPriod(employeeId, period,
				RemainType.PICKINGUP);
		if (CollectionUtil.isEmpty(listTempRemain))
			return;
		List<InterimRecAbsMng> listRecAbsData = tempDataRepo.getRecByIdsMngAtr(
				listTempRemain.stream().map(t -> t.getRemainManaID()).collect(Collectors.toList()),
				DataManagementAtr.INTERIM);
		if (CollectionUtil.isEmpty(listRecAbsData))
			return;
		List<String> listRecId = listRecAbsData.stream().map(r -> r.getRecruitmentMngId()).collect(Collectors.toList());
		tempDataRepo.deleteInterimRecMng(listRecId);
	}

	// 振休暫定データの削除
	private void deleteTempSubstitutionData(DatePeriod period, String employeeId) {
		List<InterimRemain> listTempRemain = tempRemainRepo.getRemainBySidPriod(employeeId, period, RemainType.PAUSE);
		if (CollectionUtil.isEmpty(listTempRemain))
			return;
		List<InterimRecAbsMng> listRecAbsData = tempDataRepo.getAbsByIdsMngAtr(
				listTempRemain.stream().map(t -> t.getRemainManaID()).collect(Collectors.toList()),
				DataManagementAtr.INTERIM);
		if (CollectionUtil.isEmpty(listRecAbsData))
			return;
		List<String> listAbsId = listRecAbsData.stream().map(r -> r.getAbsenceMngId()).collect(Collectors.toList());
		tempDataRepo.deleteInterimAbsMng(listAbsId);
	}

}
