package nts.uk.ctx.at.record.app.query.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReservationExportDto {
	/**
	 * 社員ID
	 */
	private String id;
	
	/**
	 * 個人社員基本情報.社員コード
	 */
	private String code;
	
	/**
	 * 個人社員基本情報.ビジネスネーム
	 */
	private String name;
	
	/**
	 * 社員指定期間所属職場履歴.職場表示名
	 */
	private String affiliationName;
	
	/**
	 * 年月.年月の開始日
	 */
	private String startDate;
	
	/**
	 * 年月.年月の終了日
	 */
	private String endDate;
}
