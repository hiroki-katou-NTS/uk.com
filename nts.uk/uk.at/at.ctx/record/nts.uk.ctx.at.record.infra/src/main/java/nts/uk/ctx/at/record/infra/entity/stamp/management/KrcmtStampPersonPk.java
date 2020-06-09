package nts.uk.ctx.at.record.infra.entity.stamp.management;

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

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KrcmtStampPersonPk implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
	
}
