package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.declare;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * プライマリキー：申告設定
 * @author shuichi_ishida
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcmtDeclareSetPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
}
