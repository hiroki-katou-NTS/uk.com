package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectRangeOutput;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.repository.RecreateFlag;

public interface StampDomainService {
    public List<Stamp> handleData(StampReflectRangeOutput s,
            String empCalAndSumExecLogID, GeneralDate date, String employeeId, String companyId,RecreateFlag recreateFlag);
}
