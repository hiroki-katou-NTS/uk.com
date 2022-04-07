package nts.uk.ctx.at.record.pub.dailyprocess.cal;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;

import java.util.List;

/**
 * 指定した要素から流動休憩を取得するPublish
 */
public interface GetFlowWorkBreakTimesFromSpecifiedElementsPub {
    List<BreakTimeSheet> getFlowWorkBreakTimes(PrevisionalForImpImport params);
}
