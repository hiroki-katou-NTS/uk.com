package nts.uk.ctx.exio.dom.exo.outitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.dataformat.ItemType;

/**
* 出力項目(定型)
*/
@AllArgsConstructor
@Getter
public class StdOutItem extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 出力項目コード
    */
    private String outItemCd;
    
    /**
    * 条件設定コード
    */
    private String condSetCd;
    
    /**
    * 出力項目名
    */
    private String outItemName;
    
    /**
    * 項目型
    */
    private ItemType itemType; 
    
    public static StdOutItem createFromJavaType(String cid, String outItemCd, String condSetCd, String outItemName,
    		int itemType) {
		StdOutItem stdOutItem = new StdOutItem(cid, outItemCd, condSetCd, outItemName,
				EnumAdaptor.valueOf(itemType, ItemType.class));
		return stdOutItem;
	}
}
