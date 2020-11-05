package nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 古いクラス → 削除予定 → 使わないでください (Old Class → Delete plan → Please don't use it)
 * @author hieult
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqdtAppLateOrLeavePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "CID")
	public String companyID;
	
	@NotNull
	@Column(name = "APP_ID")
	public String appID;
}
