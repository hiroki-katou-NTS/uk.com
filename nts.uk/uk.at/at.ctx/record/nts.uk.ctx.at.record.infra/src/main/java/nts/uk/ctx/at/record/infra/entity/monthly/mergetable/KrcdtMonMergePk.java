package nts.uk.ctx.at.record.infra.entity.monthly.mergetable;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * プライマリキー：月別実績
 * @author lanlt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcdtMonMergePk implements Serializable{

	/** serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	@Basic(optional = false)
	@Column(name = "SID")
	private String employeeId;
	
	/** 年月 */	
	@Basic(optional = false)
	@Column(name = "YM")
	private int yearMonth;
	
	/** 締めID */
	@Basic(optional = false)
	@Column(name = "CLOSURE_ID")
	private int closureId;
	
	/** 締め日 */
	@Basic(optional = false)
	@Column(name = "CLOSURE_DAY")
	private int closureDay;
	
	/** 末日とする */
	@Basic(optional = false)
	@Column(name = "IS_LAST_DAY")
	private int isLastDay;
}
