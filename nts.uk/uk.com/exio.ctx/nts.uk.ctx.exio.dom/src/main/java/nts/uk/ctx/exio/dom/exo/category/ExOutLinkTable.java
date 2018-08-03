package nts.uk.ctx.exio.dom.exo.category;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

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
	private Optional<Form> form1;

	/**
	 * FROM句2
	 */
	private Optional<Form> form2;

	/**
	 * 条件
	 */
	private Optional<Conditions> conditions;

	/**
	 * 出力条件項目名1
	 */
	private Optional<PhysicalProjectName> outCondItemName1;

	/**
	 * 出力条件項目名2
	 */
	private Optional<PhysicalProjectName> outCondItemName2;

	/**
	 * 出力条件項目名3
	 */
	private Optional<PhysicalProjectName> outCondItemName3;

	/**
	 * 出力条件項目名4
	 */
	private Optional<PhysicalProjectName> outCondItemName4;

	/**
	 * 出力条件項目名5
	 */
	private Optional<PhysicalProjectName> outCondItemName5;

	/**
	 * 出力条件項目名6
	 */
	private Optional<PhysicalProjectName> outCondItemName6;

	/**
	 * 出力条件項目名7
	 */
	private Optional<PhysicalProjectName> outCondItemName7;

	/**
	 * 出力条件項目名8
	 */
	private Optional<PhysicalProjectName> outCondItemName8;

	/**
	 * 出力条件項目名9
	 */
	private Optional<PhysicalProjectName> outCondItemName9;

	/**
	 * 出力条件項目名10
	 */
	private Optional<PhysicalProjectName> outCondItemName10;

	/**
	 * 出力条件関連付1
	 */
	private Optional<Association> outCondAssociation1;

	/**
	 * 出力条件関連付2
	 */
	private Optional<Association> outCondAssociation2;

	/**
	 * 出力条件関連付3
	 */
	private Optional<Association> outCondAssociation3;

	/**
	 * 出力条件関連付4
	 */
	private Optional<Association> outCondAssociation4;

	/**
	 * 出力条件関連付5
	 */
	private Optional<Association> outCondAssociation5;

	/**
	 * 出力条件関連付6
	 */
	private Optional<Association> outCondAssociation6;

	/**
	 * 出力条件関連付7
	 */
	private Optional<Association> outCondAssociation7;

	/**
	 * 出力条件関連付8
	 */
	private Optional<Association> outCondAssociation8;

	/**
	 * 出力条件関連付9
	 */
	private Optional<Association> outCondAssociation9;

	/**
	 * 出力条件関連付10
	 */
	private Optional<Association> outCondAssociation10;

	public ExOutLinkTable(int categoryId, String mainTable, String form1, String form2, String conditions,
			String outCondItemName1, String outCondItemName2, String outCondItemName3, String outCondItemName4,
			String outCondItemName5, String outCondItemName6, String outCondItemName7, String outCondItemName8,
			String outCondItemName9, String outCondItemName10, Integer outCondAssociation1, Integer outCondAssociation2,
			Integer outCondAssociation3, Integer outCondAssociation4, Integer outCondAssociation5, Integer outCondAssociation6,
			Integer outCondAssociation7, Integer outCondAssociation8, Integer outCondAssociation9, Integer outCondAssociation10) {
		this.categoryId = new CategoryCd(categoryId);
		this.mainTable = new MainTable(mainTable);
		this.form1 = StringUtils.isEmpty(form1) ? Optional.empty() :Optional.of(new Form(form1));
		this.form2 = StringUtils.isEmpty(form2) ? Optional.empty() :Optional.of(new Form(form2));
		this.conditions = StringUtils.isEmpty(conditions) ? Optional.empty() :Optional.of(new Conditions(conditions));
		this.outCondItemName1 = StringUtils.isEmpty(outCondItemName1) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName1));
		this.outCondItemName2 = StringUtils.isEmpty(outCondItemName2) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName2));
		this.outCondItemName3 = StringUtils.isEmpty(outCondItemName3) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName3));
		this.outCondItemName4 = StringUtils.isEmpty(outCondItemName4) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName4));
		this.outCondItemName5 = StringUtils.isEmpty(outCondItemName5) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName5));
		this.outCondItemName6 = StringUtils.isEmpty(outCondItemName6) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName6));
		this.outCondItemName7 = StringUtils.isEmpty(outCondItemName7) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName7));
		this.outCondItemName8 = StringUtils.isEmpty(outCondItemName8) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName8));
		this.outCondItemName9 = StringUtils.isEmpty(outCondItemName9) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName9));
		this.outCondItemName10 = StringUtils.isEmpty(outCondItemName10) ? Optional.empty() : Optional.of(new PhysicalProjectName(outCondItemName10));
		this.outCondAssociation1 = (outCondAssociation1 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation1, Association.class));
		this.outCondAssociation2 = (outCondAssociation2 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation2, Association.class));
		this.outCondAssociation3 = (outCondAssociation3 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation3, Association.class));
		this.outCondAssociation4 = (outCondAssociation4 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation4, Association.class));
		this.outCondAssociation5 = (outCondAssociation5 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation5, Association.class));
		this.outCondAssociation6 = (outCondAssociation6 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation6, Association.class));
		this.outCondAssociation7 = (outCondAssociation7 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation7, Association.class));
		this.outCondAssociation8 = (outCondAssociation8 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation8, Association.class));
		this.outCondAssociation9 = (outCondAssociation9 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation9, Association.class));
		this.outCondAssociation10 = (outCondAssociation10 == null) ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(outCondAssociation10, Association.class));
	}

}
