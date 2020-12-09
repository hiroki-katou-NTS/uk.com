/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.infra.entity.toppage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "CCGMT_TOP_PAGE")
public class CcgmtTopPage extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CcgmtTopPagePK ccgmtTopPagePK;

	@Column(name = "TOP_PAGE_NAME")
	public String topPageName;

	@Column(name = "LANG_NO")
	public int languageNumber;

	@Column(name = "LAYOUTID")
	public String layoutId;

	@Override
	protected Object getKey() {
		return ccgmtTopPagePK;
	}
}
