package nts.uk.ctx.at.request.infra.entity.setting.request.gobackdirectlycommon;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@Setter
public class KrqstAppGoBackDirectlyPK {
	private static final long serialVersionUID = 1L;
	/** 会社ID */
	@Column(name = "CID")
	public String companyID;
}
