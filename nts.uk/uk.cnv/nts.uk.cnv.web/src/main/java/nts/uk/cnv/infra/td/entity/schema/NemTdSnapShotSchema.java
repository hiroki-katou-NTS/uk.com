package nts.uk.cnv.infra.td.entity.schema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.feature.Feature;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_SNAPSHOT_SCHEMA")
public class NemTdSnapShotSchema extends JpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SNAPSHOT_ID")
	private String snapShotId;

	@Column(name = "GENERATED_AT")
	private GeneralDateTime generatedAt;

	@Column(name = "SOURCE_EVENT_ID")
	private String sourceEventId;
	
	@Override
	protected Object getKey() {
		return snapShotId;
	}
	
	public SchemaSnapshot toDomain() {
		return new SchemaSnapshot(
				this.snapShotId, 
				this.generatedAt, 
				this.sourceEventId);
	}
	
	public static NemTdSnapShotSchema toEntity(SchemaSnapshot snapShot) {
		return new NemTdSnapShotSchema(
				snapShot.getSnapshotId(), 
				snapShot.getGeneratedAt(),
				snapShot.getSourceEventId());
	}
}
