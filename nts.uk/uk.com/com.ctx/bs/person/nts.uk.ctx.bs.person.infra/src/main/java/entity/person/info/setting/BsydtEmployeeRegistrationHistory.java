package entity.person.info.setting;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BPSST_USER_SET")
public class BsydtEmployeeRegistrationHistory extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public BsydtEmployeeRegistrationHistoryPk bsydtEmployeeRegistrationHistoryPk;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return bsydtEmployeeRegistrationHistoryPk;
	}

}
