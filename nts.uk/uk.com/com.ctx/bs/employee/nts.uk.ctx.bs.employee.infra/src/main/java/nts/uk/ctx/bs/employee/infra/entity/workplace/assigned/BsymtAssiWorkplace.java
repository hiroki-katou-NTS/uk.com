package nts.uk.ctx.bs.employee.infra.entity.workplace.assigned;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 所属職場履歴
 * 
 * @author xuan vinh
 *
 */
@Getter
@Setter
@Entity
@Table(name = "BSYMT_ASSI_WORKPLACE")
public class BsymtAssiWorkplace extends UkJpaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private BsymtAssiWorkplacePK bsymtAssiWorkplacePK;
	
	/** the assign workplace id*/
	@Column(name = "ASSI_WORKPLACE_ID")
	private String assiWorkplaceId;
	
	/** the workplace id*/
	@Column(name = "WORKPLACE_ID")
	private String workplaceId;
	
	/** The end D. */
	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate strD;
	
	/** The end D. */
	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endD;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
