package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_BUS_MON_FORM_SHEET")
public class KrcmtMonthlyActualResultRC extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtMonthlyActualResultRCPK krcmtMonthlyActualResultRCPK;
	
	
	@Column(name = "SHEET_NAME")
	public String sheetName;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "BUSINESS_TYPE_CODE", referencedColumnName = "BUSINESS_TYPE_CODE", insertable = false, updatable = false)
    })
	public KrcmtMonthlyRecordWorkType monthlyactualresult;

	@OneToMany(mappedBy="monthlyacresult", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KRCMT_BUS_MON_FORM_ITEM")
	public List<KrcmtDisplayTimeItemRC> listKrcmtDisplayTimeItemRC;
	
	
	@Override
	protected Object getKey() {
		return krcmtMonthlyActualResultRCPK;
	}


	public KrcmtMonthlyActualResultRC(KrcmtMonthlyActualResultRCPK krcmtMonthlyActualResultRCPK, String sheetName, List<KrcmtDisplayTimeItemRC> listKrcmtDisplayTimeItemRC) {
		super();
		this.krcmtMonthlyActualResultRCPK = krcmtMonthlyActualResultRCPK;
		this.sheetName = sheetName;
		this.listKrcmtDisplayTimeItemRC = listKrcmtDisplayTimeItemRC;
	}
	
	public static KrcmtMonthlyActualResultRC toEntity(String companyID,String businessTypeCode, SheetCorrectedMonthly domain) {
		return new KrcmtMonthlyActualResultRC(
				new KrcmtMonthlyActualResultRCPK(
						companyID,
						businessTypeCode,
						domain.getSheetNo()
						),
				domain.getSheetName().v(),
				domain.getListDisplayTimeItem().stream().map(c->KrcmtDisplayTimeItemRC.toEntity(companyID,businessTypeCode, domain.getSheetNo(), c)).collect(Collectors.toList())
				);
	}
	
	public SheetCorrectedMonthly toDomain() {
			SheetCorrectedMonthly sheetCorrectedMonthly = new SheetCorrectedMonthly(
					this.krcmtMonthlyActualResultRCPK.sheetNo,
					new DailyPerformanceFormatName(this.sheetName),
					this.listKrcmtDisplayTimeItemRC.stream().map(c->c.toDomain()).collect(Collectors.toList())
					);
		return sheetCorrectedMonthly;
	}
}
