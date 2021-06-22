package nts.uk.ctx.at.request.dom.workrecord.remainmanagement;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.EarchInterimRemainCheck;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimEachData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCheckInputParam;

public interface InterimRemainDataMngCheckRegisterRequest {

    EarchInterimRemainCheck checkRegister(InterimRemainCheckInputParam inputParam);
    
    InterimEachData interimInfor(Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput);
}
