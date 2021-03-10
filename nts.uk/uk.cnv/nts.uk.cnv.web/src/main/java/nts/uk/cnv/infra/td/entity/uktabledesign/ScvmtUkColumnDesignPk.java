package nts.uk.cnv.infra.td.entity.uktabledesign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ScvmtUkColumnDesignPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "TABLE_ID")
	public String tableId;

	@Column(name = "FEATURE_ID")
	private String featureId;

	@Column(name = "DATETIME")
	private GeneralDateTime datetime;

	@Column(name = "ID")
	public String id;

}
