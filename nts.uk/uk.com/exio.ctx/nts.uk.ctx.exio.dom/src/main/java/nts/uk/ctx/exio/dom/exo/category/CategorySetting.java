package nts.uk.ctx.exio.dom.exo.category;

/**
 * カテゴリ設定
 */
public enum CategorySetting {
	/**
	 * 出力しない
	 */
	CATEGORY_SETTING(0),
	/**
	 * データ系タイプ
	 */
	DATA_TYPE(6),
	/**
	 * マスタ系タイプ(基準日のみ設定)
	 */
	MASTER_TYPE(7),
	/**
	 * マスタ系対ぴ(設定なし)
	 */
	MASTER_SYSTEM(8);

	/** The value. */
	public final int value;

	private CategorySetting(int value) {
		this.value = value;
	}
}
