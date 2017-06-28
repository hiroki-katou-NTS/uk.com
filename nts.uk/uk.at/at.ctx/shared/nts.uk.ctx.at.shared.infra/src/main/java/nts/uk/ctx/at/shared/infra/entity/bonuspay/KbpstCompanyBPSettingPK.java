package nts.uk.ctx.at.shared.infra.entity.bonuspay;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KbpstCompanyBPSettingPK {
	@Column(name = "CID")
	public String companyId;
}
