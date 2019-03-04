/**
 * 
 */
package nts.uk.ctx.at.shared.app.find.remainingnumber.yearholidaymanagement.employeeinfor.basicinfor.export.query;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hieult
 *
 */
@Stateless
public class BasicHolidayEmpInforFinder  {
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaBasicInfoRepo;

	public List<AnnualLeaveEmpBasicInfo> getData(List<String> listEmpId) {
		// 年休社員基本情報
		// 年休社員基本情報を取得する
		String cid = AppContexts.user().companyId();
		List<AnnualLeaveEmpBasicInfo> data = annLeaBasicInfoRepo.getAll(cid, listEmpId);
		return data;

	}
}
