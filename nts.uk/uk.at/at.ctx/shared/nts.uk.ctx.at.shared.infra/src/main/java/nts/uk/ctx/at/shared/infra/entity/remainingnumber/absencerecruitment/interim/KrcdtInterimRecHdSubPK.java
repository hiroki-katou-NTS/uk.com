package nts.uk.ctx.at.shared.infra.entity.remainingnumber.absencerecruitment.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtInterimRecHdSubPK implements Serializable{

	/**	振休管理データID */
	@Column(name = "ABSENCE_MNG_ID")
	public String absenceMngID;
	/**	振出管理データID */
	@Column(name = "RECRUITMENT_MNG_ID")
	public String recruitmentMngId;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
