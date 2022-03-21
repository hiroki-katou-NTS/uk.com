package nts.uk.ctx.at.request.dom.adapter.dailyprocess.cal;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;

import java.util.List;

/**
 * 流動勤務の所定内の休憩時間帯を求めるAdapter
 */
public interface GetFlowWorkBreakTimesAdapter {
    List<BreakTimeSheet> getFlowWorkBreakTimes(PrevisionalForImpExport params);
}
