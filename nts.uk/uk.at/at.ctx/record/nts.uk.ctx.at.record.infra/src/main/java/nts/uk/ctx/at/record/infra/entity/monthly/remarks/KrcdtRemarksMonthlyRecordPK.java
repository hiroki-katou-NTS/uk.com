package nts.uk.ctx.at.record.infra.entity.monthly.remarks;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 
 * @author phongtq
 *
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtRemarksMonthlyRecordPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	@Column(name = "SID")
	public String employeeID;
	
	/** 締めID */
	@Column(name = "CLOSURE_ID")
	public int closureID;
	
	/** 備考欄NO */
	@Column(name = "REMARKS_NO")
	public int remarksNo;
	
	
}
