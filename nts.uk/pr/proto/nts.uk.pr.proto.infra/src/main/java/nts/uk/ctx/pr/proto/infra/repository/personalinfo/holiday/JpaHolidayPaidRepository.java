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

	private final String SELECT_BY_CCD_AND_PID = "SELECT c FROM PhldtHolidayPaid WHERE c.phldtHolidayPaidPK.ccd = :CCD and c.phldtHolidayPaidPK.pId IN (:PID)";

	@Override
	public List<HolidayPaid> findAll(String companyCode, List<String> personIdList) {
		return this.queryProxy().query(SELECT_BY_CCD_AND_PID, PhldtHolidayPaid.class).setParameter("CCD", companyCode)
				.setParameter("PID", personIdList).getList(c -> toDomain(c));
	}

	private static HolidayPaid toDomain(PhldtHolidayPaid entity) {
		val domain = HolidayPaid.createFromJavaType(entity.remainDays, entity.remainTime,
				entity.phldtHolidayPaidPK.pId.toString());
		entity.toDomain(domain);
		return domain;
	}

	@Override
	public Optional<HolidayPaid> find(String companyCode, String personId) {
		// TODO Auto-generated method stub
		return null;
	}

}
