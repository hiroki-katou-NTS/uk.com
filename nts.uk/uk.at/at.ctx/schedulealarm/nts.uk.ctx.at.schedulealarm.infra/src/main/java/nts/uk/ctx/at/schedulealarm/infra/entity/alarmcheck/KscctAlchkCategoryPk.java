package nts.uk.ctx.at.schedulealarm.infra.entity.alarmcheck;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 勤務予定のアラームチェック条件
 * @author lan_lt
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscctAlchkCategoryPk {
	/** 契約コード */
	@Column(name = "CONTRACT_CD")
	public String contractCd;

	/** 条件コード */
	@Column(name = "CD")
	public String code;
}
