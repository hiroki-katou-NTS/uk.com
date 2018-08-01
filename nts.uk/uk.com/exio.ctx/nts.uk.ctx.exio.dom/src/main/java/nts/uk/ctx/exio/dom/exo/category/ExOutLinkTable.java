package nts.uk.ctx.exio.dom.exo.category;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 外部出力リンクテーブル
 */

@Getter
public class ExOutLinkTable extends AggregateRoot {

	/**
	 * カテゴリID
	 */
	private CategoryCd categoryId;

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
	 * 出力条件項目名4
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

	public ExOutLinkTable(int categoryId, String mainTable, String form1, String form2, String conditions,
			String outCondItemName1, String outCondItemName2, String outCondItemName3, String outCondItemName4,
			String outCondItemName5, String outCondItemName6, String outCondItemName7, String outCondItemName8,
			String outCondItemName9, String outCondItemName10, int outCondAssociation1, int outCondAssociation2,
			int outCondAssociation3, int outCondAssociation4, int outCondAssociation5, int outCondAssociation6,
			int outCondAssociation7, int outCondAssociation8, int outCondAssociation9, int outCondAssociation10) {
		this.categoryId = new CategoryCd(categoryId);
		this.mainTable = new MainTable(mainTable);
		this.form1 = new Form1(form1);
		this.form2 = new Form2(form2);
		this.conditions = new Conditions(conditions);
		this.outCondItemName1 = new PhysicalProjectName(outCondItemName1);
		this.outCondItemName2 = new PhysicalProjectName(outCondItemName2);
		this.outCondItemName3 = new PhysicalProjectName(outCondItemName3);
		this.outCondItemName4 = new PhysicalProjectName(outCondItemName4);
		this.outCondItemName5 = new PhysicalProjectName(outCondItemName5);
		this.outCondItemName6 = new PhysicalProjectName(outCondItemName6);
		this.outCondItemName7 = new PhysicalProjectName(outCondItemName7);
		this.outCondItemName8 = new PhysicalProjectName(outCondItemName8);
		this.outCondItemName9 = new PhysicalProjectName(outCondItemName9);
		this.outCondItemName10 = new PhysicalProjectName(outCondItemName10);
		this.outCondAssociation1 = EnumAdaptor.valueOf(outCondAssociation1, Association.class);
		this.outCondAssociation2 = EnumAdaptor.valueOf(outCondAssociation2, Association.class);
		this.outCondAssociation3 = EnumAdaptor.valueOf(outCondAssociation3, Association.class);
		this.outCondAssociation4 = EnumAdaptor.valueOf(outCondAssociation4, Association.class);
		this.outCondAssociation5 = EnumAdaptor.valueOf(outCondAssociation5, Association.class);
		this.outCondAssociation6 = EnumAdaptor.valueOf(outCondAssociation6, Association.class);
		this.outCondAssociation7 = EnumAdaptor.valueOf(outCondAssociation7, Association.class);
		this.outCondAssociation8 = EnumAdaptor.valueOf(outCondAssociation8, Association.class);
		this.outCondAssociation9 = EnumAdaptor.valueOf(outCondAssociation9, Association.class);
		this.outCondAssociation10 = EnumAdaptor.valueOf(outCondAssociation10, Association.class);
	}

}
