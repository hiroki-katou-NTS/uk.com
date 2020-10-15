package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.repository.RecreateFlag;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;

public interface StampDomainService {
    public List<Stamp> handleData(StampReflectRangeOutput s,
            String empCalAndSumExecLogID, GeneralDate date, String employeeId, String companyId,RecreateFlag recreateFlag);
    //打刻を取得する (new)
    public List<Stamp> handleDataNew(StampReflectRangeOutput s,
            GeneralDate date, String employeeId, String companyId,EmbossingExecutionFlag flag);
}
