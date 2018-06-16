package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AbsenceReruitmentMngInPeriodQueryImpl implements AbsenceReruitmentMngInPeriodQuery{
	@Inject
	private SubstitutionOfHDManaDataRepository confirmAbsMngRepo;
	@Inject
	private PayoutSubofHDManaRepository confirmRecAbsRepo;
	@Inject
	private InterimRecAbasMngRepository recAbsRepo;
	@Inject
	private PayoutManagementDataRepository confirmRecRepo;
	@Override
	public AbsRecRemainMngOfInPeriod getAbsRecMngInPeriod(String cid, String sid, DatePeriod dateData,
			GeneralDate baseDate, boolean mode) {
		//アルゴリズム「未相殺の振休(確定)を取得する」を実行する
		List<AbsRecDetailPara> lstAbsRec = this.getAbsOfUnOffset(sid);		
		//アルゴリズム「未使用の振出(確定)を取得する」を実行する
		lstAbsRec = this.getUnUseDaysConfirmRec(sid, lstAbsRec);
		//繰越数を計算する
		
		return null;
	}

	@Override
	public List<AbsRecDetailPara> getAbsOfUnOffset(String sid) {
		List<AbsRecDetailPara> lstOutput = new ArrayList<>();
		//アルゴリズム「確定振休から未相殺の振休を取得する」を実行する
		List<SubstitutionOfHDManagementData> lstUnOffsetDays = this.getAbsOfUnOffsetFromConfirm(sid);
		//未相殺のドメインモデル「振休管理データ」(Output)の件数をチェックする
		for (SubstitutionOfHDManagementData x : lstUnOffsetDays) {
			//アルゴリズム「暫定振出と紐付けをしない確定振休を取得する」を実行する
			AbsRecDetailPara output = this.getInterimAndConfirmAbs(sid, x);
			lstOutput.add(output);
		}		
		return lstOutput;
	}

	
	/*private List<AbsRecDetailPara> insertAbsRec(List<AbsRecDetailPara> lstData, AbsRecDetailPara detailData){
		List<AbsRecDetailPara> lstTmp = lstData.stream()
				.filter(x -> x.getSid() == detailData.getSid() && detailData.getYmdData() == detailData.getYmdData())
				.collect(Collectors.toList());
		if(lstTmp.isEmpty()) {
			lstData.add(detailData);
		} else {
			AbsRecDetailPara tmp = lstTmp.get(0);
			lstData.remove(tmp);
			tmp.setUnOffsetOfAb(detailData.getUnOffsetOfAb());
			lstData.add(tmp);
		}
		return lstData;
	}*/
	@Override
	public List<SubstitutionOfHDManagementData> getAbsOfUnOffsetFromConfirm(String sid) {
		String cid = AppContexts.user().companyId();
		// ドメインモデル「振休管理データ」
		List<SubstitutionOfHDManagementData> lstAbsConfirmData = confirmAbsMngRepo.getBysiD(cid, sid);		
		return lstAbsConfirmData.stream().filter(x -> x.getRemainDays().v() > 0).collect(Collectors.toList());
	}	

	@Override
	public AbsRecDetailPara getInterimAndConfirmAbs(String sid, SubstitutionOfHDManagementData confirmAbsData) {
		//ドメインモデル「暫定振出振休紐付け管理」を取得する
		List<InterimRecAbsMng> lstInterim = recAbsRepo.getBySidMng(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM, confirmAbsData.getSubOfHDID());
		double unUseDays = 0;
		for (InterimRecAbsMng interimRecAbsMng : lstInterim) {
			unUseDays += interimRecAbsMng.getUseDays().v();
		}
		if(lstInterim.isEmpty()) {
			unUseDays = confirmAbsData.getRemainDays().v();
		} else {
			unUseDays =  confirmAbsData.getRemainDays().v() - unUseDays;
		}
		//未相殺日数：INPUT.未相殺日数－合計(暫定振出振休紐付け管理.使用日数)
		UnOffsetOfAbs unOffsetData = new UnOffsetOfAbs(confirmAbsData.getSubOfHDID(), confirmAbsData.getRequiredDays().v(), unUseDays);
		//INPUT．振休管理データを「振出振休明細」に追加する
		
		AbsRecDetailPara outData = new AbsRecDetailPara(sid, 
				MngDataStatus.CONFIRMED,
				confirmAbsData.getHolidayDate(), 
				OccurrenceDigClass.OCCURRENCE,
				unOffsetData,
				null);
		return outData;
		
	}

	@Override
	public List<AbsRecDetailPara> getUnUseDaysConfirmRec(String sid, List<AbsRecDetailPara> lstDataDetail) {
		//2-1.確定振出から未使用の振出を取得する
		String cid = AppContexts.user().companyId();
		List<PayoutManagementData> lstConfirmRec = confirmRecRepo.getSidWithCod(cid, sid, DigestionAtr.UNUSED.value)
				.stream().filter(x -> x.getUnUsedDays().v() > 0)
				.collect(Collectors.toList());
		for (PayoutManagementData confirmRecData : lstConfirmRec) {
			double unUseDays = 0;
			//アルゴリズム「暫定振休と紐付けをしない確定振出を取得する」を実行する
			List<InterimRecAbsMng> lstInterim = recAbsRepo.getBySidMng(DataManagementAtr.CONFIRM, DataManagementAtr.INTERIM, confirmRecData.getPayoutId());
			for (InterimRecAbsMng interimData : lstInterim) {
				unUseDays += interimData.getUseDays().v();
			}
			if(lstInterim.isEmpty()) {
				unUseDays = confirmRecData.getUnUsedDays().v();
			} else {
				unUseDays = confirmRecData.getUnUsedDays().v() - unUseDays;
			}
			UnUseOfRec unUseDayOfRec = new UnUseOfRec(confirmRecData.getExpiredDate(), 
					confirmRecData.getPayoutId(), 
					confirmRecData.getOccurredDays().v(), 
					confirmRecData.getLawAtr(), 
					unUseDays);
			//Khong ton tai 1 ngay vua co nghi bu va di lam bu
			AbsRecDetailPara dataDetail = new AbsRecDetailPara(sid, 
					MngDataStatus.CONFIRMED, confirmRecData.getPayoutDate(), OccurrenceDigClass.OCCURRENCE, null, unUseDayOfRec);
			lstDataDetail.add(dataDetail);
		}		
		return lstDataDetail;
	}

	@Override
	public double calcCarryForwardDays(GeneralDate startDate, List<AbsRecDetailPara> lstDataDetail) {
		// TODO Auto-generated method stub
		return 0;
	}

}
