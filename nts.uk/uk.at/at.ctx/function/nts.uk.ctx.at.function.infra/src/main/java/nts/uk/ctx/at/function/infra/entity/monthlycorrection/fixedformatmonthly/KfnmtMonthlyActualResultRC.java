package nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_MON_ACT_RESULTS_RC")
public class KfnmtMonthlyActualResultRC extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtMonthlyActualResultRCPK kfnmtMonthlyActualResultRCPK;
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "BUSINESS_TYPE_CODE")
	public String businessTypeCode;
	
	@Column(name = "SHEET_NAME")
	public String sheetName;

	@OneToMany(mappedBy="monthlyacresult", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_DIS_TIME_ITEM_RC")
	public List<KfnmtDisplayTimeItemRC> listKfnmtDisplayTimeItemRC;
	
	
	@Override
	protected Object getKey() {
		return kfnmtMonthlyActualResultRCPK;
	}


	public KfnmtMonthlyActualResultRC(KfnmtMonthlyActualResultRCPK kfnmtMonthlyActualResultRCPK, String companyID, String businessTypeCode, String sheetName, List<KfnmtDisplayTimeItemRC> listKfnmtDisplayTimeItemRC) {
		super();
		this.kfnmtMonthlyActualResultRCPK = kfnmtMonthlyActualResultRCPK;
		this.companyID = companyID;
		this.businessTypeCode = businessTypeCode;
		this.sheetName = sheetName;
		this.listKfnmtDisplayTimeItemRC = listKfnmtDisplayTimeItemRC;
	}
	
	public static KfnmtMonthlyActualResultRC toEntity(String monthlyActualID,String companyID,String businessTypeCode, SheetCorrectedMonthly domain) {
		return new KfnmtMonthlyActualResultRC(
				new KfnmtMonthlyActualResultRCPK(
						monthlyActualID,domain.getSheetNo()
						),
				companyID,
				businessTypeCode,
				domain.getSheetName().v(),
				domain.getListDisplayTimeItem().stream().map(c->KfnmtDisplayTimeItemRC.toEntity(monthlyActualID, domain.getSheetNo(), c)).collect(Collectors.toList())
				);
	}
	
	public SheetCorrectedMonthly toDomain() {
			SheetCorrectedMonthly sheetCorrectedMonthly = new SheetCorrectedMonthly(
					this.kfnmtMonthlyActualResultRCPK.monthlyActualID,
					this.kfnmtMonthlyActualResultRCPK.sheetNo,
					new DailyPerformanceFormatName(this.sheetName),
					this.listKfnmtDisplayTimeItemRC.stream().map(c->c.toDomain()).collect(Collectors.toList())
					);
		return sheetCorrectedMonthly;
	}
}
