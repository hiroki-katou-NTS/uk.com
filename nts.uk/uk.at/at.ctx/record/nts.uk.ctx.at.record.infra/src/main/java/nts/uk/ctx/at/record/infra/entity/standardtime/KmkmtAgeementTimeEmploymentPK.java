package nts.uk.ctx.at.record.infra.entity.standardtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.query.DBCharPaddingAs;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KmkmtAgeementTimeEmploymentPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyId;

	@Column(name = "BASIC_SETTING_ID")
	public String basicSettingId;

	@Column(name = "EMP_CTG_CODE")
	@DBCharPaddingAs(EmploymentCode.class)
	public String employmentCategoryCode;
}
