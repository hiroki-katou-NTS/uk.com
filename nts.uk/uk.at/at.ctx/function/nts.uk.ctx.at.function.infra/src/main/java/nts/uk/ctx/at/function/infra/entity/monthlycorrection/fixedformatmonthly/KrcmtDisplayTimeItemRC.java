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
@Table(name = "KFNMT_MON_FORM_BUS_ITEM")
public class KrcmtDisplayTimeItemRC extends ContractUkJpaEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtDisplayTimeItemRCPK krcmtDisplayTimeItemRCPK;
	
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;

	@Column(name = "COLUMN_WIDTH")
	public Integer columnWidthTable;
	
	@ManyToOne
	@JoinColumns( {
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "BUSINESS_TYPE_CODE", referencedColumnName = "BUSINESS_TYPE_CODE", insertable = false, updatable = false),
        @JoinColumn(name = "SHEET_NO", referencedColumnName = "SHEET_NO", insertable = false, updatable = false)
        
    })
	public KrcmtMonthlyActualResultRC monthlyacresult;
	
	@Override
	protected Object getKey() {
		return krcmtDisplayTimeItemRCPK;
	}

	public KrcmtDisplayTimeItemRC(KrcmtDisplayTimeItemRCPK krcmtDisplayTimeItemRCPK, int displayOrder, Integer columnWidthTable) {
		super();
		this.krcmtDisplayTimeItemRCPK = krcmtDisplayTimeItemRCPK;
		this.displayOrder = displayOrder;
		this.columnWidthTable = columnWidthTable;
	}
	
	public static KrcmtDisplayTimeItemRC toEntity(String conpanyID,String businessTypeCode,int sheetNo,DisplayTimeItem domain) {
		return new KrcmtDisplayTimeItemRC(
				new KrcmtDisplayTimeItemRCPK(
						conpanyID,
						businessTypeCode,
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
				this.krcmtDisplayTimeItemRCPK.itemDisplay,
				this.columnWidthTable
				);
	}
}
