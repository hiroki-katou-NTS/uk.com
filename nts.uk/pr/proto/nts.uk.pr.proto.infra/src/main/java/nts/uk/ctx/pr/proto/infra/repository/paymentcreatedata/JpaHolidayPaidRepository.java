package nts.uk.ctx.pr.proto.infra.repository.paymentcreatedata;

import java.util.List;

import nts.uk.ctx.pr.proto.dom.paymentcreatedata.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.paymentcreatedata.HolidayPaidRepository;

public class JpaHolidayPaidRepository implements HolidayPaidRepository {

	@Override
	public List<HolidayPaid> find(String companyCode, List<String> personIdList) {
		// TODO Auto-generated method stub
		return null;
	}

}
