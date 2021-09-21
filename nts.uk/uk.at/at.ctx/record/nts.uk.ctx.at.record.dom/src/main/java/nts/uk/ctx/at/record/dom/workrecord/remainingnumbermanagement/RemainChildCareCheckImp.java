package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseRequireImplFactory;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.GetRemainingNumberChildCareService;
import nts.uk.ctx.at.record.dom.workrecord.monthlyprocess.export.monthlyactualerrors.CreateChildCareErrors;
import nts.uk.ctx.at.record.dom.workrecord.monthlyprocess.export.monthlyactualerrors.CreateChildCareErrorsParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.EmployeeMonthlyPerError;

/**
 * @author anhnm
 * 
 */
@Stateless
public class RemainChildCareCheckImp implements RemainChildCareCheck {
    
    @Inject
    private ChildCareNurseRequireImplFactory childCareNurseRequireImplFactory;
    
    @Inject
    private CreateChildCareErrors createChildCareErrors;

    @Override
    public List<EmployeeMonthlyPerError> checkRemainChildCare(RemainChildCareCheckParam param) {
        val require = childCareNurseRequireImplFactory.createRequireImpl();
        
        // [NO.206]期間中の子の看護休暇残数を取得
        AggrResultOfChildCareNurse aggrResultOfChildCareNurse = GetRemainingNumberChildCareService.getChildCareRemNumWithinPeriod(
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
        
        CreateChildCareErrorsParam CreateChildCareErrorsParam = new CreateChildCareErrorsParam(
                param.getSID(), 
                param.getYearMonth(), 
                param.getClosureId(), 
                param.getClosureDate(), 
                aggrResultOfChildCareNurse.getChildCareNurseErrors()); 
        // 子の看護エラーから月別実績エラー一覧を作成する
        List<EmployeeMonthlyPerError> employeeMonthlyPerError = createChildCareErrors.createChildCareErrors(CreateChildCareErrorsParam);
        return employeeMonthlyPerError;
    }

}
