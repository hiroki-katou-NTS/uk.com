package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNRT_SET_OUT_ITEMS_WO_SC")
public class KfnrtSetOutItemsWoSc extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	* ID
	*/
	@EmbeddedId
	public KfnrtSetOutItemsWoScPk setOutItemsWoScPk;

	/**
	* 名称
	*/
	@Basic(optional = false)
	@Column(name = "NAME")
	public String name;

	/**
	* 36協定時間を超過した月数を出力する
	*/
	@Basic(optional = false)
	@Column(name = "OUT_NUM_EXCEED_TIME_36_AGR")
	public int outNumExceedTime36Agr;
	
	/*
	 * 年間勤務表印刷形式
	 */
	@Basic(optional = false)
	@Column(name = "PRINT_FORM")
	public int printForm;	
	
	/**
	* 複数月表示
	*/
	@Basic(optional = false)
	@Column(name = "MULTI_MON_DISP")
	public int multiMonthDisplay;
	
	/**
	* 合計表示の月数
	*/
	@Basic(optional = true)
	@Column(name = "SUM_DISP_MONTHS")
	public Integer monthsInTotalDisplay;
	
	/**
	* 合計平均表示
	*/
	@Basic(optional = false)
	@Column(name = "SUM_AVG_DISP")
	public int totalAverageDisplay;

	@Override
	protected Object getKey() {
		return setOutItemsWoScPk;
	}

	@OneToMany(mappedBy = "setOutItemsWoSc", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNRT_ITEM_OUT_TBL_BOOK")
	public List<KfnrtItemOutTblBook> listItemOutTblBook;

	public SetOutItemsWoSc toDomain() {
		return SetOutItemsWoSc.createFromJavaType(this.setOutItemsWoScPk.cid, this.setOutItemsWoScPk.cd, this.name,
				this.outNumExceedTime36Agr == 1 ? true : false, this.printForm,
				this.listItemOutTblBook.stream().map(m -> m.toDomain()).collect(Collectors.toList()),
				this.multiMonthDisplay == 1 ? true : false, this.monthsInTotalDisplay, this.totalAverageDisplay);
	}

	public static KfnrtSetOutItemsWoSc toEntity(SetOutItemsWoSc domain) {
		return new KfnrtSetOutItemsWoSc(new KfnrtSetOutItemsWoScPk(domain.getCid(), domain.getCd().v()),
										domain.getName().v(), domain.isOutNumExceedTime36Agr()? 1: 0,
										domain.getPrintForm().value, domain.isMultiMonthDisplay()? 1:0,
										domain.getMonthsInTotalDisplay().isPresent()?domain.getMonthsInTotalDisplay().get().value:null,
										domain.getTotalAverageDisplay().value,
			domain.getListItemOutTblBook().stream().map(m -> KfnrtItemOutTblBook.toEntity(m)).collect(Collectors.toList()));
	}
}
