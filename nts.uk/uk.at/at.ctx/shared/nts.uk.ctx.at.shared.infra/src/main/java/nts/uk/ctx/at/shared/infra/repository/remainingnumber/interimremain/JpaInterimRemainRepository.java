package nts.uk.ctx.at.shared.infra.repository.remainingnumber.interimremain;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreaterAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.breakdayoff.interim.KrcmtInterimBreakMng;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.interimremain.KrcmtInterimRemainMng;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class JpaInterimRemainRepository extends JpaRepository  implements InterimRemainRepository{
	private String QUERY_BY_SID_PRIOD = "SELECT c FROM KrcmtInterimRemainMng c"
			+ " WHERE c.sId = :employeeId"
			+ " AND c.ymd >= :startDate"
			+ " AND c.ymd <= :endDate"
			+ " AND c.remainType = :remainType";
	@Override
	public List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData, RemainType remainType) {
		return this.queryProxy().query(QUERY_BY_SID_PRIOD, KrcmtInterimRemainMng.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", dateData.start())
				.setParameter("endDate", dateData.end())
				.setParameter("remainType", remainType.value)
				.getList(c -> convertToDomainSet(c));
	}
	private InterimRemain convertToDomainSet(KrcmtInterimRemainMng c) {		
		return new InterimRemain(c.remainMngId, 
				c.sId, 
				c.ymd, 
				EnumAdaptor.valueOf(c.createrAtr, CreaterAtr.class), 
				EnumAdaptor.valueOf(c.remainType, RemainType.class),
				EnumAdaptor.valueOf(c.remainAtr, RemainAtr.class));
	}
	@Override
	public Optional<InterimRemain> getById(String remainId) {
		return this.queryProxy().find(remainId, KrcmtInterimRemainMng.class)
				.map(x -> convertToDomainSet(x));
	}
	

}
