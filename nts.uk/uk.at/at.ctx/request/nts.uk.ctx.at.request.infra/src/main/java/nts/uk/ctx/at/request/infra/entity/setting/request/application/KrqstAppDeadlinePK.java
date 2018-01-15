package nts.uk.ctx.at.request.infra.entity.setting.request.application;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqstAppDeadlinePK implements Serializable{
	private static final long serialVersionUID = 1L;
	/**会社ID*/
	@Column(name = "CID")
	public String companyId;
	/**
	 * 締めＩＤ
	 */
	@Column(name = "CLOSURE_ID")
	public int closureId;
}
