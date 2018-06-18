package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreaterAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class BreakDayOffMngInPeriodQueryImpl implements BreakDayOffMngInPeriodQuery{
	@Inject
	private ComDayOffManaDataRepository dayOffConfirmRepo;
	@Inject
	private InterimBreakDayOffMngRepository breakDayOffInterimRepo;
	@Inject
	private LeaveManaDataRepository breakConfrimRepo;
	@Inject
	private InterimRemainRepository interimRemainRepo;
	@Override
	public BreakDayOffRemainMngOfInPeriod getBreakDayOffMngInPeriod(BreakDayOffRemainMngParam inputParam) {
		//アルゴリズム「未相殺の代休(確定)を取得する」を実行する
		List<BreakDayOffDetail> lstDetailData = this.getConfirmDayOffDetail(inputParam.getSid());
		//アルゴリズム「未使用の休出(確定)を取得する」を実行する
		List<BreakDayOffDetail> lstBreakData = this.getConfirmBreakDetail(inputParam.getSid());
		if(!lstBreakData.isEmpty()) {
			lstDetailData.addAll(lstBreakData);
		}
		//TODO 繰越数を計算する
		
		//3.未相殺の代休(暫定)を取得する
		List<BreakDayOffDetail> lstInterimDayOffDetail = this.lstInterimDayOffDetail(inputParam);
		if(!lstInterimDayOffDetail.isEmpty()) {
			lstDetailData.addAll(lstInterimDayOffDetail);
		}
		//アルゴリズム「未使用の休出(暫定)を取得する」を実行する
		
		return null;
	}

	@Override
	public List<BreakDayOffDetail> getConfirmDayOffDetail(String sid) {
		List<BreakDayOffDetail> lstOutputData = new ArrayList<>();
		//アルゴリズム「確定代休から未相殺の代休を取得する」を実行する
		List<CompensatoryDayOffManaData> lstDayOffConfirmData = this.lstConfirmDayOffData(sid);
		for (CompensatoryDayOffManaData dayOffConfirmData : lstDayOffConfirmData) {
			//1-3.暫定休出と紐付けをしない確定代休を取得する
			BreakDayOffDetail dataDetail = this.getConfirmDayOffData(dayOffConfirmData, sid);
			if(dataDetail != null) {
				lstOutputData.add(dataDetail);
			}
		}
		return lstOutputData;
	}

	@Override
	public List<CompensatoryDayOffManaData> lstConfirmDayOffData(String sid) {
		//ドメインモデル「代休管理データ」
		String cid = AppContexts.user().companyId();
		List<CompensatoryDayOffManaData> lstDayOffConfirm =  dayOffConfirmRepo.getBySid(cid, sid);
		
		return lstDayOffConfirm.stream().filter(x -> x.getRemainDays().v() > 0 || x.getRemainTimes().v() > 0)
				.collect(Collectors.toList());
	}

	@Override
	public BreakDayOffDetail getConfirmDayOffData(CompensatoryDayOffManaData dayoffConfirmData, String sid) {
		BreakDayOffDetail detail = new BreakDayOffDetail();
		//ドメインモデル「暫定休出代休紐付け管理」を取得する
		List<InterimBreakDayOffMng> interimTyingData = breakDayOffInterimRepo.getDayOffByIdAndDataAtr(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM, dayoffConfirmData.getComDayOffID());
		//未相殺日数と未相殺時間を設定する
		double unOffsetDays = dayoffConfirmData.getRemainDays().v();
		int unOffsetTimes = dayoffConfirmData.getRemainTimes().v();
		for (InterimBreakDayOffMng tyingData : interimTyingData) {
			unOffsetDays -= tyingData.getUseDays().v();
			unOffsetTimes -= tyingData.getUseTimes().v();
		}
		//未相殺日数と未相殺時間をチェックする
		if(unOffsetDays <= 0 && unOffsetTimes <= 0) {
			return null;
		}		
		//「代休の未相殺」．未相殺日数=未相殺日数,  「代休の未相殺」．未相殺時間=未相殺時間
		UnOffSetOfDayOff dayOffData = new UnOffSetOfDayOff(dayoffConfirmData.getComDayOffID(),
				dayoffConfirmData.getRequiredTimes().v(),					
				dayoffConfirmData.getRequireDays().v(), 
				unOffsetTimes, 
				unOffsetDays);
		detail.setDataAtr(MngDataStatus.CONFIRMED);
		detail.setSid(sid);
		detail.setYmdData(dayoffConfirmData.getDayOffDate());
		detail.setOccurrentClass(OccurrenceDigClass.DIGESTION);
		detail.setUnOffsetOfDayoff(Optional.of(dayOffData));
		return detail;
	}

	@Override
	public List<BreakDayOffDetail> getConfirmBreakDetail(String sid) {
		List<BreakDayOffDetail> lstData = new ArrayList<>();
		//アルゴリズム「確定休出から未使用の休出を取得する」を実行する
		List<LeaveManagementData> lstConfirmBreakData = this.lstConfirmBreakData(sid);
		for (LeaveManagementData confirmData : lstConfirmBreakData) {
			//アルゴリズム「暫定代休と紐付けをしない確定休出を取得する」を実行する
			BreakDayOffDetail ouputData = this.getConfirmBreakData(confirmData, sid);
			if(ouputData != null) {
				lstData.add(ouputData);
			}
		}
		return lstData;
	}

	@Override
	public List<LeaveManagementData> lstConfirmBreakData(String sid) {
		//ドメインモデル「休出管理データ」
		String cid = AppContexts.user().companyId();
		List<LeaveManagementData> lstBreackConfirm = breakConfrimRepo.getBySid(cid, sid);
		return lstBreackConfirm.stream().filter(x -> x.getUnUsedDays().v() > 0 && x.getUnUsedTimes().v() > 0)
				.collect(Collectors.toList());
	}

	@Override
	public BreakDayOffDetail getConfirmBreakData(LeaveManagementData breakConfirm, String sid) {
		//ドメインモデル「暫定休出代休紐付け管理」を取得する
		List<InterimBreakDayOffMng> interimTyingData = breakDayOffInterimRepo.getBreakByIdAndDataAtr(DataManagementAtr.CONFIRM, DataManagementAtr.INTERIM, breakConfirm.getID());
		//未使用日数：INPUT.未使用日数 
		double unUseDays = breakConfirm.getUnUsedDays().v();
		Integer unUseTimes = breakConfirm.getUnUsedTimes().v();
		for (InterimBreakDayOffMng breakData : interimTyingData) {
			//未使用日数と未使用時間を設定する
			unUseDays -= breakData.getUseDays().v();
			unUseTimes -= breakData.getUseTimes().v();
		}
		if(unUseDays <= 0 && unUseTimes <= 0) {
			return null;
		}
		//「休出の未使用」．未使用日数 = 未使用日数、「休出の未使用」．未使用時間 = 未使用時間 
		UnUserOfBreak breakData = new UnUserOfBreak(breakConfirm.getID(), 
				breakConfirm.getFullDayTime().v(),
				breakConfirm.getExpiredDate(),
				breakConfirm.getOccurredTimes().v(),
				breakConfirm.getOccurredDays().v(),
				breakConfirm.getHalfDayTime().v(),
				unUseTimes, 
				unUseDays);
		BreakDayOffDetail outputData = new BreakDayOffDetail(sid, 
				MngDataStatus.CONFIRMED, 
				breakConfirm.getComDayOffDate(),
				OccurrenceDigClass.OCCURRENCE,
				Optional.of(breakData),
				Optional.empty());
		return outputData;
	}

	@Override
	public double calcCarryForwardDays(GeneralDate baseDate, List<BreakDayOffDetail> lstDetailData) {
		//アルゴリズム「6.残数と未消化数を集計する」を実行
		
		return 0;
	}

	@Override
	public RemainUnDigestedDayTimes getRemainUnDigestedDayTimes(GeneralDate baseDate,
			List<BreakDayOffDetail> lstDetailData) {
		//残日数 = 0、残時間数 = 0、未消化日数 = 0、未消化時間 = 0（初期化）
		RemainUnDigestedDayTimes outputData = new RemainUnDigestedDayTimes(0, 0, 0, 0);
		//TODO アルゴリズム「代休の設定を取得する」を実行する
		
		return null;
	}

	@Override
	public List<BreakDayOffDetail> lstInterimDayOffDetail(BreakDayOffRemainMngParam inputParam) {
		List<BreakDayOffDetail> lstOutput = new ArrayList<>();
		List<InterimRemain> lstInterimData = new ArrayList<>();
		List<InterimDayOffMng> lstDayoffMng = new ArrayList<>();
		//INPUT．モードをチェックする
		if(inputParam.isMode()) {
			//TODO
		} else {
			//ドメインモデル「暫定代休管理データ」を取得する
			lstInterimData = interimRemainRepo.getRemainBySidPriod(inputParam.getSid(), inputParam.getDateData(), RemainType.SUBHOLIDAY);
			lstInterimData.stream().forEach(x -> {
				Optional<InterimDayOffMng> dayOffData = breakDayOffInterimRepo.getDayoffById(x.getRemainManaID());
				dayOffData.ifPresent(y -> lstDayoffMng.add(y));
			});
		}
		if(inputParam.isReplaceChk()
				&& inputParam.getInterimMng() != null 
				&& !inputParam.getInterimMng().isEmpty()
				&& inputParam.getDayOffMng().isPresent()) {
			//INPUT．上書き用の暫定管理データをドメインモデル「暫定代休管理データ」に追加する
			InterimDayOffMng dayOffInput = inputParam.getDayOffMng().get();
			List<InterimRemain> lstInterimInput = inputParam.getInterimMng()
					.stream()
					.filter(z -> z.getRemainManaID().equals(dayOffInput.getDayOffManaId()))
					.collect(Collectors.toList());
			if(!lstInterimInput.isEmpty()) {
				InterimRemain interimData = lstInterimInput.get(0);
				List<InterimRemain> lstTmp = lstInterimData.stream().filter(a -> a.getYmd().equals(interimData.getYmd()))
				.collect(Collectors.toList());
				if(!lstTmp.isEmpty()) {
					InterimRemain tmpData = lstTmp.get(0);
					lstInterimData.remove(tmpData);
					List<InterimDayOffMng> lstTmpDayoff = lstDayoffMng.stream()
							.filter(b -> b.getDayOffManaId().equals(tmpData.getRemainManaID())).collect(Collectors.toList());
					if(!lstTmpDayoff.isEmpty()) {
						InterimDayOffMng tmpDayoff = lstTmpDayoff.get(0);
						lstDayoffMng.remove(tmpDayoff);
					}
				}
				lstInterimData.add(interimData);
			}
			lstDayoffMng.add(dayOffInput);
		}
		//取得した件数をチェックする
		for (InterimDayOffMng interimDayOffMng : lstDayoffMng) {
			//アルゴリズム「休出と紐付けをしない代休を取得する」を実行する
			Optional<InterimRemain> remainData = interimRemainRepo.getById(interimDayOffMng.getDayOffManaId());
			BreakDayOffDetail outData = this.getNotTypeBreak(interimDayOffMng, remainData.get());
			if(outData != null) {
				lstOutput.add(outData);
			}
			
		}
		return lstOutput;
	}

	@Override
	public BreakDayOffDetail getNotTypeBreak(InterimDayOffMng detailData, InterimRemain remainData) {
		//ドメインモデル「暫定休出代休紐付け管理」を取得する
		List<InterimBreakDayOffMng> lstDayOff = breakDayOffInterimRepo.getBreakDayOffMng(remainData.getRemainManaID(), false, DataManagementAtr.INTERIM);
		//未相殺日数と未相殺時間を設定する //TODO tai sao ko lay luon ngay 未相殺日数
		double unOffsetDays = detailData.getRequiredDay().v();
		Integer unOffsetTimes = detailData.getRequiredTime().v();
		for (InterimBreakDayOffMng interimMng : lstDayOff) {
			unOffsetDays -= interimMng.getUseDays().v();
			unOffsetTimes -= interimMng.getUseTimes().v();
		}
		if(unOffsetDays <= 0 && unOffsetTimes <= 0) {
			return null;
		}
		//「代休の未相殺」．未相殺日数=未相殺日数,  「代休の未相殺」．未相殺時間=未相殺時間 
		UnOffSetOfDayOff dayOffData = new UnOffSetOfDayOff(detailData.getDayOffManaId(), 
				detailData.getRequiredTime().v(), detailData.getRequiredDay().v(), unOffsetTimes, unOffsetDays);
		MngDataStatus dataAtr = MngDataStatus.NOTREFLECTAPP;
		if(remainData.getCreatorAtr() == CreaterAtr.SCHEDULE) {
			dataAtr = MngDataStatus.SCHEDULE;
		} else if (remainData.getCreatorAtr() == CreaterAtr.RECORD){
			dataAtr = MngDataStatus.RECORD;
		}
		CompensatoryDayoffDate date = new CompensatoryDayoffDate(true, Optional.of(remainData.getYmd()));
		BreakDayOffDetail dataOutput = new BreakDayOffDetail(remainData.getSID(), 
				dataAtr, 
				date, OccurrenceDigClass.DIGESTION, Optional.empty(), Optional.of(dayOffData));
		return dataOutput;
	}

}
