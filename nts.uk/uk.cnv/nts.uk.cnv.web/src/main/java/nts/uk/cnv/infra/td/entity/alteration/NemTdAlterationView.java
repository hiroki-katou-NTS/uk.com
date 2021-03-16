package nts.uk.cnv.infra.td.entity.alteration;

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
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.DevelopmentState;
import nts.uk.cnv.dom.td.alteration.summary.TableIdInfo;

/**
 * おるたの状態ビュー
 * @author ai_muto
 *
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALTERATION_VIEW")
public class NemTdAlterationView extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ALTERATION_ID")
	private String alterationId;

	@Column(name = "TABLE_ID")
	private String tableId;

	@Column(name = "FEATURE_ID")
	private String featureId;

	@Column(name = "DATETIME")
	private GeneralDateTime time;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "IS_ORDERED")
	private boolean ordered;

	@Column(name = "IS_DELIVERED")
	private boolean delivered;

	@Column(name = "IS_ACCEPTED")
	private boolean accepted;

	@Override
	protected Object getKey() {
		return alterationId;
	}

	public AlterationSummary toDomain() {
		return new AlterationSummary(
				this.alterationId,
				this.time,
				new TableIdInfo(
						this.tableId,
						"テーブル名なんて取れないよお…困った"
					),
				convertState(),
				new AlterationMetaData(this.userName, this.comment),
				this.featureId
			);
	}

	private DevelopmentState convertState() {
		if(this.accepted) return DevelopmentState.ACCEPTED;

		if(this.delivered) return DevelopmentState.DELIVERED;

		if(this.ordered) return DevelopmentState.ORDERED;

		return DevelopmentState.NOT_ORDERING;
	}
}
