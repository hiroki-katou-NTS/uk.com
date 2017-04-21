package nts.uk.ctx.pr.core.dom.payment.banktransfer.adapter;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicPersonBankAccountDto {
	@Getter
    private String companyCode;
	
	@Getter
    private String personID;
	
	@Getter
    private String histId;
	
	@Getter
    private int startYearMonth;
	
	@Getter
    private int endYearMonth;
	
	@Getter
    private BasicPersonUseSettingDto useSet1;
    
	@Getter
    private BasicPersonUseSettingDto useSet2;
    
	@Getter
    private BasicPersonUseSettingDto useSet3;
    
	@Getter
    private BasicPersonUseSettingDto useSet4;
    
	@Getter
    private BasicPersonUseSettingDto useSet5;

	public BasicPersonBankAccountDto(String companyCode, String personID, String histId, int startYearMonth, int endYearMonth,
			BasicPersonUseSettingDto useSet1, BasicPersonUseSettingDto useSet2, BasicPersonUseSettingDto useSet3, BasicPersonUseSettingDto useSet4,
			BasicPersonUseSettingDto useSet5) {
		super();
		this.companyCode = companyCode;
		this.personID = personID;
		this.histId = histId;
		this.startYearMonth = startYearMonth;
		this.endYearMonth = endYearMonth;
		this.useSet1 = useSet1;
		this.useSet2 = useSet2;
		this.useSet3 = useSet3;
		this.useSet4 = useSet4;
		this.useSet5 = useSet5;
	}
}
