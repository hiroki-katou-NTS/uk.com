package nts.uk.ctx.at.record.dom.remainingnumber.absenceleave.temp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.GetVacationAddSet;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：暫定振休・振出管理データ
 * @author shuichu_ishida
 */
@Stateless
@Transactional(value = TxType.REQUIRED)
public class InterimAbsenceRecruitServiceImpl implements InterimAbsenceRecruitService {

	/** 暫定残数管理データ */
	@Inject
	private InterimRemainRepository interimRemainRepo;
	/** 暫定振休・振出管理データ */
	@Inject
	private InterimRecAbasMngRepository interimRecAbsMngRepo;
	/** 日別実績の勤務情報 */
	@Inject
	private WorkInformationRepository workInfoOfDailyRepo;
	/** 勤務情報の取得 */
	@Inject
	private WorkTypeRepository workTypeRepo;
	/** 休暇加算設定の取得 */
	@Inject
	private GetVacationAddSet getVacationAddSet;
	
	/** 作成 */
	@Override
	public void create(String companyId, String employeeId, DatePeriod period,
			Optional<List<WorkInfoOfDailyPerformance>> workInfoOfDailyList) {
		
		// 日別実績の勤務情報を取得
		List<WorkInfoOfDailyPerformance> targetWorkInfos = new ArrayList<>();
		if (workInfoOfDailyList.isPresent()){
			targetWorkInfos = workInfoOfDailyList.get();
		}
		else {
			targetWorkInfos = this.workInfoOfDailyRepo.findByPeriodOrderByYmd(employeeId, period);
		}
		if (targetWorkInfos.size() == 0) return;
		
		// 勤務情報　取得
		val workTypes = this.workTypeRepo.findByCompanyId(companyId);
		Map<WorkTypeCode, WorkType> workTypeMap = new HashMap<>();
		for (val workType : workTypes){
			val workTypeCode = workType.getWorkTypeCode();
			workTypeMap.putIfAbsent(workTypeCode, workType);
		}

		// 休暇加算設定　取得
		VacationAddSet vacationAddSet = this.getVacationAddSet.get(companyId);
		
		for (val targetWorkInfo : targetWorkInfos){

			// 勤務種類から振出・振休の日数を取得
			if (targetWorkInfo.getRecordInfo() == null) continue;
			val workTypeCode = targetWorkInfo.getRecordInfo().getWorkTypeCode();
			if (workTypeCode == null) continue;
			if (!workTypeMap.containsKey(workTypeCode)) return;
			val workType = workTypeMap.get(workTypeCode);
			val workTypeDaysCountTable = new WorkTypeDaysCountTable(workType, vacationAddSet, Optional.empty());

			double recruitDays = workTypeDaysCountTable.getTransferAttendanceDays().v();
			if (recruitDays > 0.0){
				
				// 暫定振出管理データを作成
				String recruitGuid = IdentifierUtil.randomUniqueId();
				InterimRemain remain = new InterimRemain(
						recruitGuid,
						employeeId,
						targetWorkInfo.getYmd(),
						CreateAtr.RECORD,
						RemainType.PICKINGUP,
						RemainAtr.SINGLE);
				InterimRecMng recMng = new InterimRecMng(
						recruitGuid,
						GeneralDate.ymd(9999, 12, 31),
						new OccurrenceDay(recruitDays),
						StatutoryAtr.NONSTATURORY,
						new UnUsedDay(recruitDays));
				this.interimRemainRepo.persistAndUpdateInterimRemain(remain);
				this.interimRecAbsMngRepo.persistAndUpdateInterimRecMng(recMng);
			}
			
			double absenceDays = workTypeDaysCountTable.getTransferHolidayUseDays().v();
			if (absenceDays > 0.0){
				
				// 暫定振休管理データを作成
				String absenceGuid = IdentifierUtil.randomUniqueId();
				InterimRemain remain = new InterimRemain(
						absenceGuid,
						employeeId,
						targetWorkInfo.getYmd(),
						CreateAtr.RECORD,
						RemainType.PAUSE,
						RemainAtr.SINGLE);
				InterimAbsMng absMng = new InterimAbsMng(
						absenceGuid,
						new RequiredDay(absenceDays),
						new UnOffsetDay(absenceDays));
				this.interimRemainRepo.persistAndUpdateInterimRemain(remain);
				this.interimRecAbsMngRepo.persistAndUpdateInterimAbsMng(absMng);
			}
		}
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, DatePeriod period) {

		// 暫定振休管理データを削除
		val absenceList = this.interimRemainRepo.getRemainBySidPriod(employeeId, period, RemainType.PAUSE);
		for (val absenceData : absenceList){
			val targetId = absenceData.getRemainManaID();
			this.interimRecAbsMngRepo.deleteInterimAbsMng(targetId);
			this.interimRecAbsMngRepo.deleteInterimRecAbsMng(targetId, false);
		}
		this.interimRemainRepo.deleteBySidPeriodType(employeeId, period, RemainType.PAUSE);

		// 暫定振出管理データを削除
		val recruitList = this.interimRemainRepo.getRemainBySidPriod(employeeId, period, RemainType.PICKINGUP);
		for (val recruitData : recruitList){
			val targetId = recruitData.getRemainManaID();
			this.interimRecAbsMngRepo.deleteInterimRecMng(targetId);
			this.interimRecAbsMngRepo.deleteInterimRecAbsMng(targetId, true);
		}
		this.interimRemainRepo.deleteBySidPeriodType(employeeId, period, RemainType.PICKINGUP);
	}
}
