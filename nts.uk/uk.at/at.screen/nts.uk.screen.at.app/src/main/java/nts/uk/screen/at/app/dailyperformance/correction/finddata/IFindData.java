package nts.uk.screen.at.app.dailyperformance.correction.finddata;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;

public interface IFindData {
    public Optional<DaiPerformanceFun> getDailyPerformFun(String company);
    
    public void destroyFindData();
}
