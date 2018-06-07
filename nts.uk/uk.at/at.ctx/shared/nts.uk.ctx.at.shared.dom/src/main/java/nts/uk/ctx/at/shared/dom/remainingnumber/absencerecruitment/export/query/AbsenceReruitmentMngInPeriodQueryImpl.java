package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
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
	@Override
	public AbsRecRemainMngOfInPeriod getAbsRecMngInPeriod(String cid, String sid, DatePeriod dateData,
			GeneralDate baseDate, boolean mode) {
		//アルゴリズム「未相殺の振休(確定)を取得する」を実行する
		
		//アルゴリズム「未使用の振出(確定)を取得する」を実行する
		return null;
	}

	@Override
	public List<AbsRecDetailPara> getAbsOfUnOffset(String sid) {
		List<AbsRecDetailPara> lstOutput = new ArrayList<>();
		//アルゴリズム「確定振休から未相殺の振休を取得する」を実行する
		List<SubstitutionOfHDManagementData> lstUnOffsetDays = this.getAbsOfUnOffsetFromConfirm(sid);
		//未相殺のドメインモデル「振休管理データ」(Output)の件数をチェックする
		lstUnOffsetDays.stream().forEach(x -> {
			//アルゴリズム「暫定振出と紐付けをしない確定振休を取得する」を実行する
			AbsRecDetailPara output = this.getInterimAndConfirmAbs(sid, x);
			lstOutput.add(output);
		});
		return lstOutput;
	}

	@Override
	public List<SubstitutionOfHDManagementData> getAbsOfUnOffsetFromConfirm(String sid) {
		String cid = AppContexts.user().companyId();
		// ドメインモデル「振休管理データ」
		List<SubstitutionOfHDManagementData> lstAbsConfirmData = confirmAbsMngRepo.getBysiD(cid, sid);
		/*lstAbsConfirmData.stream().forEach(x -> {
			//アルゴリズム「確定振休から未相殺の振休を取得する(1件)」を実行する
			double unOffsetDays = this.getUnOffsetOfAbs(x.getSubOfHDID(), x.getRequiredDays().v());
			if(unOffsetDays > 0) {
				x.setRemainDays(new ManagementDataRemainUnit(unOffsetDays));
			}
		});*/
		return lstAbsConfirmData;
	}
	
	private double getUnOffsetOfAbs(String mngId, double requestDays) {
		double outputData = 0;
		//ドメインモデル「振出振休紐付け管理」を取得する
		List<PayoutSubofHDManagement> lstAbsOfMng = confirmRecAbsRepo.getBySubId(mngId);
		double useDays = 0;
		for (PayoutSubofHDManagement absOfMng : lstAbsOfMng) {
			useDays += absOfMng.getUsedDays().v();
		}
		return outputData - useDays;
		
	}

	@Override
	public AbsRecDetailPara getInterimAndConfirmAbs(String sid, SubstitutionOfHDManagementData confirmAbsData) {
		double unOffsetDays = 0;
		//ドメインモデル「暫定振出振休紐付け管理」を取得する
		List<InterimRecAbsMng> lstInterim = recAbsRepo.getBySidMng(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM, confirmAbsData.getSubOfHDID());
		for (InterimRecAbsMng interimRecAbsMng : lstInterim) {
			unOffsetDays += interimRecAbsMng.getUseDays().v();
		}
		if(unOffsetDays > 0) {
			//「振休の未相殺」．未相殺日数=未相殺日数
			UnOffsetOfAbs unOffsetData = new UnOffsetOfAbs(confirmAbsData.getSubOfHDID(), confirmAbsData.getRequiredDays().v(), unOffsetDays);
			//INPUT．振休管理データを「振出振休明細」に追加する
			AbsRecDetailPara outData = new AbsRecDetailPara(sid, 
					MngDataStatus.CONFIRMED, //TODO
					confirmAbsData.getHolidayDate(), 
					OccurrenceDigClass.OCCURRENCE,//TODO
					unOffsetData);
			return outData;
		}
		return null;
	}

}
