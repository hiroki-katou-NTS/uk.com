package nts.uk.screen.at.app.kdw013.h;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ApprovalStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ModeData;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.approval.ApprovalStatusActualDayChange;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.ConfirmStatusActualDayChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.screen.at.app.dailyperformance.correction.closure.FindClosureDateService;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalConfirmCache;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemParent;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthValue;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author thanhpv
 * @part UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.H：実績内容確認.メニュー別OCD.実績登録パラメータを作成する
 */
@Stateless
public class CreateAchievementRegistrationParam {
    
    @Inject
    private FindClosureDateService findClosureDateService; 
    
    @Inject 
    private ApprovalStatusActualDayChange approvalStatusActualDayChange;
    
    @Inject
    private ConfirmStatusActualDayChange confirmStatusActualDayChange;
    
    /**
     * @name 実績登録パラメータを作成する
     * @param targetEmployee 対象社員
     * @param targetDate 対象日
     * @param items 修正内容：List<itemvalue>
     * @param integrationOfDaily 日別実績(Work)
     * @return
     */
    public DPItemParent create(String empTarget, GeneralDate targetDate, List<ItemValue> items, IntegrationOfDaily integrationOfDaily){
    	LoginUserContext loginUserContext = AppContexts.user();
    	
    	//DPItemValueを作成する
    	List<DPItemValue> itemValues = items.stream().map(i -> new DPItemValue(empTarget, targetDate, i)).collect(Collectors.toList());
    	
    	//QA: 120066 check Optional empty
    	//DPItemValueを作成する
    	Optional<ClosurePeriod> closurePeriod = findClosureDateService.getClosurePeriod(empTarget, targetDate);
    	
    	//DPMonthValueを作成する
    	DPMonthValue monthValue = new DPMonthValue(empTarget, closurePeriod.get().getYearMonth().v(), closurePeriod.get().getClosureId().value, ClosureDateDto.from(closurePeriod.get().getClosureDate()), new ArrayList<DPItemValue>());
    	
    	//QA: 120066 check giá trị ModeData.NORMAL.value
    	//[No.585]日の実績の承認状況を取得する（NEW）
    	List<ApprovalStatusActualResult> lstApproval = approvalStatusActualDayChange.processApprovalStatus(loginUserContext.companyId(), loginUserContext.employeeId(), Arrays.asList(empTarget), Optional.of(new DatePeriod(targetDate, targetDate)), Optional.empty(), ModeData.NORMAL.value);
    	
    	//QA: 120066 check giá trị Arrays.asList(targetDate)
    	//[No.584]日の実績の確認状況を取得する（NEW）
    	List<ConfirmStatusActualResult> lstConfirm = confirmStatusActualDayChange.processConfirmStatus(loginUserContext.companyId(), loginUserContext.employeeId(), Arrays.asList(empTarget), Optional.of(new DatePeriod(targetDate, targetDate)), Optional.empty());
    	
    	//approvalConfirmCacheを作成する
    	ApprovalConfirmCache approvalConfirmCache = new ApprovalConfirmCache(loginUserContext.employeeId(), Arrays.asList(empTarget), new DatePeriod(targetDate, targetDate), 0, lstConfirm, lstApproval);
    	
    	//DPItemParentを作成する
    	return new DPItemParent(
    			0, 
    			empTarget,
    			itemValues, 
    			new ArrayList<>(), 
    			new ArrayList<>(), 
    			new DateRange(targetDate, targetDate), 
    			null, 
    			monthValue, 
    			Arrays.asList(DailyRecordDto.from(integrationOfDaily)), 
    			Arrays.asList(DailyRecordDto.from(integrationOfDaily)), 
    			Arrays.asList(DailyRecordDto.from(integrationOfDaily)), 
    			false, 
    			new ArrayList<>(), 
    			new HashMap<>(), 
    			new ArrayList<>(), 
    			new ArrayList<>(), 
    			false, //QA: 120066 is boolean type can't set null
    			new ArrayList<>(), 
    			false, 
    			false, 
    			false, 
    			approvalConfirmCache, 
    			Optional.empty(), 
    			null);
    }
}
