package nts.uk.ctx.bs.person.infra.entity.person.info.setting.copy;

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
	@Column(name = "EMP_COPY_CTG_ID")
	public String categoryId;

}
