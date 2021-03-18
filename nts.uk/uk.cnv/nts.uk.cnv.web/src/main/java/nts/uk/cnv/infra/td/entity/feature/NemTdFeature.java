package nts.uk.cnv.infra.td.entity.feature;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.feature.Feature;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_FEATURE")
public class NemTdFeature extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FEATURE_ID")
	private String featureId;

	@Column(name = "NAME")
	private String featureName;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Override
	protected Object getKey() {
		return featureId;
	}
	
	public Feature toDomain() {
		return new Feature(
				featureId, 
				featureName, 
				description);
	}
	
	public static NemTdFeature toEntity(Feature domain) {
		return new NemTdFeature(
				domain.getFeatureId(), 
				domain.getName(), 
				domain.getDescription());
	}
}
