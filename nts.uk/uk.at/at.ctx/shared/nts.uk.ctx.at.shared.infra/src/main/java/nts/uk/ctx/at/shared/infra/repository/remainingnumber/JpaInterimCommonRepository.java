package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.InterimCommonRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

@Stateless
public class JpaInterimCommonRepository extends JpaRepository implements InterimCommonRepository {

	@Override
	public void deleteInterim(String sid, DatePeriod period, RemainType type) {

		this.getEntityManager().createQuery(this.createDeleteQuery(type))
			.setParameter("sid", sid)
			.setParameter("start", period.start())
			.setParameter("end", period.end())
			.executeUpdate();
	}
	
	private String createDeleteQuery(RemainType type) {

		switch (type) {
		case ANNUAL:
			return "DELETE FROM KshdtInterimHdpaid d WHERE pk.sid = :sid AND pk.ymd >= :start AND pk.ymd <= :end";
		case FUNDINGANNUAL:
			return "DELETE FROM KrcdtRsvleaMngTemp d WHERE PK.employeeId = :sid AND PK.ymd >= :start AND PK.ymd <= :end";
		case SPECIAL:
			return "DELETE FROM xxx d WHERE sid = :sid AND ymd >= :start AND ymd <= :end";
		case PAUSE:
			return "DELETE FROM xxx d WHERE sid = :sid AND ymd >= :start AND ymd <= :end";
		case BREAK:
			return "DELETE FROM KrcdtInterimHdwkMng d WHERE sid = :sid AND ymd >= :start AND ymd <= :end";
		case SUBHOLIDAY:
			return "DELETE FROM KrcmtInterimDayOffMng d WHERE sid = :sid AND ymd >= :start AND ymd <= :end";
		case PICKINGUP:
			return "DELETE FROM xxx d WHERE sid = :sid AND ymd >= :start AND ymd <= :end";
		case TIMEANNUAL:
			return "DELETE FROM xxx d WHERE sid = :sid AND ymd >= :start AND ymd <= :end";
		case CARE:
			return "DELETE FROM xxx d WHERE sid = :sid AND ymd >= :start AND ymd <= :end";
		case CHILDCARE:
			return "DELETE FROM xxx d WHERE sid = :sid AND ymd >= :start AND ymd <= :end";
		default:
			throw new RuntimeException("unkown remain type");
		}
	}
}
