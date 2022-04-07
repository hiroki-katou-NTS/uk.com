package nts.uk.ctx.at.request.ac.application.overtime;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyprocess.cal.GetFlowWorkBreakTimesFromSpecifiedElementsPub;
import nts.uk.ctx.at.record.pub.dailyprocess.cal.PrevisionalForImpImport;
import nts.uk.ctx.at.request.dom.adapter.dailyprocess.cal.GetFlowWorkBreakTimesAdapter;
import nts.uk.ctx.at.request.dom.adapter.dailyprocess.cal.PrevisionalForImpExport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Stateless
public class GetFlowWorkBreakTimesAdapterImpl implements GetFlowWorkBreakTimesAdapter {
    @Inject
    private GetFlowWorkBreakTimesFromSpecifiedElementsPub publish;

    @Override
    public List<BreakTimeSheet> getFlowWorkBreakTimes(PrevisionalForImpExport params) {
        PrevisionalForImpImport paramsImport = new PrevisionalForImpImport(
                params.getEmployeeId(),
                params.getTargetDate(),
                params.getTimeSheets(),
                params.getWorkTypeCode(),
                params.getWorkTimeCode(),
                params.getBreakTimeSheets(),
                params.getOutingTimeSheets(),
                params.getShortWorkingTimeSheets()
        );
        return publish.getFlowWorkBreakTimes(paramsImport);
    }
}
