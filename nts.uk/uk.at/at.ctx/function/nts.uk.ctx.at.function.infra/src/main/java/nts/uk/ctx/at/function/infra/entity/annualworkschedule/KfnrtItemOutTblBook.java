package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 帳表に出力する項目
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNRT_ITEM_OUT_TBL_BOOK")
public class KfnrtItemOutTblBook extends ContractUkJpaEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	* ID
	*/
	@EmbeddedId
	public KfnrtItemOutTblBookPk itemOutTblBookPk;

	/**
	* 並び順
	*/
	@Basic(optional = false)
	@Column(name = "SORT_BY")
	public int sortBy;

	/**
	* 見出し名称
	*/
	@Basic(optional = false)
	@Column(name = "HEADING_NAME")
	public String headingName;

	/**
	* 使用区分
	*/
	@Basic(optional = false)
	@Column(name = "USE_CLASS")
	public int useClass;

	/**
	* 値の出力形式
	*/
	@Basic(optional = false)
	@Column(name = "VAL_OUT_FORMAT")
	public int valOutFormat;

	@Override
	protected Object getKey()
	{
		return itemOutTblBookPk;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "SET_OUT_CD", referencedColumnName = "CD", insertable = false, updatable = false) })
	public KfnrtSetOutItemsWoSc setOutItemsWoSc;


	@OneToMany(mappedBy = "itemOutTblBook", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNRT_CALC_FORMULA_ITEM")
	public List<KfnrtCalcFormulaItem> listCalcFormulaItem;

	public KfnrtItemOutTblBook(KfnrtItemOutTblBookPk itemOutTblBookPk, int sortBy, String headingName, int useClass,
			int valOutFormat, List<KfnrtCalcFormulaItem> listCalcFormulaItem) {
		super();
		this.itemOutTblBookPk = itemOutTblBookPk;
		this.sortBy = sortBy;
		this.headingName = headingName;
		this.useClass = useClass;
		this.valOutFormat = valOutFormat;
		this.listCalcFormulaItem = listCalcFormulaItem;
	}

	public ItemOutTblBook toDomain() {
		return ItemOutTblBook.createFromJavaType(this.itemOutTblBookPk.cid, this.itemOutTblBookPk.setOutCd,
								  this.itemOutTblBookPk.cd, this.sortBy,
								  this.headingName, this.useClass == 1? true: false,
								  this.valOutFormat,
		this.listCalcFormulaItem.stream().map(m -> m.toDomain()).collect(Collectors.toList()));
	}

	public static KfnrtItemOutTblBook toEntity(ItemOutTblBook domain) {
		return new KfnrtItemOutTblBook(new KfnrtItemOutTblBookPk(domain.getCid(), domain.getSetOutCd(), domain.getCd().v()),
									   domain.getSortBy(), domain.getHeadingName().v(),
									   domain.isUseClassification()? 1: 0,
									   domain.getValOutFormat().value,
									   domain.getListOperationSetting().stream()
									   .map(m -> KfnrtCalcFormulaItem.toEntity(m)).collect(Collectors.toList()));
	}
}
