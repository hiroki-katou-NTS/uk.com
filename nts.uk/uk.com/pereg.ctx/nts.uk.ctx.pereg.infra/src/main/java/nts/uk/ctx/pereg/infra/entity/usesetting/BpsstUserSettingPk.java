package nts.uk.ctx.pereg.infra.entity.usesetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BpsstUserSettingPk implements Serializable {

	private static final long serialVersionUID = 1L;
	// 社員ID
	@Basic(optional = false)
	@Column(name = "SID")
	public String employeeId;

}
