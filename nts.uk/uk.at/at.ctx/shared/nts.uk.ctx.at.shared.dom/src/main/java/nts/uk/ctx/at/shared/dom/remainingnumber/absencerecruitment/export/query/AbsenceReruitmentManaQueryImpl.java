package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.InterimRemainAggregateOutputData;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.ClosureRemainPeriodOutputData;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.RemainManagementExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class AbsenceReruitmentManaQueryImpl implements AbsenceReruitmentManaQuery{
	
	@Inject
	private RemainManagementExport remainManaExport;
	@Inject
	private InterimRemainRepository remainRepo;
	@Inject
	private InterimRecAbasMngRepository recAbsRepo;
	@Inject
	private PayoutManagementDataRepository confirmRecMngRepo;
	@Inject
	private SubstitutionOfHDManaDataRepository comfirmAbsMngRepo;
	@Inject
	private PayoutSubofHDManaRepository confirmRecAbsRepo;
	@Override
	public List<InterimRemainAggregateOutputData> getAbsRecRemainAggregate(String employeeId, GeneralDate baseDate,
			YearMonth startMonth, YearMonth endMonth) {
		List<InterimRemainAggregateOutputData> lstOutData = new ArrayList<>();
		//アルゴリズム「締めと残数算出対象期間を取得する」を実行する
		ClosureRemainPeriodOutputData closureData = remainManaExport.getClosureRemainPeriod(employeeId, baseDate, startMonth, endMonth);
		//残数算出対象年月を設定する
		for(YearMonth ym = closureData.getStartMonth(); closureData.getEndMonth().greaterThanOrEqualTo(ym); ym.addMonths(1)) {
			InterimRemainAggregateOutputData outPutData = new InterimRemainAggregateOutputData(ym, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0);
			//アルゴリズム「指定年月の締め期間を取得する」を実行する
			DatePeriod dateData = remainManaExport.getClosureOfMonthDesignation(closureData.getClosure(), ym);
			//アルゴリズム「期間内の振休発生数合計を取得」を実行する
			Double occurrentDays = this.getTotalOccurrentDay(employeeId, dateData);
			//アルゴリズム「期間内の振休使用数合計を取得」を実行する
			Double useDays = this.getUsedDays(employeeId, dateData);			
			//残数算出対象年月をチェックする
			if(ym.greaterThan(closureData.getClosure().getClosureMonth().getProcessingYm())) {
				//月度別代休残数集計を作成する
				outPutData.setYm(ym);
				outPutData.setMonthOccurrence(occurrentDays);
				outPutData.setMonthUse(useDays);
			} else {
				//当月の振休残数を集計する
				outPutData = this.calAsbRemainOfCurrentMonth(employeeId, dateData, outPutData);
				//月末振休残数：月初振休残数＋振休発生数合計－振休使用数合計－振休消滅数合計
				outPutData.setMonthEndRemain(outPutData.getMonthStartRemain() + occurrentDays - useDays - outPutData.getMonthExtinction() );
			}
			lstOutData.add(outPutData);
		}
		return lstOutData;
	}
	@Override
	public Double getTotalOccurrentDay(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定振出管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData, RemainType.PAUSE);
		Double outputData = (double) 0;
		for (InterimRemain data : getRemainBySidPriod) {
			Optional<InterimRecMng> recData = recAbsRepo.getReruitmentById(data.getRemainManaID());
			if(recData.isPresent()) {
				outputData += recData.get().getOccurrenceDays().v();					
			}
		}
		//ドメインモデル「振出管理データ」を取得する
		List<PayoutManagementData> lstComfirmData = confirmRecMngRepo.getBySidAndDatePeriod(employeeId, dateData);
		for (PayoutManagementData comfirmData : lstComfirmData) {
			outputData += comfirmData.getOccurredDays().v();
		}
		//振休発生数合計：合計(振出管理データ->発生日数)＋合計(暫定振出管理データ->発生日数)
		return outputData ;
	}
	@Override
	public Double getUsedDays(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定振休管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData, RemainType.PAUSE);
		Double outputData = (double) 0;
		for (InterimRemain data : getRemainBySidPriod) {
			Optional<InterimAbsMng> absData = recAbsRepo.getAbsById(data.getRemainManaID());
			if(absData.isPresent()) {
				outputData += absData.get().getRequeiredDays().v();
			}
		}
		//ドメインモデル「振休管理データ」を取得する
		List<SubstitutionOfHDManagementData> lstComfirmData = comfirmAbsMngRepo.getBySidAndDatePeriod(employeeId, dateData);
		for (SubstitutionOfHDManagementData comfimAsbData : lstComfirmData) {
			outputData += comfimAsbData.getRequiredDays().v();
		}
		return outputData;
	}
	@Override
	public AbsRecGenerationDigestionHis generationDigestionHis(String cid, String sid, GeneralDate baseDate) {
		//確定管理データを取得する
		AbsRecConfirmOutputPara absRecConfirmData = this.getAbsRecConfirmData(sid);
		//暫定管理データを取得する
		AbsRecInterimOutputPara absRecInterimData = this.getAbsRecInterimData(sid, baseDate);
		//振出履歴を作成する
		List<RecruitmentHistoryOutPara> lstHisRecData = this.createRecruitmentHis(absRecInterimData.getInterimRecMngInfor(), absRecConfirmData.getLstRecConfirm());
		//振休履歴を作成する
		List<AbsenceHistoryOutputPara> lstHisAbsData = this.createAbsenceHis(absRecInterimData.getInterimAbsMngInfor());
		//振出振休履歴対照情報を作成する
		List<RecAbsHistoryOutputPara> createAbsRecData = this.createRecAbsHis(lstHisRecData, lstHisAbsData, absRecInterimData.getInterimRecAbsMngInfor());
		//残数集計情報を作成する
		AsbRemainTotalInfor totalInfor = this.getAbsRemainTotalInfor(lstHisRecData, lstHisAbsData);
		
		return new AbsRecGenerationDigestionHis(sid, totalInfor, createAbsRecData);
	}
	@Override
	public AbsRecInterimOutputPara getAbsRecInterimData(String sid, GeneralDate baseDate) {
		// 対象期間を決定する
		DatePeriod adjustDate = remainManaExport.periodCovered(sid, baseDate);
		//指定期間内に発生した暫定振出と紐付いた確定振休・暫定振休を取得する
		AbsRecInterimOutputPara outputData = this.getInterimAbsMng(sid, adjustDate);
		//未消化の確定振出に紐付いた暫定振休を取得する
		outputData = this.getNotInterimAbsMng(sid, adjustDate, outputData);
		
		return outputData;
	}
	@Override
	public AbsRecInterimOutputPara getInterimAbsMng(String sid, DatePeriod dateData) {
		AbsRecInterimOutputPara outPut = new AbsRecInterimOutputPara(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
		List<InterimRemain> lstRemain = remainRepo.getRemainBySidPriod(sid, dateData, RemainType.PAUSE);
		if(lstRemain.isEmpty()) {
			return outPut;
		}
		List<InterimAbsMng> lstAbsData = new ArrayList<InterimAbsMng>();
		List<InterimRecMng> lstRecData = new ArrayList<InterimRecMng>();
		List<InterimRecAbsMng> lstRecAbsMng = new ArrayList<InterimRecAbsMng>();
		//ドメインモデル「暫定振出振休紐付け管理」を取得する 振出管理データ IN (暫定振出管理データ.残数管理データID)
		lstRemain.stream().forEach(x -> {
			//ドメインモデル「暫定振出管理データ」を取得する
			Optional<InterimRecMng> optRecMng = recAbsRepo.getReruitmentById(x.getRemainManaID());
			if(optRecMng.isPresent()) {
				lstRecData.add(optRecMng.get());
			}
			List<InterimRecAbsMng> optRecAbsData = recAbsRepo.getRecOrAbsMng(x.getRemainManaID(), true, DataManagementAtr.INTERIM);
			optRecAbsData.stream().forEach(y -> {
				lstRecAbsMng.add(y);
				//ドメインモデル「暫定振休管理データ」を取得する
				Optional<InterimAbsMng> optAbsMng = recAbsRepo.getAbsById(y.getAbsenceMngId());
				if(optAbsMng.isPresent()) {
					lstAbsData.add(optAbsMng.get());
				}
			});			
		});
		
		return new AbsRecInterimOutputPara(lstAbsData, lstRecAbsMng, lstRecData);
	}
	@Override
	public AbsRecInterimOutputPara getNotInterimAbsMng(String sid, DatePeriod dateData, AbsRecInterimOutputPara absRecData) {
		// can phai gop voi du lieu truoc nua
		//振出管理データをチェックする
		//TODO lam tiep khi chuyen domain
		return null;
	}
	@Override
	public List<RecruitmentHistoryOutPara> createRecruitmentHis(List<InterimRecMng> interimData, List<PayoutManagementData> confirmData) {
		List<RecruitmentHistoryOutPara> lstOutputData = new ArrayList<>();
		//振出履歴Listを作成する
		//振出管理データの件数分ループ
		confirmData.stream().forEach(x -> {
			RecruitmentHistoryOutPara hisData = new RecruitmentHistoryOutPara();
			hisData.setRecId(x.getPayoutId());
			hisData.setDataAtr(MngDataAtr.CONFIRMED);
			hisData.setRecDate(x.getPayoutDate());//can xac nhan lai
			hisData.setHolidayAtr(x.getLawAtr().value);
			hisData.setExpirationDate(x.getExpiredDate());
			hisData.setOccurrenceDays(x.getOccurredDays().v());
			hisData.setUnUseDays(x.getUnUsedDays().v());
			//確定振出に紐付いた暫定振出振休紐付け管理を抽出する		
			//Optional<InterimRecAbsMng> optRecConfirm = recAbsRepo.getRecOrAbsMng(x.getPayoutId(), true, DataManagementAtr.CONFIRM);
			
		});
		//暫定振出管理データの件数分ループ
		interimData.stream().forEach(x -> {
			RecruitmentHistoryOutPara hisData = new RecruitmentHistoryOutPara();
			hisData.setRecId(x.getRecruitmentMngId());
			hisData.setExpirationDate(x.getExpirationDate());
			hisData.setOccurrenceDays(x.getOccurrenceDays().v());
			hisData.setUnUseDays(x.getUnUsedDays().v());
			hisData.setChkDisappeared(false);
			Optional<InterimRemain> optRemainData = remainRepo.getById(x.getRecruitmentMngId());
			if(optRemainData.isPresent()) {
				hisData.setDataAtr(EnumAdaptor.valueOf(optRemainData.get().getCreatorAtr().value, MngDataAtr.class));
				hisData.setRecDate(new CompensatoryDayoffDate(false, Optional.of(optRemainData.get().getYmd())));
			}
			lstOutputData.add(hisData);
		});	
		
		return lstOutputData;
	}
	@Override
	public List<AbsenceHistoryOutputPara> createAbsenceHis(List<InterimAbsMng> interimData) {
		List<AbsenceHistoryOutputPara> lstOuputData = new ArrayList<AbsenceHistoryOutputPara>();
		//振休管理データの件数分ループ
		// TODO khi chuyen xong domain
		
		//暫定振休管理データの件数分ループ
		if(!interimData.isEmpty()) {
			interimData.stream().forEach(x -> {
				AbsenceHistoryOutputPara hisData = new AbsenceHistoryOutputPara();
				Optional<InterimRemain> optRemainData = remainRepo.getById(x.getAbsenceMngId());
				if(optRemainData.isPresent()) {
					hisData.setAbsDate(optRemainData.get().getYmd());				
					hisData.setCreateAtr(EnumAdaptor.valueOf(optRemainData.get().getCreatorAtr().value, MngDataAtr.class));
				}
				hisData.setAbsId(x.getAbsenceMngId());
				hisData.setRequeiredDays(x.getRequeiredDays().v());
				hisData.setUnOffsetDays(x.getUnOffsetDays().v());
				lstOuputData.add(hisData);
			});
		}
		
		return lstOuputData;
	}
	@Override
	public List<RecAbsHistoryOutputPara> createRecAbsHis(List<RecruitmentHistoryOutPara> lstRecHis,
			List<AbsenceHistoryOutputPara> lstAbsHis, List<InterimRecAbsMng> lstInterimData) {
		List<RecAbsHistoryOutputPara> lstOutputPara = new ArrayList<>();
		//紐付き対象のない振出振休履歴対象情報を作成してListに追加する
		//振出履歴を抽出する
		/*List<RecruitmentHistoryOutPara> lstRecHisTmp = lstRecHis.stream().filter(z -> z.getUnUseDays() > 0)
				.collect(Collectors.toList());
		lstRecHisTmp.stream().forEach(x -> {
			RecAbsHistoryOutputPara outPutData = new RecAbsHistoryOutputPara();	
			//TODO outPutData.setYmd(x.getRecDate().getDayoffDate().isPresent()); can phai xem lai xu ly 
			outPutData.setRecHisData(Optional.of(x));
			lstOutputPara.add(outPutData);
		});
		//振休履歴を抽出する
		List<AbsenceHistoryOutputPara> lstAbsHisTmp = lstAbsHis.stream().filter(z -> z.getUnOffsetDays() > 0)
				.collect(Collectors.toList());
		lstAbsHisTmp.stream().forEach(x -> {
			RecAbsHistoryOutputPara outPutData = new RecAbsHistoryOutputPara();	
			outPutData.setYmd(x.getAbsDate());
			outPutData.setAbsHisData(Optional.of(x));
			lstOutputPara.add(outPutData);
		});
				
		// TODO 振出振休紐付け管理の件数分ループ
		
		
		List<RecAbsHistoryOutputPara> lstInterim = new ArrayList<>();
		//暫定振出振休紐付け管理の件数分ループ
		lstInterimData.stream().forEach(x ->  {
			RecAbsHistoryOutputPara outPutData = new RecAbsHistoryOutputPara();
			outPutData.setUseDays(Optional.of(x.getUseDays().v()));
			//振出データID＝暫定振出振休紐付け管理.振出管理データ
			List<RecruitmentHistoryOutPara> lstRecRuiment = lstRecHis.stream().filter(z -> x.getRecruitmentMngId() == z.getRecId())
			.collect(Collectors.toList());			
			List<RecAbsHistoryOutputPara> lstTmp = new ArrayList<>();
			if(!lstRecRuiment.isEmpty()) {
				RecruitmentHistoryOutPara recRuiment = lstRecRuiment.get(0);
				lstTmp = lstInterim.stream().filter(z -> recRuiment.getRecDate().equals(z.getYmd())).collect(Collectors.toList());
				if(lstTmp.isEmpty()) {
					//TODO outPutData.setYmd(recRuiment.getRecDate()); can phai xem lai xu ly
					outPutData.setRecHisData(Optional.of(recRuiment));
					lstInterim.add(outPutData);
				} else {
					RecAbsHistoryOutputPara tmpOutput = lstTmp.get(0);
					lstInterim.remove(tmpOutput);
					tmpOutput.setRecHisData(Optional.of(recRuiment));
					lstInterim.add(tmpOutput);
				}
			}
			
				
			//振休データID＝暫定振出振休紐付け管理.振休管理データ
			List<AbsenceHistoryOutputPara> lstAbsInterim = lstAbsHis.stream().filter(z -> x.getAbsenceMngId() == z.getAbsId())
					.collect(Collectors.toList());
			if(!lstAbsInterim.isEmpty()) {
				AbsenceHistoryOutputPara absInterim = lstAbsInterim.get(0);
				lstTmp = lstInterim.stream().filter(z -> absInterim.getAbsDate().equals(z.getYmd())).collect(Collectors.toList());
				if(lstTmp.isEmpty()) {
					outPutData.setYmd(absInterim.getAbsDate());
					outPutData.setAbsHisData(Optional.of(absInterim));
					lstInterim.add(outPutData);
				} else {
					RecAbsHistoryOutputPara tmpOutput = lstTmp.get(0);
					lstInterim.remove(tmpOutput);
					tmpOutput.setAbsHisData(Optional.of(absInterim));
					lstInterim.add(tmpOutput);
				}
			}
			
		});
		if(!lstInterim.isEmpty()) {
			lstOutputPara.addAll(lstInterim);
		}
		lstOutputPara.stream().sorted((a, b) -> b.getYmd().compareTo(a.getYmd()));*/
		return lstOutputPara;
	}
	@Override
	public AsbRemainTotalInfor getAbsRemainTotalInfor(List<RecruitmentHistoryOutPara> lstRecHis,
			List<AbsenceHistoryOutputPara> lstAbsHis) {
		AsbRemainTotalInfor outPutData = new AsbRemainTotalInfor((double) 0,(double) 0,(double) 0,(double) 0, (double) 0);
		/*List<AbsenceHistoryOutputPara> lstAbsHisRecord = lstAbsHis.stream().filter(x -> x.getCreateAtr() == MngDataAtr.RECORD).collect(Collectors.toList());
		lstAbsHisRecord.stream().forEach(x -> {
			//実績使用日数を算出する
			outPutData.setRecordUseDays(outPutData.getRecordUseDays() + x.getRequeiredDays());			
		});
		List<RecruitmentHistoryOutPara> lstRechisRecord = lstRecHis.stream().filter(x -> x.getDataAtr() == MngDataAtr.RECORD).collect(Collectors.toList());
		lstRechisRecord.stream().forEach(x -> {
			//実績発生日数を算出する
			outPutData.setRecordOccurrenceDays(outPutData.getRecordOccurrenceDays() + x.getOccurrenceDays());
		});
		
		List<AbsenceHistoryOutputPara> lstAbsHisSche = lstAbsHis.stream().filter(x -> x.getCreateAtr() == MngDataAtr.SCHEDULE).collect(Collectors.toList());
		lstAbsHisSche.stream().forEach(x -> {
			//予定使用日数を算出する
			outPutData.setScheUseDays(outPutData.getScheUseDays() + x.getRequeiredDays());
		});
		List<RecruitmentHistoryOutPara> lstRechisSche = lstRecHis.stream().filter(x -> x.getDataAtr() == MngDataAtr.SCHEDULE).collect(Collectors.toList());
		lstRechisSche.stream().forEach(x -> {
			//予定発生日数を算出する
			outPutData.setScheOccurrenceDays(outPutData.getScheOccurrenceDays() + x.getOccurrenceDays());
		});
		//振出履歴.状態＝確定済
		Double unUseDay = (double) 0;
		List<RecruitmentHistoryOutPara> lstRecHisConfirm = lstRecHis.stream().filter(x -> x.getDataAtr() == MngDataAtr.CONFIRMED).collect(Collectors.toList());
		for (RecruitmentHistoryOutPara confirmData : lstRecHisConfirm) {
			unUseDay += confirmData.getUnUseDays();
		}
		//・振休履歴.状態＝確定済
		Double unOffsetDays = (double) 0;
		List<AbsenceHistoryOutputPara> lstAbsHisConfirm = lstAbsHis.stream().filter(x -> x.getCreateAtr() == MngDataAtr.CONFIRMED).collect(Collectors.toList());
		for (AbsenceHistoryOutputPara confirmData : lstAbsHisConfirm) {
			unOffsetDays += confirmData.getUnOffsetDays();
		}
		//繰越日数：確定未使用日数－確定未相殺日数
		outPutData.setCarryForwardDays(unUseDay - unOffsetDays);*/
		return outPutData;
	}
	@Override
	public InterimRemainAggregateOutputData calAsbRemainOfCurrentMonth(String sid, DatePeriod dateData,
			InterimRemainAggregateOutputData outData) {
		//アルゴリズム「月初の振休残数を取得」を実行する 月初振休残数
		Double monthStartRemain = this.getUsedDays(sid, dateData);
		//アルゴリズム「期間内の振休消滅数合計を取得」を実行する
		outData.setMonthStartRemain(monthStartRemain);
		//アルゴリズム「期間内の振休消滅数合計を取得」を実行する
		Double extinctionDays = this.getMonthExtinctionDays(sid, dateData);
		outData.setMonthExtinction(extinctionDays);
		return outData;
	}
	@Override
	public Double useDays(String sid) {
		//ドメインモデル「振出管理データ」を取得
		List<PayoutManagementData> lstComfirmData = confirmRecMngRepo.getByStateAtr(sid, DigestionAtr.UNUSED);
		//取得した「振出管理データ」の未使用日数を合計
		Double unUseDays = (double) 0;
		for (PayoutManagementData comfirmData : lstComfirmData) {
			unUseDays += comfirmData.getUnUsedDays().v();
		}
		//ドメインモデル「振休管理データ」を取得
		List<SubstitutionOfHDManagementData> lstAbsCormfirmData = comfirmAbsMngRepo.getByRemainDays(sid, (double) 0);
		//取得した「振休管理データ」の未相殺日数を合計
		Double remainDays = (double) 0;
		for (SubstitutionOfHDManagementData absComfirmDay : lstAbsCormfirmData) {
			remainDays += absComfirmDay.getRemainDays().v();
		}
		//振休発生数合計－振休使用数合計を返す		
		return unUseDays - remainDays;
	}
	@Override
	public Double getMonthExtinctionDays(String sid, DatePeriod dateData) {
		//ドメインモデル「暫定振出管理データ」を取得する
		DatePeriod dateTmp = new DatePeriod(GeneralDate.fromString("1900/01/01", "yyyy/mm/dd"), dateData.end());
		List<InterimRemain> lstRemain = remainRepo.getRemainBySidPriod(sid, dateTmp, RemainType.PAUSE);
		List<String> lstMngId = new ArrayList<>();
		lstRemain.stream().forEach(x -> {
			lstMngId.add(x.getRemainManaID());			
		});
		List<InterimRecMng> lstInterimRecMng = recAbsRepo.getRecByIdPeriod(lstMngId, (double) 0, dateData);
		Double interimUnUseDays = (double) 0;
		for (InterimRecMng interimRecData : lstInterimRecMng) {
			interimUnUseDays += interimRecData.getUnUsedDays().v();
		}
		//ドメインモデル「振出管理データ」を取得する
		List<PayoutManagementData> lstComfirmRec = confirmRecMngRepo.getEachPeriod(sid, dateTmp, dateData,(double) 0, DigestionAtr.EXPIRED);
		
		Double confirmUnUseDays = (double) 0;
		Double interimUseDays = (double) 0;
		for (PayoutManagementData confirmData : lstComfirmRec) {
			//確定未使用日数合計を算出する
			confirmUnUseDays += confirmData.getUnUsedDays().v();
			//ドメインモデル「暫定振出振休紐付け管理」を取得する 振出管理データ＝振出管理データ.振出データID
			List<InterimRecAbsMng> optRecAbsData = recAbsRepo.getRecOrAbsMng(confirmData.getPayoutId(), true, DataManagementAtr.INTERIM);
			for (InterimRecAbsMng recAbsData : optRecAbsData) {
				interimUseDays += recAbsData.getUseDays().v();
			}			
		}
		//確定未使用日数：確定未使用日数－合計(暫定振出振休紐付け管理->使用日数)
		confirmUnUseDays -= interimUseDays;
		//振休消滅数合計を算出する
		//振休消滅数合計：確定未使用数合計＋合計(暫定振出管理データ->未使用日数)
		
		return interimUnUseDays + confirmUnUseDays;
	}
	@Override
	public AbsRecConfirmOutputPara getAbsRecConfirmData(String sid) {
		//未消化の確定振出と紐付いた確定振休を取得する
		AbsRecConfirmOutputPara outPutData = this.getUndigestedConfirm(sid);
		//未相殺の確定振休を取得する
		List<SubstitutionOfHDManagementData> getByRemainDays = comfirmAbsMngRepo.getByRemainDays(sid, (double)0);
		if(!getByRemainDays.isEmpty()) {
			outPutData.getLstAbsConfirm().addAll(getByRemainDays);
		}
		return outPutData;
	}
	
	private AbsRecConfirmOutputPara getUndigestedConfirm(String sid) {
		AbsRecConfirmOutputPara outputData = new AbsRecConfirmOutputPara(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
		//ドメインモデル「振出管理データ」を取得する
		List<PayoutManagementData> lstRecconfirm  = confirmRecMngRepo.getByStateAtr(sid, DigestionAtr.UNUSED);
		lstRecconfirm.stream().forEach(x -> {
			outputData.getLstRecConfirm().add(x);
			//ドメインモデル「振出振休紐付け管理」を取得する
			List<PayoutSubofHDManagement> lstAbsRecConfirm = confirmRecAbsRepo.getByPayoutId(x.getPayoutId());
			lstAbsRecConfirm.stream().forEach(y -> {
				outputData.getLstAbsRecConfirm().add(y);
				//ドメインモデル「振休管理データ」を取得する
				Optional<SubstitutionOfHDManagementData> absConfirm = comfirmAbsMngRepo.findByID(y.getSubOfHDID());
				absConfirm.ifPresent(z -> outputData.getLstAbsConfirm().add(z));
			});
		});
		return outputData;
		
	}

}
