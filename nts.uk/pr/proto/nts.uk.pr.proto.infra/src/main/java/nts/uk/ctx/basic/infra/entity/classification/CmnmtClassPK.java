package nts.uk.ctx.basic.infra.entity.classification;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CmnmtClassPK implements Serializable{

	private static final long serialVersionUID = 2057071023975099159L;
	
	private String companyCode;
	private String classificationCode;

}
