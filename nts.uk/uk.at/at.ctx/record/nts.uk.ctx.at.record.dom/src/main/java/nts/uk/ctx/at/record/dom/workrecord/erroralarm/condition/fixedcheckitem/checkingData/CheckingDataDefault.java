package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkingData;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;

@Stateless
public class CheckingDataDefault implements CheckingDataService {

	@Override
	public List<ValueExtractAlarmWR> checkingData(String workplaceID,String employeeID, GeneralDate startDate, GeneralDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
