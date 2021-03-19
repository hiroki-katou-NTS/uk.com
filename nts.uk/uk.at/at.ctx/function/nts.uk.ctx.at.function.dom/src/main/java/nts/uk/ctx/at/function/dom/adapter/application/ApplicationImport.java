package nts.uk.ctx.at.function.dom.adapter.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class ApplicationImport {
	private String appID;
	/**
	 * appDate,申請日
	 */
	private GeneralDate appDate;
	/**
	 * appType,申請種類
	 */
	private Integer appType;
	/**
	 * employeeID,申請者
	 */
	private String employeeID;
	/**
	 * appTypeName,申請表示名※
	 */
	private String appTypeName;
	/**
	 * 反映状態: 0, "未反映"/1, "反映待ち"/2, "反映済"/3, "取消済"/4, "差し戻し"/5, "否認"
	 */
	private Integer state;
}
