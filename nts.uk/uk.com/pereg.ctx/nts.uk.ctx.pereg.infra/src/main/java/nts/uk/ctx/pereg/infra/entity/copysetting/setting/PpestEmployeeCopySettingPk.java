package nts.uk.ctx.pereg.infra.entity.copysetting.setting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpestEmployeeCopySettingPk implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "PER_INFO_CTG_ID")
	public String categoryId;

}
