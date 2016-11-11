package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.List;

import nts.uk.ctx.pr.proto.dom.paymentdata.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.HolidayPaidRepository;

public class JpaHolidayPaidRepository implements HolidayPaidRepository {

	@Override
	public List<HolidayPaid> find(String companyCode, List<String> personIdList) {
		// TODO Auto-generated method stub
		return null;
	}

}
