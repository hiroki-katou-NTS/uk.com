package nts.uk.ctx.at.request.dom.setting.company.displayname;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 休暇申請種類表示名
 * @author yennth
 * Chú ý: DB không có dữ liệu, domain có thể không dùng nữa. Sử dụng name lấy từ domain HdAppSet
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HdAppDispName extends AggregateRoot{
	// 会社ID
	private String companyId;
	// 休暇申請種類
	private HdAppType hdAppType;
	// 表示名
	private DispName dispName;
	
	public static HdAppDispName createFromJavaType(String companyId, int hdAppType, String dispName){
		return new HdAppDispName(companyId, EnumAdaptor.valueOf(hdAppType, HdAppType.class), 
				new DispName(dispName));
	}
}
