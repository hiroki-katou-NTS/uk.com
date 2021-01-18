package nts.uk.ctx.at.function.dom.adapter.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationStateImport {

	/**
	 * 申請日
	 */
	private GeneralDate appDate;
	
	/**
	 * 申請種類
	 */
	private Integer appType;
	
	/**
	 * 申請者
	 */
	private String employeeID;
	
	/**
	 * 申請表示名
	 */
	private String appTypeName;
	
	/**
	 * 反映状態: 0, "未反映"/1, "反映待ち"/2, "反映済"/3, "取消済"/4, "差し戻し"/5, "否認"
	 */
	private Integer reflectState;
	/**
	 * 事前事後区分: 0: "事前", 1: "事後"
	 */
	private Integer prePostAtr;

}
