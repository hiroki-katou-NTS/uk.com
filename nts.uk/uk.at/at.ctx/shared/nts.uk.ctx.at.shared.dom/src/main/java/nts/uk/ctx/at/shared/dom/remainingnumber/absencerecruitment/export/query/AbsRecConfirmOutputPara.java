package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AbsRecConfirmOutputPara {
	/** 振出管理データ	 */
	List<PayoutManagementData> lstRecConfirm;
	/**
	 * 振休管理データ
	 */
	List<SubstitutionOfHDManagementData> lstAbsConfirm;
	/**
	 * 振出振休紐付け管理
	 */
	List<PayoutSubofHDManagement> lstAbsRecConfirm;
}
