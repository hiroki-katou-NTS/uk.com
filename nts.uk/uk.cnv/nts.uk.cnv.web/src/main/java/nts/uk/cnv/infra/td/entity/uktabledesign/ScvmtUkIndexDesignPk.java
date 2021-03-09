package nts.uk.cnv.infra.td.entity.uktabledesign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class ScvmtUkIndexDesignPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "TABLE_ID")
	private String tableId;

	@Column(name = "FEATURE_ID")
	private String featureId;

	@Column(name = "DATETIME")
	private GeneralDateTime datetime;

	@Column(name = "NAME")
	private String name;
}
