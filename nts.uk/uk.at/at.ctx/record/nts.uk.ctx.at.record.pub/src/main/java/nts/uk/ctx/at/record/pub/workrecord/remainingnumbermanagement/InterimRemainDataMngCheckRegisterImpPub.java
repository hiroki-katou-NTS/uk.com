package nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.InterimRemainDataMngCheckRegister;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.EarchInterimRemainCheck;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimEachData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCheckInputParam;

@Stateless
public class InterimRemainDataMngCheckRegisterImpPub implements InterimRemainDataMngCheckRegisterPub {
    
    @Inject
    private InterimRemainDataMngCheckRegister interimRemainDataMngCheckRegister;

    @Override
    public EarchInterimRemainCheck checkRegister(InterimRemainCheckInputParam inputParam) {
        return interimRemainDataMngCheckRegister.checkRegister(inputParam);
    }

    @Override
    public InterimEachData interimInfor(Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput) {
        return interimRemainDataMngCheckRegister.interimInfor(mapDataOutput);
    }

}
