package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.InterimCommonRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

@Stateless
public class JpaInterimCommonRepository extends JpaRepository implements InterimCommonRepository {
	
	@Inject 
	private InterimRemainRepository interimRemainRepo;

	@Override
	public void deleteInterim(String sid, DatePeriod period, RemainType type) {
		
		/** TODO: 新テーブル対応待ち */
		val interim = interimRemainRepo.getRemainBySidPriod(sid, period, type);

		val interimIds = interim.stream().map(c -> c.getRemainManaID()).collect(Collectors.toList());
		
		internalDelete(sid, period, type, interimIds);
		
		interimRemainRepo.deleteBySidPeriodType(sid, period, type);
	}
	
	private void internalDelete(String sid, DatePeriod period, RemainType type, List<String> interimIds) {
		
		switch (type) {
		case ANNUAL:

			this.getEntityManager().createQuery(this.createDeleteQuery(type))
									.setParameter("sid", sid)
									.setParameter("start", period.start())
									.setParameter("end", period.end())
									.executeUpdate();
			break;

		case FUNDINGANNUAL:
		case BREAK:
		case SUBHOLIDAY:

			if(interimIds.isEmpty()) {
				return;
			}
			
			this.getEntityManager().createQuery(this.createDeleteQuery(type))
									.setParameter("ids", interimIds)
									.executeUpdate();
			break;
		default:
			break;
		}
	}
	
	private String createDeleteQuery(RemainType type) {

		switch (type) {
		case ANNUAL:
			return "DELETE FROM KshdtInterimHdpaid d WHERE d.pk.sid = :sid AND d.pk.ymd >= :start AND d.pk.ymd <= :end";
		case FUNDINGANNUAL:
		return "DELETE FROM KrcdtHdstkTemp d WHERE d.remainMngId IN :ids";
//			return "DELETE FROM KrcdtRsvleaMngTemp d WHERE d.PK.employeeId = :sid AND d.PK.ymd >= :start AND d.PK.ymd <= :end";
		case SPECIAL:
			return "DELETE FROM xxx d WHERE d.sid = :sid AND d.ymd >= :start AND d.ymd <= :end";
		case PAUSE:
			return "DELETE FROM xxx d WHERE d.sid = :sid AND d.ymd >= :start AND d.ymd <= :end";
		case BREAK:
			return "DELETE FROM KrcdtInterimHdwkMng d WHERE d.breakMngId IN :ids";
//			return "DELETE FROM KrcdtInterimHdwkMng d WHERE d.sid = :sid AND d.ymd >= :start AND d.ymd <= :end";
		case SUBHOLIDAY:
			return "DELETE FROM KrcmtInterimDayOffMng d WHERE d.dayOffMngId IN :ids";
//			return "DELETE FROM KrcmtInterimDayOffMng d WHERE d.sid = :sid AND d.ymd >= :start AND d.ymd <= :end";
		case PICKINGUP:
			return "DELETE FROM xxx d WHERE d.sid = :sid AND d.ymd >= :start AND d.ymd <= :end";
		case TIMEANNUAL:
			return "DELETE FROM xxx d WHERE d.sid = :sid AND d.ymd >= :start AND d.ymd <= :end";
		case CARE:
			return "DELETE FROM xxx d WHERE d.sid = :sid AND d.ymd >= :start AND d.ymd <= :end";
		case CHILDCARE:
			return "DELETE FROM xxx d WHERE d.sid = :sid AND d.ymd >= :start AND d.ymd <= :end";
		default:
			throw new RuntimeException("unkown remain type");
		}
	}
}
