package nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmlstrole.KfnmtALstWkpPms;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmlstrole.KfnmtALstWkpPmsPk;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition.KfnmtPtnMapCat;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition.KfnmtWkpCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * entity : アラームリストパターン設定(職場別)
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALSTWKP_PTN")
public class KfnmtALstWkpPtn extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtALstWkpPtnPk pk;

	@Column(name = "CONTRACT_CD")
	public String contractCode;

	@Column(name = "ALARM_PATTERN_NAME")
	public String alarmPatternName;

	@Column(name = "PERMISSION_SET")
	public boolean authSetting;

	@OneToMany(mappedBy = "kfnmtALstWkpPtn", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_ALSTWKP_PMS")
	public List<KfnmtALstWkpPms> alarmPerSet;

	@OneToMany(mappedBy = "kfnmtALstWkpPtn", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_PTN_MAP_CAT")
	public List<KfnmtWkpCheckCondition> checkConList;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
