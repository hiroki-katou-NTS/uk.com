package nts.uk.ctx.hr.shared.infra.entity.report.registration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PpedtPreReflectAnyItemPk implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "HIST_ID")
	public String histId; // 新規GUIDをセット
	
	@NotNull
	@Column(name = "PARENT_HIST_ID")
	public String parentHistId; // (※1)で作成したGUIDをセット
}
