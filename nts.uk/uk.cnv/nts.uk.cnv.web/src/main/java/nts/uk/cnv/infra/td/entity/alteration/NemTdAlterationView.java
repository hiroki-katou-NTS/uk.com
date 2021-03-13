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
import nts.uk.cnv.dom.td.alteration.Alteration;

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
	private String AlterationId;

	@Column(name = "TABLE_ID")
	private String tableId;

	@Column(name = "ORDERED")
	private boolean ordered;

	@Column(name = "DELIVERED")
	private boolean delivered;

	@Column(name = "ACCEPTED")
	private boolean accepted;

	@Override
	protected Object getKey() {
		return AlterationId;
	}

	public Alteration toDomain() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
