package nts.uk.ctx.exio.app.command.exo.outcnddetail;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;

/**
 * 出力条件詳細(定型)
 */
@Value
public class OutCndDetailCommand {
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 条件設定コード
	 */
	private String conditionSettingCd;

	/**
	 * 条件SQL
	 */
	private String exterOutCdnSql;

	private List<OutCndDetailItemCommand> listOutCndDetailItem;

	public OutCndDetail toDomain() {
		return new OutCndDetail(this.cid, this.conditionSettingCd, this.exterOutCdnSql,
				this.listOutCndDetailItem.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
}
