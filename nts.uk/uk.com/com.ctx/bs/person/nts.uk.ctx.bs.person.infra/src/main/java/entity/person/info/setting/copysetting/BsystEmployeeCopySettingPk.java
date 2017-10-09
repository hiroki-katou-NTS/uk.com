package entity.person.info.setting.copysetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BsystEmployeeCopySettingPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "COPY_CTG_ID")
	public String categoryId;

}
