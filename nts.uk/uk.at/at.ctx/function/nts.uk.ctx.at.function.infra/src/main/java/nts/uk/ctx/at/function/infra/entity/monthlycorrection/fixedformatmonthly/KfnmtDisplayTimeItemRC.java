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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_DIS_TIME_ITEM_RC")
public class KfnmtDisplayTimeItemRC extends UkJpaEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtDisplayTimeItemRCPK kfnmtDisplayTimeItemRCPK;
	
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;

	@Column(name = "COLUMN_WIDTH_TABLE")
	public Integer columnWidthTable;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "MON_ACTUAL_RESULTS_ID", referencedColumnName = "MON_ACTUAL_RESULTS_ID", insertable = false, updatable = false),
        @JoinColumn(name = "SHEET_NO", referencedColumnName = "SHEET_NO", insertable = false, updatable = false)
    })
	public KfnmtMonthlyActualResultRC monthlyacresult;
	
	@Override
	protected Object getKey() {
		return kfnmtDisplayTimeItemRCPK;
	}

	public KfnmtDisplayTimeItemRC(KfnmtDisplayTimeItemRCPK kfnmtDisplayTimeItemRCPK, int displayOrder, Integer columnWidthTable) {
		super();
		this.kfnmtDisplayTimeItemRCPK = kfnmtDisplayTimeItemRCPK;
		this.displayOrder = displayOrder;
		this.columnWidthTable = columnWidthTable;
	}
	
	public static KfnmtDisplayTimeItemRC toEntity(String monthlyActualID,int sheetNo,DisplayTimeItem domain) {
		return new KfnmtDisplayTimeItemRC(
				new KfnmtDisplayTimeItemRCPK(
						monthlyActualID,
						sheetNo,
						domain.getItemDaily()
						),
				domain.getDisplayOrder(),
				domain.getColumnWidthTable()==null?null:domain.getColumnWidthTable().get()
				);
	}
	public DisplayTimeItem toDomain() {
		return new DisplayTimeItem(
				this.kfnmtDisplayTimeItemRCPK.itemDisplay,
				this.displayOrder,
				this.columnWidthTable
				);
	}
}
