package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author nws_namnv2
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KrcmtSupportCardEditPk implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 会社ID
	@Column(name = "CID")
	public String cid;

}
