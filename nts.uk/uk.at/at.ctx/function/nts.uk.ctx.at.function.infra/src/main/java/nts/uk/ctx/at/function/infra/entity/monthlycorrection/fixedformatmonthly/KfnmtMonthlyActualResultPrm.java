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
@Table(name = "KFNMT_AUT_MON_FORM_SHEET")
public class KfnmtMonthlyActualResultPrm  extends ContractUkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KfnmtMonthlyActualResultPrmPK kfnmtMonthlyActualResultPrmPK;

	@Column(name = "SHEET_NAME")
	public String sheetName;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "MON_FORMAT_CODE", referencedColumnName = "MON_FORMAT_CODE", insertable = false, updatable = false)
    })
	public KrcmtMonPfmCorrectionFormat monthlyactualresultpfm;
	
	@OneToMany(mappedBy="monthlyacresultpfm", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_AUT_MON_FORM_ITEM")
	public List<KfnmtDisplayTimeItemPfm> listKrcmtDisplayTimeItemPfm;
	
	
	@Override
	protected Object getKey() {
		return kfnmtMonthlyActualResultPrmPK;
	}


	public KfnmtMonthlyActualResultPrm(KfnmtMonthlyActualResultPrmPK kfnmtMonthlyActualResultPrmPK, String sheetName, List<KfnmtDisplayTimeItemPfm> listKrcmtDisplayTimeItemPfm) {
		super();
		this.kfnmtMonthlyActualResultPrmPK = kfnmtMonthlyActualResultPrmPK;
		this.sheetName = sheetName;
		this.listKrcmtDisplayTimeItemPfm = listKrcmtDisplayTimeItemPfm;
	}
	
	public static KfnmtMonthlyActualResultPrm toEntity(String companyID,String monthlyPfmFormatCode, SheetCorrectedMonthly domain) {
		return new KfnmtMonthlyActualResultPrm(
				new KfnmtMonthlyActualResultPrmPK(
						companyID,
						monthlyPfmFormatCode,
						domain.getSheetNo()
						),
				domain.getSheetName().v(),
				domain.getListDisplayTimeItem().stream().map(c->KfnmtDisplayTimeItemPfm.toEntity(companyID,monthlyPfmFormatCode, domain.getSheetNo(), c)).collect(Collectors.toList())
				);
	}
	
	public SheetCorrectedMonthly toDomain() {
			SheetCorrectedMonthly sheetCorrectedMonthly = new SheetCorrectedMonthly(
					this.kfnmtMonthlyActualResultPrmPK.sheetNo,
					new DailyPerformanceFormatName(this.sheetName),
					this.listKrcmtDisplayTimeItemPfm.stream().map(c->c.toDomain()).collect(Collectors.toList())
					);
		return sheetCorrectedMonthly;
	}
	
}
