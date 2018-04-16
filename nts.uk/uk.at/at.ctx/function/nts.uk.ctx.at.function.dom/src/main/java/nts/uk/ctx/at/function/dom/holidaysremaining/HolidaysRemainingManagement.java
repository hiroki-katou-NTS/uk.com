package nts.uk.ctx.at.function.dom.holidaysremaining;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author thanh.tq 休暇残数管理表の出力項目設定
 */
@Getter
@Setter
@NoArgsConstructor
public class HolidaysRemainingManagement extends AggregateRoot {
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 会社ID
	 */
	private String companyID;

	/**
	 * コード
	 */
	private String code;

	/**
	 * 出力する項目一覧
	 */
	private ItemOutputForm listItemsOutput;

	public HolidaysRemainingManagement(String name, String companyID, String code, ItemOutputForm listItemsOutput) {
		super();
		this.name = name;
		this.companyID = companyID;
		this.code = code;
		this.listItemsOutput = listItemsOutput;
	}

}
