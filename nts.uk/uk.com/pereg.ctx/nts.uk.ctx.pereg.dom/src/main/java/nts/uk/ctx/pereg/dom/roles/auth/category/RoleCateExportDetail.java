package nts.uk.ctx.pereg.dom.roles.auth.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleCateExportDetail {
	
	private String roleCode;
	
	private String roleName;
	
	private String categoryName;
	
	private Boolean isCateConfig;
	
	private Integer otherPersonAuthType;

	private Integer allowPersonRef;

	private Integer allowOtherRef;

	private Integer selfPastHisAuth;

	private Integer selfFutureHisAuth;

	private Integer selfAllowAddHis;

	private Integer selfAllowDelHis;

	private Integer otherPastHisAuth;

	private Integer otherFutureHisAuth;

	private Integer otherAllowAddHis;

	private Integer otherAllowDelHis;

	private Integer selfAllowAddMulti;

	private Integer selfAllowDelMulti;

	private Integer otherAllowAddMulti;

	private Integer otherAllowDelMulti;
	
	private Boolean isItemConfig;
	
	private Integer categoryType;
	
	private String itemName;
	
	private Integer otherAuth;
	
	private Integer selfAuth;
	
}
