package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.DisplayTimeItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_AUT_MON_FORM_ITEM")
public class KfnmtDisplayTimeItemPfm  extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtDisplayTimeItemPfmPK kfnmtDisplayTimeItemPfmPK;

	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;

	@Column(name = "COLUMN_WIDTH")
	public Integer columnWidthTable;

	
	@ManyToOne
	@JoinColumns( {
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "MON_FORMAT_CODE", referencedColumnName = "MON_FORMAT_CODE", insertable = false, updatable = false),
        @JoinColumn(name = "SHEET_NO", referencedColumnName = "SHEET_NO", insertable = false, updatable = false),
        
    })
	public KfnmtMonthlyActualResultPrm monthlyacresultpfm;
	@Override
	protected Object getKey() {
		return kfnmtDisplayTimeItemPfmPK;
	}
	public KfnmtDisplayTimeItemPfm(KfnmtDisplayTimeItemPfmPK kfnmtDisplayTimeItemPfmPK, int displayOrder, Integer columnWidthTable) {
		super();
		this.kfnmtDisplayTimeItemPfmPK = kfnmtDisplayTimeItemPfmPK;
		this.displayOrder = displayOrder;
		this.columnWidthTable = columnWidthTable;
	}
	
	
	public static KfnmtDisplayTimeItemPfm toEntity(String conpanyID,String monthlyPfmFormatCode,int sheetNo,DisplayTimeItem domain) {
		return new KfnmtDisplayTimeItemPfm(
				new KfnmtDisplayTimeItemPfmPK(
						conpanyID,
						monthlyPfmFormatCode,
						sheetNo,
						domain.getItemDaily()
						),
				domain.getDisplayOrder(),
				!domain.getColumnWidthTable().isPresent()?null:domain.getColumnWidthTable().get()
				);
	}
	public DisplayTimeItem toDomain() {
		return new DisplayTimeItem(
				this.displayOrder,
				this.kfnmtDisplayTimeItemPfmPK.itemDisplay,
				this.columnWidthTable
				);
	}
}
