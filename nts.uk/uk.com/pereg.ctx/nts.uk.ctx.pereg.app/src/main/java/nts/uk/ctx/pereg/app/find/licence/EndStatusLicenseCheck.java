package nts.uk.ctx.pereg.app.find.licence;

public enum EndStatusLicenseCheck {

	/** Trạng thái: Vượt Quá Giới Hạn License */
	OVER(0, "ライセンス超過を返す"),
	
	/** Trạng thái: Bằng Giới Hạn License */
	REACHED(1, "ライセンス到達を返す"),
	
	/** Trạng thái: Cảnh Báo Giới Hạn License */
	WARNING(2, "ライセンス警告を返す"),
		
	/** Trạng thái: Bình Thường */
	NORMAL(3, "正常を返す");

	/** The value. */
	public final int value;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static EndStatusLicenseCheck[] values = EndStatusLicenseCheck.values();

	private EndStatusLicenseCheck(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public static EndStatusLicenseCheck valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}
		// Find value.
		for (EndStatusLicenseCheck val : EndStatusLicenseCheck.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
