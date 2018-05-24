package nts.uk.ctx.at.shared.infra.repository.remainmana.interimremain;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive.CreaterAtr;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.infra.entity.remainmana.interimremain.KrcmtInterimRemainMana;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class JpaInterimRemainRepository extends JpaRepository  implements InterimRemainRepository{
	private String QUERY_BY_SID_PRIOD = "SELECT c FROM KrcmtInterimRemainMana c"
			+ " WHERE c.sId = :employeeId"
			+ " AND c.ymd >= :startDate"
			+ " AND c.ymd <= :endDate";
	@Override
	public List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData) {
		return this.queryProxy().query(QUERY_BY_SID_PRIOD, KrcmtInterimRemainMana.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", dateData.start())
				.setParameter("endDate", dateData.end())
				.getList(c -> convertToDomainSet(c));
	}
	private InterimRemain convertToDomainSet(KrcmtInterimRemainMana c) {		
		return new InterimRemain(c.remainManaId, 
				c.sId, 
				c.ymd, 
				EnumAdaptor.valueOf(c.createrAtr, CreaterAtr.class), 
				EnumAdaptor.valueOf(c.remainType, RemainType.class),
				EnumAdaptor.valueOf(c.remainAtr, RemainAtr.class));
	}

}
