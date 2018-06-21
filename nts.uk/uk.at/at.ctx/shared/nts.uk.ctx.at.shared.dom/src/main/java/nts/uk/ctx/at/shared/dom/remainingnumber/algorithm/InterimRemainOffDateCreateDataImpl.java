package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DayoffTranferInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.InforFormerRemainData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.OccurrenceUseDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.TranferTimeInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.WorkTypeRemainInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.GetDesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

public class InterimRemainOffDateCreateDataImpl implements InterimRemainOffDateCreateData{
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeIsFluidWork workTimeService;
	@Inject
	private GetDesignatedTime confirmTime;
	@Override
	public DailyInterimRemainMngData createData(String sid, GeneralDate baseDate, boolean dayOffTimeIsUse,
			InterimRemainCreateInfor detailData) {
		//残数作成元情報を作成する
		InforFormerRemainData formerRemainData = this.createInforFormerRemainData(sid, baseDate, detailData, dayOffTimeIsUse);
		//残数作成元情報から暫定残数管理データを作成する
		DailyInterimRemainMngData createDataInterimRemain = this.createDataInterimRemain(formerRemainData);
		return createDataInterimRemain;
	}

	@Override
	public InforFormerRemainData createInforFormerRemainData(String sid, GeneralDate baseDate,
			InterimRemainCreateInfor detailData, boolean dayOffTimeIsUse) {
		InforFormerRemainData outputData = new InforFormerRemainData(sid, baseDate, dayOffTimeIsUse, Optional.empty(), Optional.empty(), Optional.empty());		
		//最新の勤務種類変更を伴う申請を抽出する
		List<AppRemainCreateInfor> appWithWorkType = this.getAppWithWorkType(detailData.getAppData());
		if(appWithWorkType.isEmpty()) {
			//実績をチェックする
			if(detailData.getRecordData().isPresent()) {
				//実績から残数作成元情報を設定する
				return this.createInterimDataFromecord(detailData.getRecordData().get(), outputData);
			} else {
				//予定をチェックする
				if(detailData.getScheData().isPresent()) {
					//予定から残数作成元情報を設定する
					return this.createInterimDataFromSche(detailData.getScheData().get(), outputData, dayOffTimeIsUse);
				}
			}
		} else {
			//TODO 最新の勤務種類変更を伴う申請から残数作成元情報を設定する
		}
		return outputData;
	}
	/**
	 * 最新の勤務種類変更を伴う申請を抽出する
	 * @param lstAppData
	 * @return
	 */
	private List<AppRemainCreateInfor> getAppWithWorkType(List<AppRemainCreateInfor> lstAppData){
		//TODO
		return null;
		
	}

	@Override
	public InforFormerRemainData createInterimDataFromecord(RecordRemainCreateInfor recordData, InforFormerRemainData outputData) {
		//アルゴリズム「勤務種類別残数情報を作成する」を実行する
		WorkTypeRemainInfor dataDetail = this.createWorkTypeRemainInfor(CreateAtr.RECORD, recordData.getWorkTypeCode());
		outputData.setWorkTypeRemain(Optional.of(dataDetail));
		//アルゴリズム「実績から代休振替情報を設定する」を実行する
		DayoffTranferInfor dayOffInfor = this.createDayOffTranferFromRecord(CreateAtr.RECORD, recordData, outputData.isDayOffTimeIsUse());
		outputData.setDayOffTranfer(Optional.of(dayOffInfor));
		return outputData;
	}

	@Override
	public WorkTypeRemainInfor createWorkTypeRemainInfor(CreateAtr createAtr, String workTypeCode) {
		WorkTypeRemainInfor outputData = new WorkTypeRemainInfor();
		String cid = AppContexts.user().companyId();
		//ドメインモデル「勤務種類」を取得する
		Optional<WorkType> optWorkTypeData = workTypeRepo.findByPK(cid, workTypeCode);
		if(!optWorkTypeData.isPresent()) {
			return outputData;
		}
		WorkType workTypeData = optWorkTypeData.get();
		//勤務種類別残数情報を作成する
		outputData.setCreateData(createAtr);
		outputData.setWorkTypeCode(workTypeCode);
		outputData.setOccurrenceDetailData(this.createDetailData());
		//勤務区分をチェックする
		if(workTypeData.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay) {
			//1日勤務時の残数発生使用明細を作成する
			return this.createWithOneDayWorkType(workTypeData, outputData);
		} else {
			//午前と午後勤務時の残数発生使用明細を作成する
			return this.createWithHaftDayWorkType(workTypeData, outputData);
		}
	}

	@Override
	public WorkTypeRemainInfor createWithOneDayWorkType(WorkType workType, WorkTypeRemainInfor dataOutput) {
		//アルゴリズム「残数発生使用対象の勤務種類の分類かを判定」を実行する
		JudgmentTypeOfWorkType judmentType = this.judgmentType(workType.getDailyWork().getOneDay());
		if(judmentType == JudgmentTypeOfWorkType.REMAINOCCNOTCOVER) {
			return dataOutput;
		}
		//勤務種類の分類をチェックする
		if(workType.getDailyWork().getOneDay() == WorkTypeClassification.HolidayWork) {
			//代休を発生させるかをチェックする
			if(workType.getWorkTypeSet().getGenSubHodiday() == WorkTypeSetCheck.NO_CHECK) {
				return dataOutput;
			}
		}		
		return this.setData(dataOutput, 1.0, workType.getDailyWork().getOneDay());
	}	
	/**
	 * 残数発生使用対象の勤務種類の分類かを判定
	 * @param typeAtr
	 * @return
	 */
	private JudgmentTypeOfWorkType judgmentType(WorkTypeClassification typeAtr) {
		if(typeAtr == WorkTypeClassification.Absence
				|| typeAtr == WorkTypeClassification.AnnualHoliday
				|| typeAtr == WorkTypeClassification.Pause
				|| typeAtr == WorkTypeClassification.SpecialHoliday
				|| typeAtr == WorkTypeClassification.SubstituteHoliday
				|| typeAtr == WorkTypeClassification.TimeDigestVacation
				|| typeAtr == WorkTypeClassification.YearlyReserved) {
			return JudgmentTypeOfWorkType.REMAIN;
		} else if (typeAtr == WorkTypeClassification.HolidayWork
				|| typeAtr == WorkTypeClassification.Shooting) {
			return JudgmentTypeOfWorkType.REMAINOCCNOTCOVER;
		} else {
			return JudgmentTypeOfWorkType.REMAINOCC;
		}
	}
	/**
	 * 勤務種類別残数情報を作成する
	 * @return
	 */
	private List<OccurrenceUseDetail> createDetailData() {
		List<OccurrenceUseDetail> occurrenceDetailData = new ArrayList<>();
		OccurrenceUseDetail detailData = new OccurrenceUseDetail(0, false, WorkTypeClassification.AnnualHoliday);
		occurrenceDetailData.add(detailData);
		detailData = new OccurrenceUseDetail(0, false, WorkTypeClassification.YearlyReserved);
		occurrenceDetailData.add(detailData);
		detailData = new OccurrenceUseDetail(0, false, WorkTypeClassification.SpecialHoliday);
		occurrenceDetailData.add(detailData);
		detailData = new OccurrenceUseDetail(0, false, WorkTypeClassification.Absence);
		occurrenceDetailData.add(detailData);
		detailData = new OccurrenceUseDetail(0, false, WorkTypeClassification.Pause);
		occurrenceDetailData.add(detailData);
		detailData = new OccurrenceUseDetail(0, false, WorkTypeClassification.TimeDigestVacation);
		occurrenceDetailData.add(detailData);
		detailData = new OccurrenceUseDetail(0, false, WorkTypeClassification.HolidayWork);
		occurrenceDetailData.add(detailData);
		detailData = new OccurrenceUseDetail(0, false, WorkTypeClassification.Shooting);
		occurrenceDetailData.add(detailData);
		return occurrenceDetailData;
	}

	@Override
	public WorkTypeRemainInfor createWithHaftDayWorkType(WorkType workType, WorkTypeRemainInfor dataOutput) {
		//午前
		//アルゴリズム「残数発生使用対象の勤務種類の分類かを判定」を実行する
		JudgmentTypeOfWorkType judgmentTypeAM = this.judgmentType(workType.getDailyWork().getMorning());
		if(judgmentTypeAM == JudgmentTypeOfWorkType.REMAINOCCNOTCOVER) {
			return dataOutput;
		}
		//勤務種類の分類に対応する残数発生使用明細を設定する		
		dataOutput = this.setData(dataOutput, 0.5, workType.getDailyWork().getMorning());
		//午後
		//アルゴリズム「残数発生使用対象の勤務種類の分類かを判定」を実行する
		JudgmentTypeOfWorkType judgmentTypePM = this.judgmentType(workType.getDailyWork().getAfternoon());
		if(judgmentTypePM == JudgmentTypeOfWorkType.REMAINOCCNOTCOVER) {
			return dataOutput;
		}
		//勤務種類の分類に対応する残数発生使用明細を設定する
		dataOutput = this.setData(dataOutput, 0.5, workType.getDailyWork().getAfternoon());
		return dataOutput;
	}
	/**
	 * 勤務種類の分類に対応する残数発生使用明細を設定する
	 * @param dataOutput
	 * @param days
	 * @param workTypeClass
	 * @return
	 */
	private WorkTypeRemainInfor setData(WorkTypeRemainInfor dataOutput, double days, WorkTypeClassification workTypeClass) {
		for (OccurrenceUseDetail detail : dataOutput.getOccurrenceDetailData()) {
			if(detail.getWorkTypeAtr() == workTypeClass) {
				dataOutput.getOccurrenceDetailData().remove(detail);
				detail.setDays(days);
				detail.setUseAtr(true);
				dataOutput.getOccurrenceDetailData().add(detail);
				break;
			}
		}
		return dataOutput;
	}

	@Override
	public DayoffTranferInfor createDayOffTranferFromRecord(CreateAtr createAtr, RecordRemainCreateInfor recordData,
			boolean dayOffTimeIsUse) {
		//代休振替情報を作成する
		TranferTimeInfor tranferBreakTime = new TranferTimeInfor(createAtr, recordData.getTransferTotal(), Optional.empty());
		DayoffTranferInfor outputData = new DayoffTranferInfor(recordData.getWorkTimeCode(), Optional.of(tranferBreakTime), Optional.empty());
		//アルゴリズム「代休振替時間を算出する」を実行する
		String workTimeCode = recordData.getWorkTimeCode().isPresent() ? recordData.getWorkTimeCode().get() : "000";
		tranferBreakTime = this.calDayoffTranferTime(createAtr, workTimeCode, recordData.getTransferTotal(), DayoffChangeAtr.BREAKTIME);
		outputData.setTranferBreakTime(Optional.of(tranferBreakTime));
		//アルゴリズム「実績から振替残業時間を作成する」を実行する
		if(dayOffTimeIsUse) {
			TranferTimeInfor tranferOvertime = this.calDayoffTranferTime(createAtr, workTimeCode, recordData.getTransferOvertimesTotal(), DayoffChangeAtr.OVERTIME);
			outputData.setTranferOverTime(Optional.of(tranferOvertime));
		}
		return outputData;
	}

	@Override
	public DailyInterimRemainMngData createDataInterimRemain(InforFormerRemainData inforData) {
		DailyInterimRemainMngData outputData = new DailyInterimRemainMngData();
		//振休
		outputData = this.createInterimAbsData(inforData, WorkTypeClassification.Pause, outputData);
		//代休
		outputData = this.createInterimDayOffData(inforData, WorkTypeClassification.SubstituteHoliday, outputData);
		//TODO 振出
		
		return outputData;
	}

	@Override
	public DailyInterimRemainMngData createInterimAbsData(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,

			DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(inforData, workTypeClass);
		if(occUseDetail.isPresent()) {
			String mngId = IdentifierUtil.randomUniqueId();
			InterimRemain mngDataRemain = new InterimRemain(mngId, inforData.getSid(), inforData.getYmd(), inforData.getWorkTypeRemain().get().getCreateData(), RemainType.PAUSE, RemainAtr.SINGLE);
			InterimAbsMng absData = new InterimAbsMng(mngId, new RequiredDay(occUseDetail.get().getDays()), new UnOffsetDay(occUseDetail.get().getDays()));
			mngData.setInterimAbsData(Optional.of(absData));
			mngData.getRecAbsData().add(mngDataRemain);
		}
		
		return mngData;
	}

	@Override
	public DailyInterimRemainMngData createInterimDayOffData(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(inforData, workTypeClass);
		if(occUseDetail.isPresent()) {
			if(!occUseDetail.get().isUseAtr()) {
				String mngId = IdentifierUtil.randomUniqueId();
				InterimRemain mngDataRemain = new InterimRemain(mngId, inforData.getSid(), inforData.getYmd(), inforData.getWorkTypeRemain().get().getCreateData(), RemainType.SUBHOLIDAY, RemainAtr.SINGLE);
				InterimDayOffMng dayoffMng = new InterimDayOffMng(mngId, 
						new RequiredTime(0),
						new RequiredDay(occUseDetail.get().getDays()),
						new UnOffsetTime(0), 
						new UnOffsetDay(occUseDetail.get().getDays()));
				mngData.setDayOffData(Optional.of(dayoffMng));
				mngData.getRecAbsData().add(mngDataRemain);
			} else {
				//TODO 2018.06.20 chua lam trong giai doan nay
			}
		}
		return mngData;
	}

	@Override
	public InforFormerRemainData createInterimDataFromSche(ScheRemainCreateInfor scheData,
			InforFormerRemainData outputData, boolean dayOffTimeIsUse) {
		String workTimeCode = scheData.getWorkTimeCode().isPresent() ? scheData.getWorkTimeCode().get() : "000";
		//アルゴリズム「勤務種類別残数情報を作成する」を実行する
		WorkTypeRemainInfor remainInfor = this.createWorkTypeRemainInfor(CreateAtr.SCHEDULE, scheData.getWorkTypeCode());
		//勤務種類別残数情報を設定する
		outputData.setWorkTypeRemain(Optional.of(remainInfor));
		//アルゴリズム「就業時間帯から代休振替情報を作成する」を実行する
		DayoffTranferInfor tranferData = this.createDayoffFromWorkTime(remainInfor, workTimeCode, null, CreateAtr.SCHEDULE, null, dayOffTimeIsUse);
		outputData.setDayOffTranfer(Optional.of(tranferData));
		return outputData;
	}

	@Override
	public DayoffTranferInfor createDayoffFromWorkTime(WorkTypeRemainInfor remainInfor, String workTimeCode,
			Integer timeSetting, CreateAtr createAtr, Integer timeOverSetting, boolean dayOffTimeIsUse) {
		//アルゴリズム「代休を発生させる勤務種類かを判定する」を実行する
		boolean chkDayOff = this.checkDayoffOcc(remainInfor);
		if(!chkDayOff) {
			return null;
		}
		TranferTimeInfor transferBreak = new TranferTimeInfor(createAtr, timeSetting, Optional.empty());
		TranferTimeInfor transferOver = new TranferTimeInfor(createAtr, timeOverSetting, Optional.empty());
		//振替可能時間をチェックする
		if(timeSetting == null) {
			//アルゴリズム「所定時間を取得」を実行する
			timeSetting = workTimeService.getTimeByWorkTimeTypeCode(workTimeCode, remainInfor.getWorkTypeCode());
		}
		if(timeOverSetting != null && dayOffTimeIsUse) {
			//振替残業時間を作成する
			transferOver = this.calDayoffTranferTime(createAtr, workTimeCode, timeOverSetting, DayoffChangeAtr.OVERTIME);
		}
		//アルゴリズム「代休振替時間を算出する」を実行する
		transferBreak = this.calDayoffTranferTime(createAtr, workTimeCode, timeSetting, DayoffChangeAtr.BREAKTIME);
		
		return new DayoffTranferInfor(Optional.of(workTimeCode), Optional.of(transferBreak), Optional.of(transferOver));
	}
	/**
	 * 代休を発生させる勤務種類かを判定する
	 * @param remainInfor
	 * @return
	 */
	private boolean checkDayoffOcc(WorkTypeRemainInfor remainInfor) {
		List<OccurrenceUseDetail> lstChk = remainInfor.getOccurrenceDetailData()
				.stream()
				.filter(x -> x.getWorkTypeAtr() == WorkTypeClassification.HolidayWork && x.isUseAtr())
				.collect(Collectors.toList());
		if(lstChk.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public TranferTimeInfor calDayoffTranferTime(CreateAtr createAtr,String workTimeCode, Integer timeSetting, DayoffChangeAtr dayoffChange) {
		
		String cid = AppContexts.user().companyId();
		//アルゴリズム「代休振替設定を取得」を実行する
		Optional<SubHolTransferSet> optDayOffTranferSetting = confirmTime.get(cid, workTimeCode);
		if(!optDayOffTranferSetting.isPresent()) {
			return null;
		}
		//使用区分をチェックする
		SubHolTransferSet transferSetting = optDayOffTranferSetting.get();
		if(!transferSetting.isUseDivision()) {
			return null;
		}
		//振替区分をチェックする
		if(transferSetting.getSubHolTransferSetAtr() == SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL) {
			//一定時間の振替処理を行う
			//振替可能時間と一定時間を比較する
			if(timeSetting < transferSetting.getCertainTime().v()) {
				return new TranferTimeInfor(createAtr, 0, Optional.empty());
			} else {
				return new TranferTimeInfor(createAtr, transferSetting.getCertainTime().v(), Optional.empty());
			}
		} else {
			//振替可能時間と1日の時間を比較する
			if(timeSetting < transferSetting.getDesignatedTime().getOneDayTime().v()) {
				//振替可能時間と半日の時間を比較する
				if(timeSetting < transferSetting.getDesignatedTime().getHalfDayTime().v()) {
					return new TranferTimeInfor(createAtr, 0, Optional.of((double) 0));
				} else {
					return new TranferTimeInfor(createAtr, transferSetting.getDesignatedTime().getHalfDayTime().v(), Optional.of(0.5));
				}				
			} else {
				return new TranferTimeInfor(createAtr, transferSetting.getDesignatedTime().getOneDayTime().v(), Optional.of(1.0));
			}
		}
	}
}
