package nts.uk.ctx.pereg.infra.entity.roles.functionauth;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Table(name = "PPEMT_PER_INFO_FUNCTION")
// @Entity
public class PpemtPersonInfoFunction extends UkJpaEntity {

	@Id
	@Column(name = "FUNCTION_NO")
	public int functionNo; 
	
	@Column(name = "FUNCTION_NAME")
	public String functionName; 
	
	@Column(name = "DESCRIPTION")
	public String description; 
	
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder; 
	
	@Column(name = "DEFAULT_VALUE")
	public int defaultValue; 
	
	@Override
	protected Object getKey() {
		return functionNo;
	}

}
