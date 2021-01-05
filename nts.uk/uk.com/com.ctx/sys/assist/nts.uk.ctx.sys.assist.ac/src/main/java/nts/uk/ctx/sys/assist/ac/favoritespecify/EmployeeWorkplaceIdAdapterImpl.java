package nts.uk.ctx.sys.assist.ac.favoritespecify;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.sys.assist.dom.favorite.adapter.EmployeeWorkplaceIdAdapter;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmployeeWorkplaceIdAdapterImpl implements EmployeeWorkplaceIdAdapter{
	@Inject
	private WorkplacePub workplacePub;
	
	@Override
	public Map<String, String> getWorkplaceId(List<String> sIds, GeneralDate baseDate) {
		// ＄所属職場　＝　RQ.社員ID（List）と基準日から所属職場IDを取得(社員IDリスト、基準日)　//List<所属職場履歴項目>
		List<AffWorkplaceHistoryExport> listAffWorkplace =  this.workplacePub.getWorkplaceBySidsAndBaseDate(sIds, baseDate);
		return null;
	}

}
