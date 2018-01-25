package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.primitive.ShowName;

/**
 * 申請一覧共通設定
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppCommonSet extends AggregateRoot{
	// 会社ID
	private String companyId;
	// 所属職場名表示
	private ShowName showWkpNameBelong;
	public static AppCommonSet createFromJavaType(String companyId, int showWkpNameBelong){
		return new AppCommonSet(companyId, EnumAdaptor.valueOf(showWkpNameBelong, ShowName.class));
	}
}
