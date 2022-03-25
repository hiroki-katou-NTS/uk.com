package nts.uk.screen.com.app.find.cmm030.b;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.workplace.AffAtWorkplaceExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;
import nts.uk.screen.com.app.find.cmm018.ScreenQueryApprovalAuthorityEmp;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class Cmm030BScreenQuery {

	@Inject
	private WorkplacePub workplacePub;

	@Inject
	private SyEmployeePub syEmployeePub;

	@Inject
	private ScreenQueryApprovalAuthorityEmp screenQueryApprovalAuthorityEmp;

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.B：社員選択.メニュー別OCD.Ｂ：所属職場IDを取得する.Ｂ：所属職場IDを取得する
	 * 
	 * @param sids     List<社員ID>
	 * @param baseDate 基準日
	 * @return 所属職場履歴項目
	 */
	public List<AffAtWorkplaceExport> getWorkplaceInfo(List<String> sids, GeneralDate baseDate) {
		// 社員ID（List）と基準日から所属職場IDを取得
		return this.workplacePub.findBySIdAndBaseDateV2(sids, baseDate);
	}

	/**
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM030_自分の承認者設定.B：社員選択.メニュー別OCD.Ｂ：承認権限保持者を取得する.Ｂ：承認権限保持者を取得する
	 * 
	 * @param workplaceId 職場ID
	 * @param baseDate    基準日
	 * @return 社員ID（List）から社員コードと表示名を取得
	 */
	public List<ResultRequest600Export> getApprovalAuthorityHolders(String workplaceId, GeneralDate baseDate) {
		// 取得する(職場ID, 基準日)
		List<String> sids = this.screenQueryApprovalAuthorityEmp.get(Arrays.asList(workplaceId), baseDate,
				SystemAtr.WORK.value);
		// [RQ600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
		return this.syEmployeePub.getEmpInfoLstBySids(sids, null, false, false);
	}
}
