package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkingData;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
/**
 * 5.データのチェック
 * @author tutk
 *
 */
public interface CheckingDataService {
	public List<ValueExtractAlarmWR> checkingData(String workplaceID,String employeeID,GeneralDate startDate,GeneralDate endDate);
}
