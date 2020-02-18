package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectRangeOutput;
import nts.uk.ctx.at.record.dom.stamp.StampItem;

public interface StampDomainService {
    public List<StampItem> handleData(StampReflectRangeOutput s,
            String empCalAndSumExecLogID, GeneralDate date, String employeeId, String companyId,RecreateFlag recreateFlag);
}
