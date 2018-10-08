package nts.uk.ctx.exio.dom.exo.outcnddetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

/**
 * 出力条件詳細(定型)
 */
@AllArgsConstructor
@Getter
@Setter
public class OutCndDetail extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 条件設定コード
	 */
	@Setter
	private ConditionSettingCd conditionSettingCd;

	/**
	 * 条件SQL
	 */
	private ExterOutCdnSql exterOutCdnSql;
	
	@Setter
	private List<OutCndDetailItem> listOutCndDetailItem;

	public OutCndDetail(String cid, String conditionSettingCd, String exterOutCdnSql,
			List<OutCndDetailItem> listOutCndDetailItem) {
		this.cid = cid;
		this.conditionSettingCd = new ConditionSettingCd(conditionSettingCd);
		this.exterOutCdnSql = new ExterOutCdnSql(exterOutCdnSql);
		this.listOutCndDetailItem = listOutCndDetailItem;
	}

}
