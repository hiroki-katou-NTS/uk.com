package nts.uk.ctx.at.shared.dom.specialholiday.grantday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GrantRegularMethod {
	/** 0- 付与開始日を指定して付与する */
	GrantStartDateSpecify(0),
	/** 1- 付与日テーブルを参照して付与する */
	ReferGrantDateTable(1);

	public final int value;
}
