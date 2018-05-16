package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PayoutManagementDataFinder {
	
	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;

	/**
	 * ドメイン「振休管理データ」より紐付け対象となるデータを取得する
	 * @param sid
	 * @param startDate
	 * @param endDate
	 * @return List<PayoutManagementDataDto>
	 */
	public List<PayoutManagementDataDto> getBySidDatePeriod(String sid, GeneralDate startDate, GeneralDate endDate){
		List<PayoutManagementData> listPayout = payoutManagementDataRepository.getBySidDatePeriod(sid, startDate, endDate, DigestionAtr.UNUSED.value);
		
		if (!listPayout.isEmpty()){
			return listPayout.stream().map(i->PayoutManagementDataDto.createFromDomain(i)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	public List<PayoutManagementDataDto> getBysiDRemCod(String empId, int state) {
		String cid = AppContexts.user().companyId();

		return payoutManagementDataRepository.getSidWithCod(cid, empId, state).stream().map(item -> PayoutManagementDataDto.createFromDomain(item))
				.collect(Collectors.toList());
	}
}
