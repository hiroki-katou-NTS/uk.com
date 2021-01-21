package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
//import java.util.Map;
import java.util.Optional;

//import nts.arc.time.GeneralDate;
//import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
//import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.requestlist.PrevisionalForImp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;

/**
 * 日別実績の仮計算(申請・スケからの窓口)
 * @author keisuke_hoshina
 *
 */
public interface ProvisionalCalculationService {
 
 public List<IntegrationOfDaily> calculation(List<PrevisionalForImp> imp); 
 
 //会社共通の設定が渡せるとき用
 public List<IntegrationOfDaily> calculationPassCompanyCommonSetting(List<PrevisionalForImp> impList,Optional<ManagePerCompanySet> companySetting);
}