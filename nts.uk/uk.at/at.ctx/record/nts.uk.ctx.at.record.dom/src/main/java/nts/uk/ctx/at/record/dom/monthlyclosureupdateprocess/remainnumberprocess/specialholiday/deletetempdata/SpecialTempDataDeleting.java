package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.deletetempdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 特別休暇暫定データ削除
 * @author shuichi_ishida
 */
@Stateless
public class SpecialTempDataDeleting {

	/** 暫定残数管理データ */
	@Inject
	private InterimRemainRepository interimRemainRepo;
	
	/** 特別休暇暫定データ */
	@Inject
	private InterimSpecialHolidayMngRepository tempSpecialRepo;
	
	/**
	 * 特別休暇暫定データ削除
	 * @param empId 社員ID
	 * @param period 期間
	 */
	public void deleteTempDataProcess(String empId, DatePeriod period) {

		// 「特別休暇暫定データを削除する」
		List<InterimRemain> interimRemains = this.interimRemainRepo.getRemainBySidPriod(
				empId, period, RemainType.SPECIAL);
		if (CollectionUtil.isEmpty(interimRemains)) return;
		List<String> ids = interimRemains.stream().map(b -> b.getRemainManaID()).collect(Collectors.toList());
		for (String id : ids) this.tempSpecialRepo.deleteSpecialHoliday(id);
		this.interimRemainRepo.deleteBySidPeriodType(empId, period, RemainType.SPECIAL);
	}
}
