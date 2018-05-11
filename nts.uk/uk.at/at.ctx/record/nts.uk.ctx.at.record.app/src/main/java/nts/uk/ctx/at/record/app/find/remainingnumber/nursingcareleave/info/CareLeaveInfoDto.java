package nts.uk.ctx.at.record.app.find.remainingnumber.nursingcareleave.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CareLeaveInfoDto extends PeregDomainDto{
	
	//社員ID
	@PeregEmployeeId
	private String sId;
	
	//子の看護休暇管理
	@PeregItem("IS00375")
	private int childCareUseArt;
	
	@PeregItem("IS00376")
	//子の看護上限設定
	private Integer childCareUpLimSet;	
	
	@PeregItem("IS00377")
	//本年度の子の看護上限日数
	private Double childCareThisFiscal;
	
	@PeregItem("IS00378")
	//次年度の子の看護上限日数
	private Double childCareNextFiscal;
	
	//子の看護休暇管理
	@PeregItem("IS00379")
	private Double childCareUsedDays;
	
	//介護休暇管理
	@PeregItem("IS00380")
	private int careUseArt;
	
	@PeregItem("IS00381")
	//介護上限設定
	private Integer careUpLimSet;	
	
	@PeregItem("IS00382")
	//本年度の介護上限日数
	private Double careThisFiscal;
	
	@PeregItem("IS00383")
	//次年度の介護上限日数
	private Double careNextFiscal;
	
	//介護使用日数
	@PeregItem("IS00384")
	private Double careUsedDays;
	
	public static CareLeaveInfoDto createDomain(String sId, int childCareUseArt, Integer childCareUpLimSet, 
			Double childCareThisFiscal, Double childCareNextFiscal, Double childCareUsedDays, 
			int careUseArt, Integer careUpLimSet, Double careThisFiscal, Double careNextFiscal, Double careUsedDays){
		CareLeaveInfoDto domain = new CareLeaveInfoDto(sId, childCareUseArt, childCareUpLimSet, 
				childCareThisFiscal, childCareNextFiscal, childCareUsedDays, 
				careUseArt, careUpLimSet, careThisFiscal, careNextFiscal, careUsedDays);
		domain.setRecordId(sId);
		return domain;
	}
	
	
}
