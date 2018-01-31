package nts.uk.ctx.at.request.ac.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class ReqWorkplaceAdapterImpl implements WorkplaceAdapter{

	@Inject
	private SyWorkplacePub wkpPub;
	/**
	 * アルゴリズム「社員から職場を取得する」を実行する
	 */
	@Override
	public WkpHistImport findWkpBySid(String sID, GeneralDate date) {
		Optional<SWkpHistExport> wkpExport = wkpPub.findBySid(sID, date);
		if(wkpExport.isPresent()){
			return toImport(wkpExport.get());
		}
		return null;
	}

	private WkpHistImport toImport(SWkpHistExport export){
		return new WkpHistImport(export.getDateRange(), export.getEmployeeId(),
				export.getWorkplaceId(), export.getWorkplaceCode(),
				export.getWorkplaceName(),
				export.getWkpDisplayName());
	}
}
