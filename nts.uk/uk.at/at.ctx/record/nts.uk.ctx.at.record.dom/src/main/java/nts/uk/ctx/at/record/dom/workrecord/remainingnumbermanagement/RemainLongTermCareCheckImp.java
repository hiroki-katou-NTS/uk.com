package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care.GetRemainingNumberCareService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseRequireImplFactory;
import nts.uk.ctx.at.record.dom.workrecord.monthlyprocess.export.monthlyactualerrors.CreateLongTermCareErrors;
import nts.uk.ctx.at.record.dom.workrecord.monthlyprocess.export.monthlyactualerrors.CreateLongTermCareErrorsParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.EmployeeMonthlyPerError;

@Stateless
public class RemainLongTermCareCheckImp implements RemainLongTermCareCheck {
    
    @Inject
    private GetRemainingNumberCareService getRemainingNumberCareService;
    
    @Inject
    private ChildCareNurseRequireImplFactory childCareNurseRequireImplFactory;
    
    @Inject
    private CreateLongTermCareErrors createLongTermCareErrors;

    @Override
    public List<EmployeeMonthlyPerError> checkRemainLongTermCare(RemainLongTermCareCheckParam param) {
        val require = childCareNurseRequireImplFactory.createRequireImpl();
        
        // [NO.207]期間中の介護休暇残数を取得
        AggrResultOfChildCareNurse result = getRemainingNumberCareService.getCareRemNumWithinPeriod(
                param.getCID(), 
                param.getSID(), 
                param.getPeriod(),
                InterimRemainMngMode.OTHER, 
                param.getPeriod().end(),
                Optional.of(param.isOverwriteFlag()), 
                param.getInterimManage(), 
                Optional.empty(), 
                param.createAtr, 
                param.getTargetPeriod(), 
                new CacheCarrier(), 
                require);
        
        CreateLongTermCareErrorsParam createChildCareErrorsParam = new CreateLongTermCareErrorsParam(
                param.getSID(), 
                param.getYearMonth(), 
                param.getClosureId(), 
                param.getClosureDate(), 
                result.getChildCareNurseErrors()); 
        // 介護エラーから月別実績エラー一覧を作成する
        List<EmployeeMonthlyPerError> employeeMonthlyPerError = createLongTermCareErrors.createLongTermCareErrors(createChildCareErrorsParam);
        return employeeMonthlyPerError;
    }

}
