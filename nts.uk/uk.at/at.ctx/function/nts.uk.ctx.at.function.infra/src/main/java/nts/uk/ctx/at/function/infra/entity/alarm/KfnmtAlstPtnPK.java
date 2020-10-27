package nts.uk.ctx.at.function.infra.entity.alarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtAlstPtnPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")	
	public String companyID;
	
	@Column(name = "ALARM_PATTERN_CD")	
	public String alarmPatternCD;
}
