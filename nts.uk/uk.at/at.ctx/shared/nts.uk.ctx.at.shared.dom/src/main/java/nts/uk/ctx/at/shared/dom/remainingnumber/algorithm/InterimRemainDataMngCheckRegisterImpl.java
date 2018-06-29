package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
@Stateless
public class InterimRemainDataMngCheckRegisterImpl implements InterimRemainDataMngCheckRegister{
	@Inject
	private BreakDayOffMngInPeriodQuery breakDayOffMngService;
	@Inject
	private AbsenceReruitmentMngInPeriodQuery absRecMngService;
	@Inject
	private InterimRemainOffPeriodCreateData interimCreateData;
	@Override
	public EarchInterimRemainCheck checkRegister(InterimRemainCheckInputParam inputParam) {
		//代休不足区分、振休不足区分、年休不足区分、積休不足区分、特休不足区分、公休不足区分、超休不足区分をfalseにする(初期化)
		EarchInterimRemainCheck outputData = new EarchInterimRemainCheck(false, false, false, false, false, false, false);
		//暫定管理データをメモリ上で作成する
		InterimRemainCreateDataInputPara inputPara = new InterimRemainCreateDataInputPara(inputParam.getCid(),
				inputParam.getSid(),
				inputParam.getRegisterDate(),
				inputParam.getRecordData(),
				inputParam.getScheData(),
				inputParam.getAppData(),
				false);
		Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput = interimCreateData.createInterimRemainDataMng(inputPara);
		List<InterimRemain> interimMngAbsRec = new ArrayList<>();
		List<InterimAbsMng> useAbsMng = new ArrayList<>();
		List<InterimRecMng> useRecMng = new ArrayList<>();
		List<InterimRemain> interimMngBreakDayOff = new ArrayList<>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimDayOffMng> dayOffMng = new ArrayList<>();
		mapDataOutput.forEach((x,y) -> {
			y.getBreakData().ifPresent(z -> {
				breakMng.add(z);
			});
			y.getDayOffData().ifPresent(a -> {
				dayOffMng.add(a);
			});			
			if(y.getBreakData().isPresent() 
					|| y.getDayOffData().isPresent()
					&& !y.getRecAbsData().isEmpty()) {
				interimMngBreakDayOff.addAll(y.getRecAbsData());
			}
			y.getRecData().ifPresent(b -> {
				useRecMng.add(b);
			});
			y.getInterimAbsData().ifPresent(c -> {
				useAbsMng.add(c);
			});
			if(y.getRecData().isPresent()
					|| y.getInterimAbsData().isPresent()
					&& !y.getRecAbsData().isEmpty()) {
				interimMngAbsRec.addAll(interimMngAbsRec);
			}
		});
		//代休チェック区分をチェックする
		if(inputParam.isChkSubHoliday()) {
			
			//期間内の休出代休残数を取得する
			BreakDayOffRemainMngParam mngParam = new BreakDayOffRemainMngParam(inputParam.getCid(),
					inputParam.getSid(),
					inputParam.getDatePeriod(),
					inputParam.isMode(),
					inputParam.getBaseDate(),
					true,
					interimMngBreakDayOff, 
					breakMng, 
					dayOffMng);
			BreakDayOffRemainMngOfInPeriod remainMng = breakDayOffMngService.getBreakDayOffMngInPeriod(mngParam);
			if(remainMng.getRemainDays() < 0) {
				outputData.setChkSubHoliday(true);
			}
		}
		//振休不足区分をチェックする
		if(inputParam.isChkPause()) {
			
			AbsRecMngInPeriodParamInput mngParam = new AbsRecMngInPeriodParamInput(inputParam.getCid(),
					inputParam.getSid(),
					inputParam.getDatePeriod(),
					inputParam.getBaseDate(),
					inputParam.isMode(),
					true,
					useAbsMng,
					interimMngAbsRec,
					useRecMng);
			AbsRecRemainMngOfInPeriod remainMng = absRecMngService.getAbsRecMngInPeriod(mngParam);
			if(remainMng.getRemainDays() < 0) {
				outputData.setChkPause(true);
			}
		}
		return outputData;
	}

}
