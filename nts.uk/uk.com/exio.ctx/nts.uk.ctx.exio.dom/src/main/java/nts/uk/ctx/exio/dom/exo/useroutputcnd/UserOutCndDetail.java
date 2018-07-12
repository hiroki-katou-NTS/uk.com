package nts.uk.ctx.exio.dom.exo.useroutputcnd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.outcnddetail.ConditionSettingCd;
import nts.uk.ctx.exio.dom.exo.outcnddetail.ExterOutCdnSql;


/**
* 出力条件詳細
*/
@AllArgsConstructor
@Getter
public class UserOutCndDetail extends AggregateRoot
{
    
    /**
    * 条件SQL
    */
    private ExterOutCdnSql cndSql;
    
    /**
    * 条件設定コード
    */
    private ConditionSettingCd cndSetCd;
    
    /**
    * ユーザID
    */
    private String userId;

	public UserOutCndDetail(String cndSql, String cndSetCd, String userId) {
		super();
		this.cndSql = new ExterOutCdnSql(cndSql);
		this.cndSetCd = new ConditionSettingCd(cndSetCd);
		this.userId = userId;
	}
    
    
}
