package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.ArrayList;
import java.util.Collections;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.ClosureRemainPeriodOutputData;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.RemainManagementExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
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
	@Inject
	private LeaveComDayOffManaRepository typingConfirmMng;
	@Override
	public List<InterimRemainAggregateOutputData> getInterimRemainAggregate(String employeeId, GeneralDate baseDate,
			YearMonth startMonth, YearMonth endMonth) {
		List<InterimRemainAggregateOutputData> lstData = new ArrayList<>();
		//アルゴリズム「締めと残数算出対象期間を取得する」を実行する
		ClosureRemainPeriodOutputData closureData = remainManaExport.getClosureRemainPeriod(employeeId, baseDate, startMonth, endMonth);
		if(closureData == null) {
			return lstData;
		}
		//残数算出対象年月を設定する
		
		for(YearMonth ym = closureData.getStartMonth(); closureData.getEndMonth().greaterThanOrEqualTo(ym); ym = ym.addMonths(1)) {
			InterimRemainAggregateOutputData outPutData = new InterimRemainAggregateOutputData(ym, 0, 0, 0, 0, 0);
			//アルゴリズム「指定年月の締め期間を取得する」を実行する
			DatePeriod dateData = remainManaExport.getClosureOfMonthDesignation(closureData.getClosure(), ym);
			if(dateData == null) {
				continue;
			}
			//アルゴリズム「期間内の代休発生数合計を取得」を実行する
			double occurrentDays = this.getTotalOccurrenceDays(employeeId, dateData);
			//アルゴリズム「期間内の代休使用数合計を取得」を実行する
			double useDays = this.getTotalUseDays(employeeId, dateData);
			//残数算出対象年月をチェックする
			//残数算出対象年月＞締め.当月.処理年月
			//月度別代休残数集計を作成する
			outPutData.setYm(ym);
			outPutData.setMonthOccurrence(occurrentDays);
			outPutData.setMonthUse(useDays);
			if(!ym.greaterThan(closureData.getClosure().getClosureMonth().getProcessingYm())) {
				//当月の代休残数を集計する
				outPutData = this.aggregatedDayoffCurrentMonth(employeeId, dateData, outPutData);
				//月末代休残数：月初代休残数＋代休発生数合計－代休使用数合計－代休消滅数合計
				outPutData.setMonthEndRemain(outPutData.getMonthStartRemain() + occurrentDays - useDays - outPutData.getMonthExtinction());
			}
			lstData.add(outPutData);
		}
		return lstData;
	}
	@Override
	public double getTotalOccurrenceDays(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定休出管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData, RemainType.BREAK);
		double outputData = 0;
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
	public double getTotalUseDays(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定代休管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData, RemainType.SUBHOLIDAY);
		double outputData = 0;
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
		double dayOffRemainBeginMonths = this.getDayOffRemainOfBeginMonth(companyId, employeeId);
		dataOut.setMonthStartRemain(dayOffRemainBeginMonths);
		//アルゴリズム「期間内の代休消滅数合計を取得」を実行する
		double unUseDays = this.totalExtinctionRemainOfInPeriod(employeeId, dateData);
		dataOut.setMonthExtinction(unUseDays);				
		return dataOut;
	}
	@Override
	public Optional<BreakDayOffOutputHisData> getBreakDayOffData(String cid, String sid, GeneralDate baseDate) {
		//確定管理データを取得する
		BreakDayOffConfirmMngData confirmMngData = this.getConfirMngData(cid, sid, baseDate);
		//暫定管理データを取得する
		BreakDayOffInterimMngData interimMngData = this.getMngData(sid, baseDate, confirmMngData.getBreakMngData());
		if(interimMngData == null) {
			return Optional.empty();
		}
		//休出履歴を作成する
		List<BreakHistoryData> lstBreakHis = this.breakHisData(interimMngData.getLstBreakMng(), confirmMngData.getBreakMngData());
		//代休履歴を作成する
		List<DayOffHistoryData> lstDayOffHis = this.dayOffHisData(interimMngData.getLstDayOffMng(), confirmMngData.getDayOffData());
		//休出代休履歴対照情報を作成する
		List<BreakDayOffHistory> lstOutput = this.lstBreakDayOffHis(interimMngData.getLstBreakDayOffMng(), lstBreakHis, lstDayOffHis, confirmMngData.getTypingMngData());
		//残数集計情報を作成する
		AsbRemainTotalInfor totalOutput = this.totalInfor(lstBreakHis, lstDayOffHis);
		return Optional.of(new BreakDayOffOutputHisData(lstOutput, totalOutput));
	}
	@Override
	public BreakDayOffInterimMngData getMngData(String sid, GeneralDate baseDate, List<LeaveManagementData> breakMngConfirmData) {
		//対象期間を決定する
		DatePeriod dateData = remainManaExport.periodCovered(sid, baseDate);
		if(dateData == null) {
			return null;
		}
		//指定期間内に発生した暫定休出と紐付いた確定代休・暫定代休を取得する
		BreakDayOffInterimMngData outPutData = this.getMngDataToInterimData(sid, dateData);
		//指定期間内に使用した暫定代休を取得する
		List<InterimRemain> lstDayOffInterimMng = remainRepo.getRemainBySidPriod(sid, dateData, RemainType.SUBHOLIDAY);
		List<InterimDayOffMng> lstDayOffMng = new ArrayList<>(outPutData.getLstDayOffMng());
		lstDayOffInterimMng.stream().forEach(x -> {
			//  ドメインモデル「暫定代休管理データ」を取得する
			Optional<InterimDayOffMng> optDayOffMng = breakDayOffRepo.getDayoffById(x.getRemainManaID());
			optDayOffMng.ifPresent(y -> {
				lstDayOffMng.add(y);	
			});
		});
		outPutData.setLstDayOffMng(lstDayOffMng);
		//未消化の確定休出に紐付いた暫定代休を取得する
		List<InterimBreakDayOffMng> lstBreakDayOffMng = new ArrayList<>(outPutData.getLstBreakDayOffMng());
		List<InterimDayOffMng> lstDayOffMngIn = new ArrayList<>(outPutData.getLstDayOffMng());
		breakMngConfirmData.stream().forEach(z -> {
			//ドメインモデル「暫定休出代休紐付け管理」を取得する
			List<InterimBreakDayOffMng> breakDayMng = breakDayOffRepo.getBreakDayOffMng(z.getID(), true, DataManagementAtr.CONFIRM);
			breakDayMng.stream().forEach(a -> {
				lstBreakDayOffMng.add(a);
				//ドメインモデル「暫定代休管理データ」を取得する
				Optional<InterimDayOffMng> optDayOffMng = breakDayOffRepo.getDayoffById(a.getDayOffManaId());
				optDayOffMng.ifPresent(b -> {
					lstDayOffMngIn.add(b);
				});
			});
		});
		outPutData.setLstBreakDayOffMng(lstBreakDayOffMng);
		outPutData.setLstDayOffMng(lstDayOffMngIn);
		return outPutData;
	}
	@Override
	public BreakDayOffInterimMngData getMngDataToInterimData(String sid, DatePeriod dateData) {
		// ドメインモデル「暫定休出管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(sid, dateData, RemainType.BREAK);
		List<InterimBreakMng> lstBreakMng = new ArrayList<>();
		List<InterimDayOffMng> lstDayOffMng = new ArrayList<>();
		List<InterimBreakDayOffMng> lstBreakDayOffMng = new ArrayList<>();
		getRemainBySidPriod.stream().forEach(x -> {
			Optional<InterimBreakMng> getBreakMng = breakDayOffRepo.getBreakManaBybreakMngId(x.getRemainManaID());
			getBreakMng.ifPresent(a -> {
				lstBreakMng.add(a);
				//ドメインモデル「暫定休出代休紐付け管理」を取得する
				List<InterimBreakDayOffMng> breakDayMng = breakDayOffRepo.getBreakDayOffMng(a.getBreakMngId(), true, DataManagementAtr.INTERIM);
				breakDayMng.stream()
					.forEach(b -> {
						lstBreakDayOffMng.add(b);
						//ドメインモデル「暫定代休管理データ」を取得する
						Optional<InterimDayOffMng> optDayOffMng = breakDayOffRepo.getDayoffById(b.getDayOffManaId());
						optDayOffMng.ifPresent(c -> {
							lstDayOffMng.add(c);
						});
					});
			});
		});
		
		return new BreakDayOffInterimMngData(lstBreakMng, lstDayOffMng, lstBreakDayOffMng);
	}

	@Override
	public List<BreakHistoryData> breakHisData(List<InterimBreakMng> lstInterimBreakMng, List<LeaveManagementData> lstConfirmBreakMngData) {
		List<BreakHistoryData> outputData = new ArrayList<>();
		//休出管理データの件数分ループ
		lstConfirmBreakMngData.stream().forEach(a -> {
			BreakHistoryData breakData = new BreakHistoryData();
			breakData.setBreakMngId(a.getID());
			breakData.setBreakDate(a.getComDayOffDate());
			breakData.setMngAtr(MngHistDataAtr.CONFIRMED);
			breakData.setExpirationDate(a.getExpiredDate());
			breakData.setOccurrenceDays(a.getOccurredDays().v());
			breakData.setUnUseDays(a.getUnUsedDays().v());
			//確定休出に紐付いた暫定休出代休紐付け管理を抽出する
			List<InterimBreakDayOffMng> breakDayMng = breakDayOffRepo.getBreakDayOffMng(a.getID(), true, DataManagementAtr.CONFIRM);
			double useDay = 0;			
			for (InterimBreakDayOffMng typingInterimData : breakDayMng) {
				useDay += typingInterimData.getUseDays().v();
			}
			//休出履歴.未使用日数：休出管理データ.未使用日数－暫定休出使用日数
			breakData.setUnUseDays(breakData.getUnUseDays() - useDay);
			outputData.add(breakData);
		});
		
		//暫定休出管理データの件数分ループ
		lstInterimBreakMng.stream().forEach(x -> {
			BreakHistoryData breakData = new BreakHistoryData();
			breakData.setChkDisappeared(false);
			breakData.setBreakMngId(x.getBreakMngId());
			breakData.setExpirationDate(x.getExpirationDate());
			breakData.setUnUseDays(x.getUnUsedDays().v());
			breakData.setOccurrenceDays(x.getOccurrenceDays().v());
			remainRepo.getById(x.getBreakMngId()).ifPresent(y -> {
				MngHistDataAtr atr = MngHistDataAtr.NOTREFLECT;
				if(y.getCreatorAtr() == CreateAtr.RECORD) {
					atr = MngHistDataAtr.RECORD;
				} else if (y.getCreatorAtr() == CreateAtr.SCHEDULE) {
					atr = MngHistDataAtr.SCHEDULE;
				}
				breakData.setMngAtr(atr);
				breakData.setBreakDate(new CompensatoryDayoffDate(false, Optional.of(y.getYmd())));
			});
			outputData.add(breakData);
		});
		return outputData;
	}
	@Override
	public List<DayOffHistoryData> dayOffHisData(List<InterimDayOffMng> lstInterimDayOffMng, List<CompensatoryDayOffManaData> lstConfrimDayOffMng) {
		List<DayOffHistoryData> outPutData = new ArrayList<>();
		//代休管理データの件数分ループ
		lstConfrimDayOffMng.stream().forEach(a -> {
			DayOffHistoryData dayOffData = new DayOffHistoryData();
			dayOffData.setDayOffId(a.getComDayOffID());
			dayOffData.setDayOffDate(a.getDayOffDate());
			dayOffData.setCreateAtr(MngHistDataAtr.CONFIRMED);
			dayOffData.setRequeiredDays(a.getRequireDays().v());
			dayOffData.setUnOffsetDays(a.getRemainDays().v());
			//確定代休に紐付いた暫定休出代休紐付け管理を抽出する
			List<InterimBreakDayOffMng> breakDayMng = breakDayOffRepo.getBreakDayOffMng(a.getComDayOffID(), false, DataManagementAtr.CONFIRM);
			double useDay = 0;
			for (InterimBreakDayOffMng typingInterimData : breakDayMng) {
				useDay += typingInterimData.getUseDays().v();
			}
			//振休履歴.未相殺日数：代休管理データ.未相殺日数－暫定代休使用日数
			dayOffData.setUnOffsetDays(dayOffData.getUnOffsetDays() - useDay);
			outPutData.add(dayOffData);
		});
		
		//暫定代休管理データの件数分ループ
		lstInterimDayOffMng.stream().forEach(x -> {
			DayOffHistoryData dayOffData = new DayOffHistoryData();
			Optional<InterimRemain> remainData = remainRepo.getById(x.getDayOffManaId());
			remainData.ifPresent(y -> {
				MngHistDataAtr atr = MngHistDataAtr.NOTREFLECT;
				if(y.getCreatorAtr() == CreateAtr.RECORD) {
					atr = MngHistDataAtr.RECORD;
				} else if (y.getCreatorAtr() == CreateAtr.SCHEDULE) {
					atr = MngHistDataAtr.SCHEDULE;
				}
				dayOffData.setCreateAtr(atr);
				dayOffData.setDayOffDate(new CompensatoryDayoffDate(false, Optional.of(y.getYmd())));
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
			List<DayOffHistoryData> lstDayOffHis, List<LeaveComDayOffManagement> lstTypingConfrimMng) {
		List<BreakDayOffHistory> lstOutputData = new ArrayList<>();
		//紐付き対象のない休出代休履歴対象情報を作成してListに追加する
		//休出履歴を抽出する ・  休出管理データ.未使用日数＞0
		List<BreakHistoryData> lstBreakUnUse = lstBreakHis.stream().filter(x -> x.getUnUseDays() > 0).collect(Collectors.toList());
		for (BreakHistoryData x : lstBreakUnUse) {
			BreakDayOffHistory outData = new BreakDayOffHistory();
			outData.setHisDate(x.getBreakDate());
			outData.setBreakHis(Optional.of(x));
			lstOutputData.add(outData);
		}
		//代休履歴を抽出する ・代休管理データ.未相殺日数＞0
		List<DayOffHistoryData> lstDayOffUnUse = lstDayOffHis.stream().filter(x -> x.getUnOffsetDays() > 0).collect(Collectors.toList());
		for (DayOffHistoryData x : lstDayOffUnUse) {
			BreakDayOffHistory outData = new BreakDayOffHistory();
			outData.setHisDate(x.getDayOffDate());
			outData.setDayOffHis(Optional.of(x));
			lstOutputData.add(outData);
		}
		
		//休出代休紐付け管理の件数分ループ
		for (LeaveComDayOffManagement typingConfrimData : lstTypingConfrimMng) {
			BreakDayOffHistory outData = new BreakDayOffHistory();
			outData.setUseDays(typingConfrimData.getUsedDays().v());
			//休出履歴を抽出する
			List<BreakHistoryData> lstBreakHisConfirm = lstBreakHis.stream().filter(x -> x.getBreakMngId().equals(typingConfrimData.getLeaveID()))
					.collect(Collectors.toList());
			for (BreakHistoryData breakHisData : lstBreakHisConfirm) {
				outData.setHisDate(breakHisData.getBreakDate());
				outData.setBreakHis(Optional.of(breakHisData));
			}
			//代休履歴を抽出する
			List<DayOffHistoryData> lstDayoffHisConfrim = lstDayOffHis.stream()
					.filter(y -> y.getDayOffId().equals(typingConfrimData.getComDayOffID()))
					.collect(Collectors.toList());
			for (DayOffHistoryData dayOffHisData : lstDayoffHisConfrim) {
				outData.setDayOffHis(Optional.of(dayOffHisData));
			}
			lstOutputData.add(outData);
		}
		
		//暫定休出代休紐付け管理の件数分ループ
		for (InterimBreakDayOffMng x : lstInterimBreakDayOff) {
			BreakDayOffHistory outData = new BreakDayOffHistory();
			outData.setUseDays(x.getUseDays().v());
			List<BreakHistoryData> breakHisData = lstBreakHis.stream()
					.filter(y -> y.getBreakMngId() == x.getBreakManaId())
					.collect(Collectors.toList());
			for (BreakHistoryData z : breakHisData) {
				outData.setBreakHis(Optional.of(z));
				outData.setHisDate(z.getBreakDate());
				//lstOutputData.add(outData);
			}
			List<DayOffHistoryData> dayOffHisData = lstDayOffHis.stream()
					.filter(b -> b.getDayOffId() == x.getDayOffManaId())
					.collect(Collectors.toList());
			for (DayOffHistoryData z : dayOffHisData) {			
				outData.setDayOffHis(Optional.of(z));
				//outData.setHisDate(z.getDayOffDate());
				//lstOutputData.add(outData);
			}
			lstOutputData.add(outData);
		}
		lstOutputData = lstOutputData.stream().sorted((a, b) -> a.getHisDate().getDayoffDate().isPresent() 
				? a.getHisDate().getDayoffDate().get().compareTo(b.getHisDate().getDayoffDate().isPresent() ? b.getHisDate().getDayoffDate().get() : GeneralDate.max())
				: GeneralDate.max().compareTo(GeneralDate.max())).collect(Collectors.toList());
		return lstOutputData;
	}
	@Override
	public AsbRemainTotalInfor totalInfor(List<BreakHistoryData> lstBreakHis, List<DayOffHistoryData> lstDayOffHis) {
		AsbRemainTotalInfor outputData = new AsbRemainTotalInfor(0, 0, 0, 0, 0);
		//実績使用日数を算出する
		List<DayOffHistoryData> dayOffHisRecord = lstDayOffHis.stream()
				.filter(x -> x.getCreateAtr() == MngHistDataAtr.RECORD)
				.collect(Collectors.toList());
		dayOffHisRecord.stream().forEach(y -> {
			outputData.setRecordUseDays(outputData.getRecordUseDays() + y.getRequeiredDays());
		});
		//実績発生日数を算出する
		List<BreakHistoryData> breakHisRecord = lstBreakHis.stream()
				.filter(x -> x.getMngAtr() == MngHistDataAtr.RECORD)
				.collect(Collectors.toList());
		breakHisRecord.stream().forEach(y -> {
			outputData.setRecordOccurrenceDays(outputData.getRecordOccurrenceDays() + y.getOccurrenceDays());
		});
		//予定使用日数を算出する
		List<DayOffHistoryData> dayOffHisSche = lstDayOffHis.stream()
				.filter(x -> x.getCreateAtr() == MngHistDataAtr.SCHEDULE
							|| x.getCreateAtr() == MngHistDataAtr.NOTREFLECT 
							)
				.collect(Collectors.toList());
		dayOffHisSche.stream().forEach(y -> {
			outputData.setScheUseDays(outputData.getScheUseDays() + y.getRequeiredDays());
		});
		//予定発生日数を算出する
		List<BreakHistoryData> breakHisSche = lstBreakHis.stream()
				.filter(x -> x.getMngAtr() == MngHistDataAtr.SCHEDULE
						|| x.getMngAtr() == MngHistDataAtr.NOTREFLECT)
				.collect(Collectors.toList());
		breakHisSche.stream().forEach(y -> {
			outputData.setScheOccurrenceDays(outputData.getScheOccurrenceDays() + y.getOccurrenceDays());
		});
		//繰越数を算出する
		List<BreakHistoryData> breakHisCarry = lstBreakHis.stream()
				.filter(x -> x.getMngAtr() == MngHistDataAtr.CONFIRMED)
				.collect(Collectors.toList());
		double carryDays = 0;
		for (BreakHistoryData breakHistoryData : breakHisCarry) {
			carryDays += breakHistoryData.getUnUseDays();
		}
		List<DayOffHistoryData> dayOffHisCarry = lstDayOffHis.stream()
				.filter(x -> x.getCreateAtr() == MngHistDataAtr.CONFIRMED)
				.collect(Collectors.toList());
		double carryDayOff = 0;
		for (DayOffHistoryData dayOffHistoryData : dayOffHisCarry) {
			carryDayOff += dayOffHistoryData.getUnOffsetDays();
		}
		outputData.setCarryForwardDays(carryDays - carryDayOff);
		return outputData;
	}
	@Override
	public double getDayOffRemainOfBeginMonth(String cid, String sid) {
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
	public double totalExtinctionRemainOfInPeriod(String sid, DatePeriod dateData) {
		DatePeriod tmpDateData = new DatePeriod(GeneralDate.min(), dateData.end()); 
		List<InterimRemain> lstInterimData = remainRepo.getRemainBySidPriod(sid, tmpDateData, RemainType.SUBHOLIDAY);
		List<String> lstMngId = new ArrayList<>();
		lstInterimData.stream().forEach(x ->{
			lstMngId.add(x.getRemainManaID());
		});
		//ドメインモデル「暫定休出管理データ」を取得する
		List<InterimBreakMng> lstBreakMng = breakDayOffRepo.getByPeriod(lstMngId, 0, dateData);
		double interimUnUseDays = 0;
		for (InterimBreakMng breakMng : lstBreakMng) {
			interimUnUseDays += breakMng.getUnUsedDays().v();
		}
		//ドメインモデル「休出管理データ」を取得する
		List<LeaveManagementData> lstLeaveMng = leaveManaDataRepo.getByExtinctionPeriod(sid, tmpDateData, dateData, 0, DigestionAtr.EXPIRED);
		double unUseDays = 0;
		double useDays = 0;
		for (LeaveManagementData leaveMng : lstLeaveMng) {
			//確定未使用日数合計を算出する
			//確定未使用日数：合計(休出管理データ->未使用日数)
			unUseDays += leaveMng.getUnUsedDays().v();
			//ドメインモデル「暫定休出代休紐付け管理」を取得する
			List<InterimBreakDayOffMng> optBreakDayOff = breakDayOffRepo.getBreakDayOffMng(leaveMng.getID(), true, DataManagementAtr.CONFIRM);
			for (InterimBreakDayOffMng interimMng : optBreakDayOff) {
				useDays += interimMng.getUseDays().v();
			}
			
		}
		//確定未使用日数：確定未使用日数－合計(暫定休出代休紐付け管理->使用日数)
		double confirmUnUseDays = unUseDays - useDays;
		//代休消滅数合計：確定未使用数合計＋合計(暫定休出管理データ->未使用日数)
		return confirmUnUseDays + interimUnUseDays;
	}
	@Override
	public BreakDayOffConfirmMngData getConfirMngData(String cid, String sid, GeneralDate baseDate) {
		//未消化の確定休出と紐付いた確定代休を取得する
		//ドメインモデル「休出管理データ」を取得する
		List<LeaveManagementData> lstBreakData = leaveManaDataRepo.getBySidWithsubHDAtr(cid, sid, DigestionAtr.UNUSED.value);
		List<LeaveComDayOffManagement> breakTypingMng = new ArrayList<>();
		List<CompensatoryDayOffManaData> dayOffData  = new ArrayList<>();
		for (LeaveManagementData x : lstBreakData) {
			//ドメインモデル「休出代休紐付け管理」を取得する
			List<LeaveComDayOffManagement> breakTypingMngTmp =  typingConfirmMng.getByLeaveID(x.getID());			
			breakTypingMngTmp.stream().forEach(y -> {
				//ドメインモデル「代休管理データ」を取得する
				Optional<CompensatoryDayOffManaData> optDayoffMng = leaveDayOffRepo.getBycomdayOffId(y.getComDayOffID());
				optDayoffMng.ifPresent(z -> {
					dayOffData.add(z);
				});
				breakTypingMng.add(y);
			});
		}
		//未相殺の確定代休を取得する
		//ドメインモデル「代休管理データ」を取得する
		List<CompensatoryDayOffManaData> lstDayOffMngByUnOffsetDays =  leaveDayOffRepo.getByReDay(cid, sid);
		if(!lstDayOffMngByUnOffsetDays.isEmpty()) {
			dayOffData.addAll(lstDayOffMngByUnOffsetDays);
		}
		return new BreakDayOffConfirmMngData(breakTypingMng, lstBreakData, dayOffData);
	}
	

}
