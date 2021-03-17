package nts.uk.cnv.infra.td.entity.uktabledesign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.alteration.Feature;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_UK_TABLE_FEATURE")
public class ScvmtUkTableFeature extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FEATURE_ID")
	private String featureId;

	@Column(name = "NAME")
	private String name;

	@Override
	protected Object getKey() {
		return featureId;
	}

	public Feature toDomain() {
		return new Feature(this.featureId, this.name);
	}
}
