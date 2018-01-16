package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
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
	private BasicScheduleService bScheduleService;

	@Inject
	private ShClosurePub shClosurePub;

	/**
	 * @param params
	 * @return
	 */
	public List<BasicScheduleScreenDto> getByListSidAndDate(BasicScheduleScreenParams params) {
		return this.bScheduleScreenRepo.getByListSidAndDate(params.employeeId, params.startDate, params.endDate);
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
	public List<WorkTypeScreenDto> findByCIdAndDeprecateCls() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.findByCIdAndDeprecateCls(companyId,
				DeprecateClassification.NotDeprecated.value);
	}

	/**
	 * Check state of list WorkTypeCode
	 * 
	 * @param lstWorkTypeCode
	 * @return List StateWorkTypeCodeDto
	 */
	public List<StateWorkTypeCodeDto> checkStateWorkTypeCode(List<String> lstWorkTypeCode) {
		List<StateWorkTypeCodeDto> lstStateWorkTypeCode = lstWorkTypeCode.stream()
				.filter(x -> bScheduleService.checkWorkDay(x) != null)
				.map(x -> new StateWorkTypeCodeDto(x, bScheduleService.checkWorkDay(x).value))
				.collect(Collectors.toList());
		return lstStateWorkTypeCode;
	}

	/**
	 * check Needed Of WorkTimeSetting
	 * 
	 * @param lstWorkTypeCode
	 * @return List StateWorkTypeCodeDto
	 */
	public List<StateWorkTypeCodeDto> checkNeededOfWorkTimeSetting(List<String> lstWorkTypeCode) {
		List<StateWorkTypeCodeDto> lstStateWorkTypeCode = lstWorkTypeCode.stream()
				.map(workTypeCode -> new StateWorkTypeCodeDto(workTypeCode,
						bScheduleService.checkNeededOfWorkTimeSetting(workTypeCode).value))
				.collect(Collectors.toList());
		return lstStateWorkTypeCode;
	}

	/**
	 * 
	 * @param params
	 * @return WorkEmpCombineDto
	 */
	public List<WorkEmpCombineScreenDto> getListWorkEmpCombine(ScheduleScreenSymbolParams params) {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.getListWorkEmpCobine(companyId, params.getLstWorkTypeCode(), params.getLstWorkTimeCode());
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
}
