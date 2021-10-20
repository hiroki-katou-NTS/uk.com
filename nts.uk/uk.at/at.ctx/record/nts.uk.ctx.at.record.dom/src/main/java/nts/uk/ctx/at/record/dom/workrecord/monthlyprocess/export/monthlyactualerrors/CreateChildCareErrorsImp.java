package nts.uk.ctx.at.record.dom.workrecord.monthlyprocess.export.monthlyactualerrors;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.pererror.CreatePerErrorsFromLeaveErrors;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.EmployeeMonthlyPerError;

@Stateless
public class CreateChildCareErrorsImp implements CreateChildCareErrors {

    @Override
    public List<EmployeeMonthlyPerError> createChildCareErrors(CreateChildCareErrorsParam param) {
        // 子の看護休暇エラーから月別残数エラー一覧を作成する
        // http://192.168.50.4:3000/issues/118129
        List<EmployeeMonthlyPerError> employeeMonthlyPerError = CreatePerErrorsFromLeaveErrors.fromChildCareLeave(
                param.getSID(), 
                param.getYearMonth(), 
                param.getClosureId(), 
                param.getClosureDate(), 
                param.getChildCareNurseErrors());
        
        return employeeMonthlyPerError;
    }

}
