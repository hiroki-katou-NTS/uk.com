package nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author yennth
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscstHoriCalDaysSetPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 会社ID **/
	@Column(name = "CID")
	public String companyId;
	/** カテゴリコード **/
	@Column(name = "CATEGORY_CD")
	public String categoryCode;
}
