package nts.uk.file.at.infra.setworkinghoursanddays;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.setworkinghoursanddays.SetWorkingHoursAndDaysExRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class jpaSetWorkingHoursAndDays extends JpaRepository implements SetWorkingHoursAndDaysExRepository {

	private static final String GET_USAGE = "SELECT s.IS_EMP, s.IS_WKP, s.IS_EMPT FROM KUWST_USAGE_UNIT_WT_SET s WHERE s.CID = ?cid ";

	@Override
	public Object[] getUsage() {
		String cid = AppContexts.user().companyId();
		Query usage = this.getEntityManager().createNativeQuery(GET_USAGE.toString()).setParameter("cid", cid);
		List<Object[]> data = usage.getResultList();
		if (data.size() == 0) {
			return null;
		}
		return data.get(0);
	}
}