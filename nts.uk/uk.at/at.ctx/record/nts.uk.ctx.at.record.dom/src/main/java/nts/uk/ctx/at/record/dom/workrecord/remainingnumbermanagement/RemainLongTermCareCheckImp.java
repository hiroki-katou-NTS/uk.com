package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.ArrayList;
import java.util.Arrays;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNurseErrors;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service.CareNursingSettingCheck;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service.ChildNursingSettingCheck;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;

@Stateless
public class RemainLongTermCareCheckImp implements RemainLongTermCareCheck {
    
    @Inject
    private ChildCareNurseRequireImplFactory childCareNurseRequireImplFactory;
    
    @Inject
    private CareNursingSettingCheck careNursingSettingCheck;
    
    @Inject
    private CareLeaveRemainingInfoRepository careLeaveRemainingInfoRepository;
    
    @Inject
    private ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepository;

    @Override
    public List<ChildCareNurseErrors> checkRemainLongTermCare(RemainLongTermCareCheckParam param) {
        val require = childCareNurseRequireImplFactory.createRequireImpl();
        val checkRequire = new RequireImp();
        
        // 介護設定が存在するかチェック
        boolean settingExist = careNursingSettingCheck.check(checkRequire, param.getSID());
        
        if (settingExist) {
            // [NO.207]期間中の介護休暇残数を取得
            AggrResultOfChildCareNurse result = GetRemainingNumberCareService.getCareRemNumWithinPeriod(
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
            
            if (result.getChildCareNurseErrors().size() > 0) {
//                CreateLongTermCareErrorsParam createChildCareErrorsParam = new CreateLongTermCareErrorsParam(
//                        param.getSID(), 
//                        param.getYearMonth(), 
//                        param.getClosureId(), 
//                        param.getClosureDate(), 
//                        result.getChildCareNurseErrors()); 
//                // 介護エラーから月別実績エラー一覧を作成する
//                List<EmployeeMonthlyPerError> employeeMonthlyPerError = createLongTermCareErrors.createLongTermCareErrors(createChildCareErrorsParam);
                return result.getChildCareNurseErrors();
            }
            
        } else {
            // INPUT.「暫定管理データ」に子の看護データがあるかチェックする
            if (param.getInterimManage().size() > 0) {
//                CreateChildCareErrorsParam CreateChildCareErrorsParam = new CreateChildCareErrorsParam(
//                        param.getSID(), 
//                        param.getYearMonth(), 
//                        param.getClosureId(), 
//                        param.getClosureDate(), 
//                        new ArrayList<ChildCareNurseErrors>()); 
                // 介護エラーから月別実績エラー一覧を作成する
//                List<EmployeeMonthlyPerError> employeeMonthlyPerError = createChildCareErrors.createChildCareErrors(CreateChildCareErrorsParam);
                List<ChildCareNurseErrors> result = Arrays.asList(ChildCareNurseErrors.of(
                        new ChildCareNurseUsedNumber(new DayNumberOfUse(1.0), Optional.empty()), 
                        new ChildCareNurseUpperLimit(0), 
                        param.getPeriod().end()));
                
                return result;
            }
        }
        
        return new ArrayList<ChildCareNurseErrors>();
    }
    
    public class RequireImp implements ChildNursingSettingCheck.Require, CareNursingSettingCheck.Require {

        @Override
        public Optional<CareLeaveRemainingInfo> getCareByEmpId(String empId) {
            return careLeaveRemainingInfoRepository.getCareByEmpId(empId);
        }

        @Override
        public Optional<ChildCareLeaveRemainingInfo> getChildCareByEmpId(String empId) {
            return childCareLeaveRemInfoRepository.getChildCareByEmpId(empId);
        }
        
    }

}
