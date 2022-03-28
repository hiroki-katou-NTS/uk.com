/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 応援予定の機能制御Entity
 * @author laitv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_SUPPORT_FUNC_CTRL")
public class KscmtSupportFuncCtrl extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CID")
	private String companyID;

	@Column(name = "USE_SUPPORT")
	public boolean useSupport;
	
	@Column(name = "USE_SUPPORT_IN_TIMEZONE")
	public boolean useSupportInTimeZone;

	@Override
	protected Object getKey() {
		return this.companyID;
	}
}
