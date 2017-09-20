package entity.person.info.setting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BsydtEmployeeRegistrationHistoryPk implements Serializable {

	
	@Basic(optional = false)
	@Column(name = "CID")
	public String CompanyId;
	
	private static final long serialVersionUID = 1L;

}
