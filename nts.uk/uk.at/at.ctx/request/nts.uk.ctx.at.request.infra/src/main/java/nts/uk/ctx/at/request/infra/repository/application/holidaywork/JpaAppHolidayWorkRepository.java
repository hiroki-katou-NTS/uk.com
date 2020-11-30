package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimit;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtAppHolidayWork;
import nts.uk.ctx.at.request.infra.entity.application.holidaywork.KrqdtAppHolidayWorkPK;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOverTimeDetM;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetail;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetailPk;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.shr.com.context.AppContexts;

/**
 * Refactor5
 * @author huylq
 *
 */
@Stateless
public class JpaAppHolidayWorkRepository extends JpaRepository implements AppHolidayWorkRepository{
	
	public static final String FIND_BY_APPID = "SELECT a FROM KrqdtAppHolidayWork as a WHERE a.krqdtAppHolidayWorkPK.cid = :companyId and a.krqdtAppHolidayWorkPK.appId = :appId";

	@Override
	public Optional<AppHolidayWork> find(String companyId, String applicationId) {
		return this.queryProxy().query(FIND_BY_APPID, KrqdtAppHolidayWork.class)
				.setParameter("companyId", companyId).setParameter("appId", applicationId).getSingle(x -> x.toDomain());
	}
	
	@Override
	public void add(AppHolidayWork appHolidayWork) {
		this.commandProxy().insert(toEntity(appHolidayWork));
		this.getEntityManager().flush();
	}
	
	private KrqdtAppHolidayWork toEntity(AppHolidayWork domain) {
		String cid = AppContexts.user().companyId();
		KrqdtAppHolidayWork entity = new KrqdtAppHolidayWork();
		KrqdtAppHolidayWorkPK entityPK = new KrqdtAppHolidayWorkPK(cid, domain.getAppID());			
		entity.setKrqdtAppHolidayWorkPK(entityPK);
		
		entity.workTypeCode = domain.getWorkInformation().getWorkTypeCode().v();
		entity.workTimeCode = domain.getWorkInformation().getWorkTimeCode().v();
		
		ReasonDivergence reasonDivergence = domain.getApplicationTime().getReasonDissociation().orElse(Collections.emptyList()).get(0);
		entity.divergenceTimeNo = reasonDivergence.getDiviationTime();
		entity.divergenceCode = reasonDivergence.getReasonCode().v();
		entity.divergenceReason = reasonDivergence.getReason().v();	
		
		entity.overtimeNight = domain.getApplicationTime().getOverTimeShiftNight().map(x -> x.getOverTimeMidNight().v()).orElse(null);
		entity.totalNight = domain.getApplicationTime().getOverTimeShiftNight().map(x -> x.getMidNightOutSide().v()).orElse(null);
		
		entity.backHomeAtr = domain.getBackHomeAtr().value;
		entity.goWorkAtr = domain.getGoWorkAtr().value;
		
		List<TimeZoneWithWorkNo> breakTimes = domain.getBreakTimeList().orElse(Collections.emptyList());
		breakTimes.stream().forEach(item -> {
			if (item.getWorkNo().v() == 1) {
				entity.breakTimeStart1 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd1 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 2) {
				entity.breakTimeStart2 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd2 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 3) {
				entity.breakTimeStart3 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd3 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 4) {
				entity.breakTimeStart4 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd4 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 5) {
				entity.breakTimeStart5 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd5 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 6) {
				entity.breakTimeStart6 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd6 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 7) {
				entity.breakTimeStart7 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd7 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 8) {
				entity.breakTimeStart8 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd8 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 9) {
				entity.breakTimeStart9 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd9 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 10) {
				entity.breakTimeStart10 = item.getTimeZone().getStartTime().v();
				entity.breakTimeEnd10 = item.getTimeZone().getEndTime().v();
			}
		});	
		
		List<TimeZoneWithWorkNo> workHours = domain.getWorkingTimeList().orElse(Collections.emptyList());
		workHours.stream().forEach(item -> {
			if (item.getWorkNo().v() == 1) {
				entity.workTimeStart1 = item.getTimeZone().getStartTime().v();
				entity.workTimeEnd1 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 2) {
				entity.workTimeStart2 = item.getTimeZone().getStartTime().v();
				entity.workTimeEnd2 = item.getTimeZone().getEndTime().v();
			}
		});
		
		AppOvertimeDetail appOvertimeDetail = domain.getAppOvertimeDetail().orElse(null);
		if (appOvertimeDetail != null) {
			Time36Agree time36Agree = appOvertimeDetail.getTime36Agree();
			Time36AgreeUpperLimit time36AgreeUpperLimit = appOvertimeDetail.getTime36AgreeUpperLimit();
			List<KrqdtAppOverTimeDetM> KrqdtAppOverTimeDetMs = new ArrayList<KrqdtAppOverTimeDetM>();
			KrqdtAppOvertimeDetail krqdtAppOvertimeDetail = new KrqdtAppOvertimeDetail(
					new KrqdtAppOvertimeDetailPk(
							AppContexts.user().companyId(),
							domain.getAppID()),
					time36Agree.getApplicationTime().v(),
					appOvertimeDetail.getYearMonth().v(),
					time36Agree.getAgreeMonth().getActualTime().v(),
					time36Agree.getAgreeMonth().getLimitErrorTime().v(),
					time36Agree.getAgreeMonth().getLimitAlarmTime().v(),
					time36Agree.getAgreeMonth().getExceptionLimitErrorTime().map(x -> x.v()).orElse(null),
					time36Agree.getAgreeMonth().getExceptionLimitAlarmTime().map(x -> x.v()).orElse(null),
					time36Agree.getAgreeMonth().getNumOfYear36Over().v(),
					time36Agree.getAgreeAnnual().getActualTime().v(),
					time36Agree.getAgreeAnnual().getLimitTime().v(),
					time36AgreeUpperLimit.getApplicationTime().v(),
					time36AgreeUpperLimit.getAgreeUpperLimitMonth().getOverTime().v(),
					time36AgreeUpperLimit.getAgreeUpperLimitMonth().getUpperLimitTime().v(),
					time36AgreeUpperLimit.getAgreeUpperLimitAverage().getUpperLimitTime().v(),
					null,
					null,
					KrqdtAppOverTimeDetMs
					);
			entity.appOvertimeDetail = krqdtAppOvertimeDetail;
		}
		
		//huytodo legal...
		
		return entity;
	}
}
