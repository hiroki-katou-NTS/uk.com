package nts.uk.ctx.pereg.infra.entity.person.info.ctg;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpemtCtgCommonPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	public String contractCd;

	@Basic(optional = false)
	@Column(name = "CATEGORY_CD")
	public String categoryCd;

}
