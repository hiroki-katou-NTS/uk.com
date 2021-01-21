package nts.uk.ctx.at.shared.app.find.remainingnumber.paymana;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PayoutManagementDataFinder {
	
	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;

	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;
	/**
	 * ドメイン「振休管理データ」より紐付け対象となるデータを取得する
	 * Ｆ．振休管理データの紐付設定（振出選択）画面表示処理
	 * @param sid
	 * @return List<PayoutManagementDataDto>
	 */
//	public List<PayoutManagementDataDto> getBySidDatePeriod(String sid, String subOfHDID){
//		List<PayoutManagementData> listPayout = payoutManagementDataRepository.getBySidDatePeriod(sid,subOfHDID,DigestionAtr.UNUSED.value);
//		
//		List<PayoutSubofHDManagement> listPayoutSub = payoutSubofHDManaRepository.getBySubId(subOfHDID);
//		Map<String, UsedDays> listPaySubId = listPayoutSub.stream().collect(
//                Collectors.toMap(PayoutSubofHDManagement::getPayoutId, PayoutSubofHDManagement::getUsedDays));
////		List<String> listPaySubId = listPayoutSub.stream().map(i->i.getPayoutId()).collect(Collectors.toList());
//		return listPayout.stream().map(i->{
//			PayoutManagementDataDto payout = PayoutManagementDataDto.createFromDomain(i);
//			if (listPaySubId.containsKey(i.getPayoutId())){
//				payout.setLinked(true);
//				payout.setunUsedDays(listPaySubId.get(i.getPayoutId()).v());
//			}
//			return payout;
//			
//		}).collect(Collectors.toList());
//	}
	
	public List<PayoutManagementDataDto> getBysiDRemCod(String empId, int state) {
		String cid = AppContexts.user().companyId();

		return payoutManagementDataRepository.getSidWithCod(cid, empId, state).stream().map(item -> PayoutManagementDataDto.createFromDomain(item))
				.collect(Collectors.toList());
	}
}
