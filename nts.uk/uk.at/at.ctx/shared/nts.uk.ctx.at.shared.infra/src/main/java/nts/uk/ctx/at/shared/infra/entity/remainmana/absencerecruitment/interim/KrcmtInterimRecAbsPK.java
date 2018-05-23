package nts.uk.ctx.at.shared.infra.entity.remainmana.absencerecruitment.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtInterimRecAbsPK implements Serializable{

	/**	振休管理データID */
	@Column(name = "ABSENCE_MANA_ID")
	public String absenceManaID;
	/**	振出管理データID */
	@Column(name = "RECRUITMENT_MANA_ID")
	public String recruitmentManaId;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
