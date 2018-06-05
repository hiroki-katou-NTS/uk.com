package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AsbRemainTotalInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.ClosureRemainPeriodOutputData;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.RemainManagementExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class BreakDayOffManagementQueryImpl implements BreakDayOffManagementQuery{
	@Inject
	private RemainManagementExport remainManaExport;
	@Inject
	private InterimBreakDayOffMngRepository breakDayOffRepo;
	@Inject
	private InterimRemainRepository remainRepo;
	@Inject
	private LeaveManaDataRepository leaveManaDataRepo;
	@Inject
	private ComDayOffManaDataRepository leaveDayOffRepo;
	@Override
	public List<InterimRemainAggregateOutputData> getInterimRemainAggregate(String employeeId, GeneralDate baseDate,
			YearMonth startMonth, YearMonth endMonth) {
		
		//アルゴリズム「締めと残数算出対象期間を取得する」を実行する
		ClosureRemainPeriodOutputData closureData = remainManaExport.getClosureRemainPeriod(employeeId, baseDate, startMonth, endMonth);
		//残数算出対象年月を設定する
		List<InterimRemainAggregateOutputData> lstData = new ArrayList<>(); 
		for(YearMonth ym = closureData.getStartMonth(); closureData.getEndMonth().greaterThanOrEqualTo(ym); ym.addMonths(1)) {
			InterimRemainAggregateOutputData outPutData = new InterimRemainAggregateOutputData(ym, (double) 0, (double) 0, (double) 0, (double) 0, (double) 0);
			/*//アルゴリズム「指定年月の締め期間を取得する」を実行する
			DatePeriod dateData = remainManaExport.getClosureOfMonthDesignation(closureData.getClosure(), ym);
			//アルゴリズム「期間内の代休発生数合計を取得」を実行する
			Double occurrentDays = this.getTotalOccurrenceDays(employeeId, dateData);
			//アルゴリズム「期間内の代休使用数合計を取得」を実行する
			Double useDays = this.getTotalUseDays(employeeId, dateData);
			//残数算出対象年月をチェックする
			//残数算出対象年月＞締め.当月.処理年月
			if(ym.greaterThan(closureData.getClosure().getClosureMonth().getProcessingYm())) {
				//月度別代休残数集計を作成する
				outPutData.setYm(ym);
				outPutData.setMonthOccurrence(occurrentDays);
				outPutData.setMonthUse(useDays);
			} else {
				//当月の代休残数を集計する
				outPutData = this.aggregatedDayoffCurrentMonth(employeeId, dateData, outPutData);
				//月末代休残数：月初代休残数＋代休発生数合計－代休使用数合計－代休消滅数合計
				outPutData.setMonthEndRemain(outPutData.getMonthStartRemain() + occurrentDays - useDays - outPutData.getMonthExtinction());
			}*/
			lstData.add(outPutData);
		}
		return lstData;
	}
	@Override
	public Double getTotalOccurrenceDays(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定休出管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData, RemainType.BREAKDAYOFF);
		Double outputData = (double) 0;
		for (InterimRemain interimRemain : getRemainBySidPriod) {
			Optional<InterimBreakMng> getBreakManaBybreakManaId = breakDayOffRepo.getBreakManaBybreakMngId(interimRemain.getRemainManaID());
			if(getBreakManaBybreakManaId.isPresent()) {
				outputData += getBreakManaBybreakManaId.get().getOccurrenceDays().v();
			}
		}		
		//ドメインモデル「休出管理データ」を取得する
		List<LeaveManagementData> lstLeaveData = leaveManaDataRepo.getByDayOffDatePeriod(employeeId, dateData);
		for (LeaveManagementData leaveData : lstLeaveData) {
			outputData += leaveData.getOccurredDays().v();
		}
		return outputData;
	}
	@Override
	public Double getTotalUseDays(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定代休管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData, RemainType.BREAKDAYOFF);
		Double outputData = (double) 0;
		for (InterimRemain interimRemain : getRemainBySidPriod) {
			Optional<InterimDayOffMng> getDayoffById = breakDayOffRepo.getDayoffById(interimRemain.getRemainManaID());
			if(getDayoffById.isPresent()) {
				outputData += getDayoffById.get().getRequiredDay().v();
			}			
		}
		//ドメインモデル「代休管理データ」を取得する
		List<CompensatoryDayOffManaData> lstLeaveData = leaveDayOffRepo.getByDayOffDatePeriod(employeeId, dateData);
		for (CompensatoryDayOffManaData leaveData : lstLeaveData) {
			outputData += leaveData.getRequireDays().v();
		}
		return outputData;
	}
	@Override
	public InterimRemainAggregateOutputData aggregatedDayoffCurrentMonth(String employeeId, DatePeriod dateData, InterimRemainAggregateOutputData dataOut) {
		String companyId = AppContexts.user().companyId();
		//アルゴリズム「月初の代休残数を取得」を実行する
		Double dayOffRemainBeginMonths = this.getDayOffRemainOfBeginMonth(companyId, employeeId);
		dataOut.setMonthStartRemain(dayOffRemainBeginMonths);
		//アルゴリズム「期間内の代休消滅数合計を取得」を実行する
		Double unUseDays = this.totalExtinctionRemainOfInPeriod(employeeId, dateData);
		dataOut.setMonthExtinction(unUseDays);				
		return dataOut;
	}
	@Override
	public BreakDayOffOutputHisData getBreakDayOffData(String cid, String sid, GeneralDate baseDate) {
		// TODO 確定管理データを取得する
		
		//暫定管理データを取得する
		BreakDayOffInterimMngData interimMngData = this.getMngData(sid, baseDate);
		//休出履歴を作成する
		List<BreakHistoryData> lstBreakHis = this.breakHisData(interimMngData.getLstBreakMng());
		//代休履歴を作成する
		List<DayOffHistoryData> lstDayOffHis = this.dayOffHisData(interimMngData.getLstDayOffMng());
		//休出代休履歴対照情報を作成する
		List<BreakDayOffHistory> lstOutput = this.lstBreakDayOffHis(interimMngData.getLstBreakDayOffMng(), lstBreakHis, lstDayOffHis);
		//残数集計情報を作成する
		AsbRemainTotalInfor totalOutput = this.totalInfor(lstBreakHis, lstDayOffHis);
		
		return new BreakDayOffOutputHisData(lstOutput, totalOutput);
	}
	@Override
	public BreakDayOffInterimMngData getMngData(String sid, GeneralDate baseDate) {
		//対象期間を決定する
		DatePeriod dateData = remainManaExport.periodCovered(sid, baseDate);
		//指定期間内に発生した暫定休出と紐付いた確定代休・暫定代休を取得する
		BreakDayOffInterimMngData outPutData = this.getMngDataToInterimData(sid, dateData);
		//TODO 未消化の確定休出に紐付いた暫定代休を取得する
		
		return outPutData;
	}
	@Override
	public BreakDayOffInterimMngData getMngDataToInterimData(String sid, DatePeriod dateData) {
		BreakDayOffInterimMngData outPutData = new BreakDayOffInterimMngData();
		// ドメインモデル「暫定休出管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(sid, dateData, RemainType.BREAKDAYOFF);
		getRemainBySidPriod.stream().forEach(x -> {
			Optional<InterimBreakMng> getBreakMng = breakDayOffRepo.getBreakManaBybreakMngId(x.getRemainManaID());
			getBreakMng.ifPresent(a -> {
				outPutData.lstBreakMng.add(a);
				//ドメインモデル「暫定休出代休紐付け管理」を取得する
				breakDayOffRepo.getBreakDayOffMng(a.getBreakMngId(), true, DataManagementAtr.INTERIM).ifPresent(b -> {
					outPutData.lstBreakDayOffMng.add(b);
					//ドメインモデル「暫定代休管理データ」を取得する
					breakDayOffRepo.getDayoffById(b.getDayOffManaId()).ifPresent(c -> {
						outPutData.lstDayOffMng.add(c);
					});
				});
			});
		});
		
		return outPutData;
	}
	@Override
	public BreakDayOffInterimMngData getNotInterimDayOffMng(String sid, DatePeriod dateData,
			BreakDayOffInterimMngData mngData) {
		// TODO lam khi chuyen branch
		
		return null;
	}
	@Override
	public List<BreakHistoryData> breakHisData(List<InterimBreakMng> lstInterimBreakMng) {
		List<BreakHistoryData> outputData = new ArrayList<>();
		// TODO 休出管理データの件数分ループ
		
		
		//暫定休出管理データの件数分ループ
		lstInterimBreakMng.stream().forEach(x -> {
			BreakHistoryData breakData = new BreakHistoryData();
			breakData.setChkDisappeared(false);
			breakData.setBreakMngId(x.getBreakMngId());
			breakData.setExpirationDate(x.getExpirationDate());
			breakData.setUnUseDays(x.getUnUsedDays().v());
			breakData.setOccurrenceDays(x.getOccurrenceDays().v());
			remainRepo.getById(x.getBreakMngId()).ifPresent(y -> {
				breakData.setMngAtr(EnumAdaptor.valueOf(y.getCreatorAtr().value, MngDataAtr.class));
				breakData.setBreakDate(y.getYmd());
			});
			outputData.add(breakData);
		});
		return outputData;
	}
	@Override
	public List<DayOffHistoryData> dayOffHisData(List<InterimDayOffMng> lstInterimDayOffMng) {
		List<DayOffHistoryData> outPutData = new ArrayList<>();
		// TODO 代休管理データの件数分ループ
		
		//暫定代休管理データの件数分ループ
		lstInterimDayOffMng.stream().forEach(x -> {
			DayOffHistoryData dayOffData = new DayOffHistoryData();
			remainRepo.getById(x.getDayOffManaId()).ifPresent(y -> {
				dayOffData.setCreateAtr(EnumAdaptor.valueOf(y.getCreatorAtr().value, MngDataAtr.class));
				dayOffData.setDayOffDate(y.getYmd());
			});
			dayOffData.setDayOffId(x.getDayOffManaId());
			dayOffData.setRequeiredDays(x.getRequiredDay().v());
			dayOffData.setUnOffsetDays(x.getUnOffsetDay().v());
			outPutData.add(dayOffData);
		});
		return outPutData;
	}
	@Override
	public List<BreakDayOffHistory> lstBreakDayOffHis(List<InterimBreakDayOffMng> lstInterimBreakDayOff, List<BreakHistoryData> lstBreakHis, 
			List<DayOffHistoryData> lstDayOffHis) {
		List<BreakDayOffHistory> lstOutputData = new ArrayList<>();
		//紐付き対象のない休出代休履歴対象情報を作成してListに追加する
		//休出履歴を抽出する ・  休出管理データ.未使用日数＞0
		List<BreakHistoryData> lstBreakUnUse = lstBreakHis.stream().filter(x -> x.getUnUseDays() > 0).collect(Collectors.toList());
		lstBreakUnUse.stream().forEach(x -> {
			BreakDayOffHistory outData = new BreakDayOffHistory();
			outData.setHisDate(x.getBreakDate());
			outData.setBreakHis(Optional.of(x));
			lstOutputData.add(outData);
		});
		//代休履歴を抽出する ・代休管理データ.未相殺日数＞0
		List<DayOffHistoryData> lstDayOffUnUse = lstDayOffHis.stream().filter(x -> x.getUnOffsetDays() > 0).collect(Collectors.toList());
		lstDayOffUnUse.stream().forEach(x -> {
			BreakDayOffHistory outData = new BreakDayOffHistory();
			outData.setHisDate(x.getDayOffDate());
			outData.setDayOffHis(Optional.of(x));
			lstOutputData.add(outData);
		});		
		
		// TODO 休出代休紐付け管理の件数分ループ
		
		//暫定休出代休紐付け管理の件数分ループ
		List<BreakDayOffHistory> lstInterimOutput = new ArrayList<>();
		lstInterimBreakDayOff.stream().forEach(x -> {
			BreakDayOffHistory outData = new BreakDayOffHistory();
			outData.setUseDays(x.getUseDays().v());
			
			List<BreakHistoryData> breakHisData = lstBreakHis.stream()
					.filter(y -> y.getBreakMngId() == x.getBreakManaId())
					.collect(Collectors.toList());
			breakHisData.stream().forEach(z -> {
				List<BreakDayOffHistory> lstTmp = lstInterimOutput.stream().filter(a -> a.getHisDate().equals(z.getBreakDate())).collect(Collectors.toList());
				if(lstTmp.isEmpty()) {
					outData.setBreakHis(Optional.of(z));
					outData.setHisDate(z.getBreakDate());
					lstInterimOutput.add(outData);
				} else {
					BreakDayOffHistory tmpData = lstTmp.get(0);
					lstOutputData.remove(tmpData);
					tmpData.setBreakHis(Optional.of(z));
					lstInterimOutput.add(tmpData);
				}
			});
			
			List<DayOffHistoryData> dayOffHisData = lstDayOffHis.stream()
					.filter(b -> b.getDayOffId() == x.getDayOffManaId())
					.collect(Collectors.toList());
			dayOffHisData.stream().forEach(z -> {
				List<BreakDayOffHistory> lstTmp = lstInterimOutput.stream().filter(a -> a.getHisDate().equals(z.getDayOffDate())).collect(Collectors.toList());
				if(lstTmp.isEmpty()) {
					outData.setDayOffHis(Optional.of(z));
					outData.setHisDate(z.getDayOffDate());
					lstInterimOutput.add(outData);
				} else {
					BreakDayOffHistory tmpData = lstTmp.get(0);
					lstOutputData.remove(tmpData);
					tmpData.setDayOffHis(Optional.of(z));
					lstOutputData.add(tmpData);
				}
			});
		});
		if(!lstInterimOutput.isEmpty()) {
			lstOutputData.addAll(lstInterimOutput);	
		}
		Collections.sort(lstOutputData, Comparator.comparing(BreakDayOffHistory :: getHisDate));
		return lstOutputData;
	}
	@Override
	public AsbRemainTotalInfor totalInfor(List<BreakHistoryData> lstBreakHis, List<DayOffHistoryData> lstDayOffHis) {
		AsbRemainTotalInfor outputData = new AsbRemainTotalInfor((double) 0, (double)0, (double) 0, (double) 0, (double) 0);
		//実績使用日数を算出する
		List<DayOffHistoryData> dayOffHisRecord = lstDayOffHis.stream()
				.filter(x -> x.getCreateAtr() == MngDataAtr.RECORD)
				.collect(Collectors.toList());
		dayOffHisRecord.stream().forEach(y -> {
			outputData.setRecordUseDays(outputData.getRecordUseDays() + y.getRequeiredDays());
		});
		//実績発生日数を算出する
		List<BreakHistoryData> breakHisRecord = lstBreakHis.stream()
				.filter(x -> x.getMngAtr() == MngDataAtr.RECORD)
				.collect(Collectors.toList());
		breakHisRecord.stream().forEach(y -> {
			outputData.setRecordOccurrenceDays(outputData.getRecordOccurrenceDays() + y.getOccurrenceDays());
		});
		//予定使用日数を算出する
		List<DayOffHistoryData> dayOffHisSche = lstDayOffHis.stream()
				.filter(x -> x.getCreateAtr() == MngDataAtr.SCHEDULE)
				.collect(Collectors.toList());
		dayOffHisSche.stream().forEach(y -> {
			outputData.setScheUseDays(outputData.getScheUseDays() + y.getRequeiredDays());
		});
		//予定発生日数を算出する
		List<BreakHistoryData> breakHisSche = lstBreakHis.stream()
				.filter(x -> x.getMngAtr() == MngDataAtr.SCHEDULE)
				.collect(Collectors.toList());
		breakHisSche.stream().forEach(y -> {
			outputData.setScheOccurrenceDays(outputData.getScheOccurrenceDays() + y.getOccurrenceDays());
		});
		//繰越数を算出する
		List<BreakHistoryData> breakHisCarry = lstBreakHis.stream()
				.filter(x -> x.getMngAtr() == MngDataAtr.CONFIRMED)
				.collect(Collectors.toList());
		Double carryDays = (double) 0;
		for (BreakHistoryData breakHistoryData : breakHisCarry) {
			carryDays += breakHistoryData.getUnUseDays();
		}
		List<DayOffHistoryData> dayOffHisCarry = lstDayOffHis.stream()
				.filter(x -> x.getCreateAtr() == MngDataAtr.CONFIRMED)
				.collect(Collectors.toList());
		Double carryDayOff = (double) 0;
		for (DayOffHistoryData dayOffHistoryData : dayOffHisCarry) {
			carryDayOff += dayOffHistoryData.getUnOffsetDays();
		}
		outputData.setCarryForwardDays(carryDays - carryDayOff);
		return outputData;
	}
	@Override
	public Double getDayOffRemainOfBeginMonth(String cid, String sid) {
		//ドメインモデル「休出管理データ」を取得
		List<LeaveManagementData> lstLeaveData = leaveManaDataRepo.getBySidWithsubHDAtr(cid, sid, DigestionAtr.UNUSED.value);
		Double unUseDays = (double) 0;
		//取得した「休出管理データ」の未使用日数を合計
		for (LeaveManagementData leaveData : lstLeaveData) {
			unUseDays += leaveData.getUnUsedDays().v();
		}
		//ドメインモデル「代休管理データ」を取得
		List<CompensatoryDayOffManaData> lstLeaveDayOffData = leaveDayOffRepo.getBySidWithReDay(cid, sid);
		Double unOffSet = (double) 0;
		//取得した「代休管理データ」の未相殺日数を合計
		for (CompensatoryDayOffManaData leaveDayOffData : lstLeaveDayOffData) {			
			unOffSet += leaveDayOffData.getRemainDays().v();
		}
		//代休発生数合計－代休使用数合計を返す
		return unUseDays - unOffSet;
	}
	@Override
	public Double totalExtinctionRemainOfInPeriod(String sid, DatePeriod dateData) {
		DatePeriod tmpDateData = new DatePeriod(GeneralDate.fromString("1900/01/01", "yyyy/mm/dd"), dateData.end()); 
		List<InterimRemain> lstInterimData = remainRepo.getRemainBySidPriod(sid, tmpDateData, RemainType.BREAKDAYOFF);
		List<String> lstMngId = new ArrayList<>();
		lstInterimData.stream().forEach(x ->{
			lstMngId.add(x.getRemainManaID());
		});
		//ドメインモデル「暫定休出管理データ」を取得する
		List<InterimBreakMng> lstBreakMng = breakDayOffRepo.getByPeriod(lstMngId, (double)0, dateData);
		Double interimUnUseDays = (double) 0;
		for (InterimBreakMng breakMng : lstBreakMng) {
			interimUnUseDays += breakMng.getUnUsedDays().v();
		}
		//ドメインモデル「休出管理データ」を取得する
		List<LeaveManagementData> lstLeaveMng = leaveManaDataRepo.getByExtinctionPeriod(sid, tmpDateData, dateData, (double) 0, DigestionAtr.UNUSED);
		Double unUseDays = (double) 0;
		Double useDays =  (double) 0;
		for (LeaveManagementData leaveMng : lstLeaveMng) {
			//確定未使用日数合計を算出する
			//確定未使用日数：合計(休出管理データ->未使用日数)
			unUseDays += leaveMng.getUnUsedDays().v();
			//ドメインモデル「暫定休出代休紐付け管理」を取得する
			Optional<InterimBreakDayOffMng> optBreakDayOff = breakDayOffRepo.getBreakDayOffMng(leaveMng.getID(), true, DataManagementAtr.CONFIRM);
			if(optBreakDayOff.isPresent()) {
				useDays += optBreakDayOff.get().getUseDays().v();
			}
		}
		//確定未使用日数：確定未使用日数－合計(暫定休出代休紐付け管理->使用日数)
		Double confirmUnUseDays = unUseDays - useDays;
		//代休消滅数合計：確定未使用数合計＋合計(暫定休出管理データ->未使用日数)
		return confirmUnUseDays + interimUnUseDays;
	}
	

}
