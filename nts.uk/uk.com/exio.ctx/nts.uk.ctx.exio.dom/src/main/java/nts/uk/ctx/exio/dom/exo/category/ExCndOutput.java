package nts.uk.ctx.exio.dom.exo.category;



import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.category.Association;

/**
* 外部出力リンクテーブル
*/
@AllArgsConstructor
@Getter
public class ExCndOutput extends AggregateRoot
{
    
    /**
    * カテゴリID
    */
    private CategoryId categoryId;
    
    /**
    * 主テーブル
    */
    private MainTable mainTable;
    
    /**
    * FROM句1
    */
    private Form1 form1;
    
    /**
    * FROM句2
    */
    private Form2 form2;
    
    /**
    * 条件
    */
    private Conditions conditions;
    
    /**
    * 出力条件項目名1
    */
    private PhysicalProjectName outCondItemName1;
    
    /**
    * 出力条件項目名2
    */
    private PhysicalProjectName outCondItemName2;
    
    /**
    * 出力条件項目名3
    */
    private PhysicalProjectName outCondItemName3;
    
    /**
    *出力条件項目名4 
    */
    private PhysicalProjectName outCondItemName4;
    
    /**
    * 出力条件項目名5
    */
    private PhysicalProjectName outCondItemName5;
    
    /**
    * 出力条件項目名6
    */
    private PhysicalProjectName outCondItemName6;
    
    /**
    * 出力条件項目名7
    */
    private PhysicalProjectName outCondItemName7;
    
    /**
    * 出力条件項目名8
    */
    private PhysicalProjectName outCondItemName8;
    
    /**
    * 出力条件項目名9
    */
    private PhysicalProjectName outCondItemName9;
    
    /**
    * 出力条件項目名10
    */
    private PhysicalProjectName outCondItemName10;
    
    /**
    * 出力条件関連付1
    */
    private Association outCondAssociation1;
    
    /**
    * 出力条件関連付2
    */
    private Association outCondAssociation2;
    
    /**
    * 出力条件関連付3
    */
    private Association outCondAssociation3;
    
    /**
    * 出力条件関連付4
    */
    private Association outCondAssociation4;
    
    /**
    * 出力条件関連付5
    */
    private Association outCondAssociation5;
    
    /**
    * 出力条件関連付6
    */
    private Association outCondAssociation6;
    
    /**
    * 出力条件関連付7
    */
    private Association outCondAssociation7;
    
    /**
    * 出力条件関連付8
    */
    private Association outCondAssociation8;
    
    /**
    * 出力条件関連付9
    */
    private Association outCondAssociation9;
    
    /**
    * 出力条件関連付10
    */
    private Association outCondAssociation10;
    
    
}




