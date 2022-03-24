/**
 * 
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author laitv
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KrcmtSupportCardPk  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 	会社ID
	@Column(name = "CID")
	public String cid;

	//  カード番号	
	@Column(name = "SUPPORT_CARD_NO")
	public String supportCardNo;

}
