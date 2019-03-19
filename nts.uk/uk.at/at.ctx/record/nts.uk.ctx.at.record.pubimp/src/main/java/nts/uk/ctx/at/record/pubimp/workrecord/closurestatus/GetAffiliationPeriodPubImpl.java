package nts.uk.ctx.at.record.pubimp.workrecord.closurestatus;

import java.time.Period;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.workrecord.closurestatus.EmpAffInfoExport;
import nts.uk.ctx.at.record.pub.workrecord.closurestatus.GetAffiliationPeriodPub;

public class GetAffiliationPeriodPubImpl implements GetAffiliationPeriodPub {

	@Override
	public EmpAffInfoExport getAffiliationPeriod(List<String> listSid, Period period, GeneralDate baseDate) {
		
		// 社員(list)に対応する処理締めを取得する (Lấy closure xử lý ứng với employee(list))
		
		return null;
		

	}

}
