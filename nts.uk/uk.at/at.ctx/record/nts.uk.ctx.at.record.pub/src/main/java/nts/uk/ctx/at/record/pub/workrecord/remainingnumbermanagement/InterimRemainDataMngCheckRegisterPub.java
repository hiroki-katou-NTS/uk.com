package nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.EarchInterimRemainCheck;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimEachData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCheckInputParam;

public interface InterimRemainDataMngCheckRegisterPub {
    
    EarchInterimRemainCheck checkRegister(InterimRemainCheckInputParam inputParam);
    
    InterimEachData interimInfor(Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput);
}
