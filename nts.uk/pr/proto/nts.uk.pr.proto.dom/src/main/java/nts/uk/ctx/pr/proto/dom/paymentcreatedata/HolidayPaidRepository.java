package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import java.util.List;

public interface HolidayPaidRepository {
	
	List<HolidayPaid> find(String companyCode, List<String> personIdList);
	
}
