package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.shift.workpairpattern.ComPatternScreenDto;
import nts.uk.screen.at.app.shift.workpairpattern.WkpPatternScreenDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * Get data DB BASIC_SCHEDULE, WORKTIME, WORKTYPE, CLOSURE not through dom layer
 * 
 * @author sonnh1
 *
 */

@Stateless
public class BasicScheduleScreenProcessor {

	@Inject
	private BasicScheduleScreenRepository bScheduleScreenRepo;

	@Inject
	private ShClosurePub shClosurePub;
	
	@Inject
	private BasicScheduleService basicScheduleService;
	
	public DataInitScreenDto getDataInit() {
		List<WorkTypeScreenDto> workTypeList = new ArrayList<WorkTypeScreenDto>();
		List<StateWorkTypeCodeDto> listStateCheckState = new ArrayList<StateWorkTypeCodeDto>();
		List<String> workTypeCodeList = new ArrayList<String>();
		List<WorkTimeScreenDto> workTimeList = new ArrayList<WorkTimeScreenDto>();
		List<String> workTimeCodeList = new ArrayList<String>();

		PresentClosingPeriodExport obj = this.getPresentClosingPeriodExport();
		// 勤務種類を取得する (Lấy dữ lieu loại làm việc)
		this.acquireWorkType(workTypeList, listStateCheckState, workTypeCodeList);
		// 就業時間帯を取得する (Lấy dữ liệu thời gian làm việc)
		this.acquireWorkingTime(workTimeList, workTimeCodeList);
		List<StateWorkTypeCodeDto> listStateCheckNeeded = this.checkNeededOfWorkTimeSetting1(workTypeCodeList,
				workTypeList);
		List<WorkEmpCombineScreenDto> listWorkEmpCombineScreenDto = this
				.getListWorkEmpCombine(new ScheduleScreenSymbolParams(workTypeCodeList, workTimeCodeList));

		return new DataInitScreenDto(workTypeList, workTimeList, obj.getClosureStartDate(), obj.getClosureEndDate(),
				listStateCheckState, listStateCheckNeeded, listWorkEmpCombineScreenDto,
				AppContexts.user().employeeId());
	}

	/**
	 * @param params
	 * @return
	 */
	public List<BasicScheduleScreenDto> getByListSidAndDate(BasicScheduleScreenParams params) {
		return this.bScheduleScreenRepo.getByListSidAndDate(params.employeeId, params.startDate, params.endDate);
	}
	
	public List<BasicScheduleScreenDto> getBasicScheduleWithJDBC(BasicScheduleScreenParams params) {
		return this.bScheduleScreenRepo.getBasicScheduleWithJDBC(params.employeeId, params.startDate, params.endDate);
	}

	/**
	 * get list workTime with abolishAtr = NOT_ABOLISH (in contrast to DISPLAY)
	 * 
	 * @return
	 */
	public List<WorkTimeScreenDto> getListWorkTime() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.getListWorkTime(companyId, AbolishAtr.NOT_ABOLISH.value);
	}

	/**
	 * getPresentClosingPeriodExport to get startDate and endDate for screen
	 * KSU001.A
	 * 
	 * @return
	 */
	public PresentClosingPeriodExport getPresentClosingPeriodExport() {
		String companyId = AppContexts.user().companyId();
		int closureId = 1;
		return shClosurePub.find(companyId, closureId).get();
	}

	/**
	 * find by companyId and DeprecateClassification = Deprecated
	 * 
	 * @return List WorkTypeDto
	 */

	public List<WorkTypeScreenDto> findByCIdAndDeprecateCls1() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.findByCIdAndDeprecateCls1(companyId,
				DeprecateClassification.NotDeprecated.value);
	}

	/**
	 * EA修正履歴　No2281
	 * 
	 * Check state of list WorkTypeCode
	 * 
	 * @param lstWorkTypeCode
	 * @return List StateWorkTypeCodeDto
	 */

	public List<StateWorkTypeCodeDto> checkStateWorkTypeCode1(List<String> listWorkTypeCode,
			List<WorkTypeScreenDto> workTypeList) {
		List<StateWorkTypeCodeDto> lstStateWorkTypeCode = new ArrayList<StateWorkTypeCodeDto>();
		List<WorkType> listWorkType = workTypeList.stream().map(x -> x.convertToDomain()).collect(Collectors.toList());
		listWorkTypeCode.forEach(workTypeCode -> {
			WorkStyle workStyle = this.basicScheduleService.checkWorkDayByList(workTypeCode, listWorkType);
			if (workStyle != null) {
				lstStateWorkTypeCode.add(new StateWorkTypeCodeDto(workTypeCode, workStyle.value));
			}
		});
		return lstStateWorkTypeCode;
	}

	/**
	 * check Needed Of WorkTimeSetting
	 * 
	 * @param lstWorkTypeCode
	 * @return List StateWorkTypeCodeDto
	 */

	public List<StateWorkTypeCodeDto> checkNeededOfWorkTimeSetting1(List<String> lstWorkTypeCode, List<WorkTypeScreenDto> workTypeList) {
		List<StateWorkTypeCodeDto> lstStateWorkTypeCode = new ArrayList<StateWorkTypeCodeDto>();
		List<WorkType> listWorkType = workTypeList.stream().map(x -> x.convertToDomain()).collect(Collectors.toList());
		lstWorkTypeCode.forEach(workTypeCode -> {
			SetupType setupType = this.basicScheduleService.checkNeedWorkTimeSetByList(workTypeCode, listWorkType);
			lstStateWorkTypeCode.add(new StateWorkTypeCodeDto(workTypeCode, setupType.value));
		});
		return lstStateWorkTypeCode;
	}

	/**
	 * 
	 * @param params
	 * @return WorkEmpCombineDto
	 */
	public List<WorkEmpCombineScreenDto> getListWorkEmpCombine(ScheduleScreenSymbolParams params) {
		String companyId = AppContexts.user().companyId();
		List<String> lstWorkTypeCode = params.getLstWorkTypeCode();
				List<String> lstWorkTimeCode = params.getLstWorkTimeCode();
		if(CollectionUtil.isEmpty(lstWorkTypeCode) || CollectionUtil.isEmpty(lstWorkTimeCode) ){
			return Collections.emptyList();
		}
		return this.bScheduleScreenRepo.getListWorkEmpCobine(companyId, params.getLstWorkTypeCode(),
				params.getLstWorkTimeCode());
	}

	/**
	 * 
	 * @return list ScheduleDisplayControlDto
	 */
	public ScheduleDisplayControlScreenDto getScheduleDisplayControl() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.getScheduleDisControl(companyId).orElse(null);
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<BasicScheduleScreenDto> getDataWorkScheTimezone(BasicScheduleScreenParams params) {
		return this.bScheduleScreenRepo.getDataWorkScheTimezone(params.employeeId, params.startDate, params.endDate);
	}

	/**
	 * Get data ComPattern, ComPatternItem, ComWorkPairSet
	 * 
	 * @return
	 */
	public List<ComPatternScreenDto> getDataComPattern() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.getDataComPattern(companyId);
	}

	/**
	 * Get data WkpPattern, WkpPatternItem, WkpWorkPairSet
	 * 
	 * @return
	 */
	public List<WkpPatternScreenDto> getDataWkpPattern(String workplaceId) {
		if (StringUtil.isNullOrEmpty(workplaceId, true)) {
			return Collections.emptyList();
		}
		return this.bScheduleScreenRepo.getDataWkpPattern(workplaceId);
	}

	/**
	 * 勤務種類を取得する (Lấy dữ lieu loại làm việc)
	 * 
	 * @param workTypeList
	 * @param listStateWorkTypeCodeDto
	 * @param workTypeCodeList
	 */
	private void acquireWorkType(List<WorkTypeScreenDto> workTypeList, List<StateWorkTypeCodeDto> listStateWorkTypeCodeDto, List<String> workTypeCodeList){
		// ドメインモデル「勤務種類」を取得する (Lấy dữ liệu từ domain 「勤務種類」)
		workTypeList.addAll(this.findByCIdAndDeprecateCls1());
		// ドメインモデル「表示可能勤務種類制御」を取得する
		// TODO- chua dung toi nen chua viet
		
		// EA修正履歴 No2281
		workTypeList.forEach(x -> {
			workTypeCodeList.add(x.getWorkTypeCode());
		});
		listStateWorkTypeCodeDto.addAll(this.checkStateWorkTypeCode1(workTypeCodeList, workTypeList));
	}
	
	/**
	 * 就業時間帯を取得する (Lấy dữ liệu thời gian làm việc)
	 * 
	 * @param workTimeList
	 * @param workTimeCodeList
	 */
	private void acquireWorkingTime(List<WorkTimeScreenDto> workTimeList, List<String> workTimeCodeList){
		// ドメインモデル「就業時間帯の設定」を取得する (Lấy dữ kiệu từ domain 「就業時間帯の設定」)
		workTimeList.addAll(this.getListWorkTime());
		workTimeList.forEach(x -> {
			workTimeCodeList.add(x.getWorkTimeCode());
		});
		// ドメインモデル「並び順」を取得する (Lấy dữ kiệu từ domain 「並び順」)
		// TODO- ben man master KMK003 khong co phan dang ki sort order nen co le khong can
		// アラームチェック条件を取得する
		// da goi o phan khac lien quan den buildTreeShiftCondition
	}
	
	/**
	 * 
	 */
	public void checkStatusForScheduledWork(){
		
	}

}
