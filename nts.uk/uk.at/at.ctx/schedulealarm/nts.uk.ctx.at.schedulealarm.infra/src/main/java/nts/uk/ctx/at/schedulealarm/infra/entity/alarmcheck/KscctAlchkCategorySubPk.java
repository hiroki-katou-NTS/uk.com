package nts.uk.ctx.at.schedulealarm.infra.entity.alarmcheck;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscctAlchkCategorySubPk {
	/** 契約コード */
	@Column(name = "CONTRACT_CD")
	public String contractCd;

	/** 条件コード */
	@Column(name = "CD")
	public String code;
	
	/** サブ条件リスト.サブコード */
	@Column(name = "SUB_CD")
	public String subCode;
}
