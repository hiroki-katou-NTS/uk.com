package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionDataRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtractingCondition;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.AttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.adapter.attendanceitemname.MonthlyAttendanceItemNameDto;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckInfor;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ResultOfEachCondition;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;


public class DailyCheckServiceImpl implements DailyCheckService{
	
	@Inject
	private AttendanceItemNameAdapter attendanceAdap;
	
	@Inject
	private DailyAttendanceItemNameAdapter dailyNameAdapter;
	
	@Inject
	private WorkTypeRepository workTypeRep;
	
	@Inject
	private DailyRecordShareFinder dailyRecordShareFinder;
	
	@Inject
	private WorkRecordExtraConRepository workRep;
	
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmRep;
	
	@Inject
	private FixedConditionWorkRecordRepository fixCondReRep;
	
	@Inject
	private FixedConditionDataRepository fixConRep;
	
	@Inject
	private IdentificationRepository indentifiRep;
	
	@Override
	public void extractDailyCheck(String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorDailyCheckId,
			List<String> errorDailyCheckCd, List<WorkPlaceHistImportAl> getWplByListSidAndPeriod, 
			List<StatusOfEmployeeAdapterAl> lstStatusEmp, List<ResultOfEachCondition> lstResultCondition, 
			List<AlarmListCheckInfor> lstCheckType) {
		
	}

	private void prepareDataBeforeChecking(String cid, List<String> lstSid, DatePeriod dPeriod, 
			String errorDailyCheckId, List<Integer> fixedExtractConditionWorkRecord, List<String> errorDailyCheckCd) {
		
		//日次の勤怠項目を取得する
		//画面で利用できる勤怠項目一覧を取得する
		List<MonthlyAttendanceItemNameDto> monthAtten = attendanceAdap.getMonthlyAttendanceItem(2);
		List<Integer> listItemId = monthAtten.stream().map(x -> x.getAttendanceItemId()).collect(Collectors.toList());
		
		//勤怠項目に対応する名称を生成する
		Map<Integer, String> mapNameId = dailyNameAdapter.getDailyAttendanceItemNameAsMapName(listItemId);
		
		//<<Public>> 勤務種類をすべて取得する
		List<WorkType> listWorkType = workTypeRep.findByCompanyId(cid);
		
		//社員と期間を条件に日別実績を取得する
		List<IntegrationOfDaily> listIntegrationDai = dailyRecordShareFinder.findByListEmployeeId(lstSid, dPeriod);
		
		//ドメインモデル「勤務実績の抽出条件」を取得する
		Optional<WorkRecordExtractingCondition> workRecordCond = workRep.getAllWorkRecordExtraConByIdAndUse(errorDailyCheckId, true);
		
		//ドメインモデル「日別実績のエラーアラーム」を取得する
		List<ErrorAlarmWorkRecord> listError = errorAlarmRep.findByListErrorAlamByIdUse(errorDailyCheckCd, true);
		
		//日次の固定抽出条件のデータを取得する
	}
	
	/**
	 * 日次の固定抽出条件のデータを取得する
	 * @param lstSid
	 * @param dPeriod
	 * @param fixedExtractConditionWorkRecord
	 */
	private void getDataForDailyFix(List<String> lstSid, DatePeriod dPeriod,  String errorDailyCheckId) {
		
		List<FixExtracItem> fixExtrac = new ArrayList<>();
		
		// ドメインモデル「勤務実績の固定抽出条件」を取得する
		List<FixedConditionWorkRecord> workRecordExtract = fixCondReRep.getFixConWorkRecordByIdUse(errorDailyCheckId, true);
		if(!workRecordExtract.isEmpty()) {
			
			for(FixedConditionWorkRecord item : workRecordExtract) {
				
				// ドメインモデル「勤務実績の固定抽出項目」を取得する
				Optional<FixedConditionData> fixCond = fixConRep.getFixedByNO(item.getFixConWorkRecordNo().value);
				
				// 勤務実績の固定抽出条件を作成して返す
				if(fixCond.isPresent()) {
					FixExtracItem temp = new FixExtracItem(item.getFixConWorkRecordNo().value, 
											fixCond.get().getFixConWorkRecordName().v(), 
											item.getMessage().v());
					fixExtrac.add(temp);
				}
			}
			
			for (FixExtracItem itemFix : fixExtrac) {
				
				switch (itemFix.getNo()) {
					case 3: 
						// 社員一覧本人が確認しているかチェック
						List<IdentityVerifiForDay> listIdentity = this.getEmployeeListCheckIfPersonConfirm(lstSid, dPeriod);
						
					case 4:	
						
					default:
				}
			}
		}
		
	}
	
	/**
	 * 社員一覧本人が確認しているかチェック
	 * @param lstSid
	 * @param dPeriod
	 * @return
	 */
	private List<IdentityVerifiForDay> getEmployeeListCheckIfPersonConfirm(List<String> lstSid, DatePeriod dPeriod) {
		List<IdentityVerifiForDay> listIdentity = new ArrayList<>();
		
		for(String item : lstSid) {
			// ドメインモデル「日の本人確認」を取得する
			List<Identification> identifiList = indentifiRep.findByEmployeeID(item, dPeriod.start(), dPeriod.end());
			List<GeneralDate> listDay = identifiList.stream().map(x -> x.getProcessingYmd()).collect(Collectors.toList());
			
			// 取得した「日の本人確認」をもとにList＜社員ID、年月日、本人状態＞を作成する
			if (!identifiList.isEmpty()) {
				for(GeneralDate i = dPeriod.start(); i.equals(dPeriod.end()); i.addDays(1)) {
					
					Optional<GeneralDate> temp = listDay.stream().filter(x -> x.equals(i)).findFirst();
					
					if(temp.isPresent()) {
						IdentityVerifiForDay identity = new IdentityVerifiForDay(item, temp.get(), "確認済み");
						listIdentity.add(identity);
					}else {
						IdentityVerifiForDay identity = new IdentityVerifiForDay(item, i, "未確認");
						listIdentity.add(identity);
					}

				}
			}			
		}
		return listIdentity;
	}
	
	/**
	 * 管理者未確認チェック
	 * @param lstSid
	 * @param dPeriod
	 * @return
	 */
	private List<IdentityVerifiForDay> administratorUnconfirmedChk(List<String> lstSid, DatePeriod dPeriod) {
		List<IdentityVerifiForDay> listIdentity = new ArrayList<>();
		
		
		
		return listIdentity;
	}
}