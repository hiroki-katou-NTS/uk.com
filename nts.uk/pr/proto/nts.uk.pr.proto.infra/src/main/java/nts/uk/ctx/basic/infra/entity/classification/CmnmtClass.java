package nts.uk.ctx.basic.infra.entity.classification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="CMNMT_CLASS")
@IdClass(CmnmtClassPK.class)
@AllArgsConstructor
@NoArgsConstructor
public class CmnmtClass implements Serializable {
	
	private static final long serialVersionUID = 4161327404158928689L;

	@Id
	@Column(name="CCD")
	private String companyCode;
	
	@Id
	@Column(name="CLSCD")
	private String classificationCode;
	
	@Column(name="CLSNAME")
	private String name;
	
	@Column(name="CLS_OUT_CD")
	private String outCode;
	
	@Column(name="MEMO")
	private String memo;

}
