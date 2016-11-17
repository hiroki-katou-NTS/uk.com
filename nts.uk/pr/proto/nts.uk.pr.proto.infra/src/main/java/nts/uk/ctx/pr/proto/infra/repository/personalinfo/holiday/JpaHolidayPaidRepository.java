package nts.uk.ctx.pr.proto.infra.repository.personalinfo.holiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaid;
import nts.uk.ctx.pr.proto.dom.personalinfo.holiday.HolidayPaidRepository;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.holiday.PhldtHolidayPaid;

@RequestScoped
public class JpaHolidayPaidRepository extends JpaRepository implements HolidayPaidRepository {
	
	private final String SELECT_BY_CCD_AND_PID = "SELECT c FROM PHLDT_HOLIDAY_PAID c WHERE c.CCD = :CCD and c.PID = :PID";

	@Override
	public List<HolidayPaid> findAll(String companyCode, List<String> personIdList) {
		List<HolidayPaid> lstHolidayPaid = new ArrayList<>();
		for (int i = 0; i < personIdList.size(); i++) {
			Optional<HolidayPaid> tmpHolidayPaid = (Optional<HolidayPaid>) this.queryProxy().query(SELECT_BY_CCD_AND_PID, PhldtHolidayPaid.class)
					.setParameter("CCD", companyCode)
					.setParameter("PID", personIdList.get(i))
					.getSingle(c -> toDomain(c));
			if(tmpHolidayPaid.isPresent()){
				lstHolidayPaid.add(tmpHolidayPaid.get());
			}
		}
		return lstHolidayPaid;
	}
	
	private static HolidayPaid toDomain(PhldtHolidayPaid entity){
		val domain = HolidayPaid.createFromJavaType(entity.remainDays, entity.remainTime, entity.phldtHolidayPaidPK.pId.toString());
		entity.toDomain(domain);
		return domain;
	}

}
