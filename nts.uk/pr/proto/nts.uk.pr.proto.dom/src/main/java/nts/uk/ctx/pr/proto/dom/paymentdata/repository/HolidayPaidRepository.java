package nts.uk.ctx.pr.proto.dom.paymentdata.repository;

import java.util.List;

import nts.uk.ctx.pr.proto.dom.paymentdata.HolidayPaid;

public interface HolidayPaidRepository {
	
	List<HolidayPaid> find(String companyCode, List<String> personIdList);
	
}
