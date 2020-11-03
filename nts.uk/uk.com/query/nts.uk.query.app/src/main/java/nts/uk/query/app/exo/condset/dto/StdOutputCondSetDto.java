package nts.uk.query.app.exo.condset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;

/**
 * The Standard output condition setting dto.<br>
 * 出力条件設定（定型）
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class StdOutputCondSetDto implements StdOutputCondSet.MementoSetter {

	/** 会社ID */
	private String companyId;

	/** 外部出力条件コード */
	private String conditionSetCode;

	/** カテゴリID */
	private int categoryId;

	/** 区切り文字 */
	private int delimiter;

	/** するしない区分 */
	private int itemOutputName;

	/** するしない区分 */
	private int autoExecution;

	/** 外部出力条件名称 */
	private String conditionSetName;

	/** するしない区分 */
	private int conditionOutputName;

	/** 文字列形式 */
	private int stringFormat;

	/**
	 * No args constructor.
	 */
	private StdOutputCondSetDto() {
	}

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain 出力条件設定（定型）
	 * @return the dto 出力条件設定（定型）
	 */
	public static StdOutputCondSetDto createFromDomain(StdOutputCondSet domain) {
		if (domain == null) {
			return null;
		}
		StdOutputCondSetDto dto = new StdOutputCondSetDto();
		domain.setMemento(dto);
		return dto;
	}

}
