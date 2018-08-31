/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.remainingnumber.nursingcareleavemanagement.interimdata;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ChildNursingRemainExport.
 */

@Data
@Builder
// 子看護の残数
public class ChildNursingRemainExport {

	/** The pre grant statement. */
	// 付与前明細
	private ChildNursingRemainInforExport preGrantStatement;

	/** The after grant statement. */
	// 付与後明細
	private Optional<ChildNursingRemainInforExport> afterGrantStatement;

	/** The is manage. */
	// 管理区分
	private Boolean isManage;

	/** The grant period flag. */
	// 期中付与フラグ
	private Boolean grantPeriodFlag;

}
