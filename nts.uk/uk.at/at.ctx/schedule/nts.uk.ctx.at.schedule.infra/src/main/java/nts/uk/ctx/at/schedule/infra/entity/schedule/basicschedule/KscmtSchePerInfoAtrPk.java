package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author trungtran
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KscmtSchePerInfoAtrPk implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/** 個人情報区分 */
	@Column(name = "PERSON_INFO_ATR")
	public int personInfoAtr;
}
