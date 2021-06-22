package nts.uk.ctx.at.request.ac.remainingnumber;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.InterimRemainDataMngCheckRegisterPub;
import nts.uk.ctx.at.request.dom.workrecord.remainmanagement.InterimRemainDataMngCheckRegisterRequest;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.EarchInterimRemainCheck;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimEachData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCheckInputParam;

public class InterimRemainDataMngCheckRegisterImpRequest implements InterimRemainDataMngCheckRegisterRequest {
    
    private InterimRemainDataMngCheckRegisterPub interimRemainDataMngCheckRegisterPub;

    @Override
    public EarchInterimRemainCheck checkRegister(InterimRemainCheckInputParam inputParam) {
        return interimRemainDataMngCheckRegisterPub.checkRegister(inputParam);
    }

    @Override
    public InterimEachData interimInfor(Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput) {
        return interimRemainDataMngCheckRegisterPub.interimInfor(mapDataOutput);
    }

}
