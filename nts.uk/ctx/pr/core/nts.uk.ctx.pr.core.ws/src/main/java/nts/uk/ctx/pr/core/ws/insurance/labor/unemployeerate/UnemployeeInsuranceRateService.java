package nts.uk.ctx.pr.core.ws.insurance.labor.unemployeerate;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.HistoryUnemployeeInsuranceDto;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemDto;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSettingDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.CareerGroup;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItemSetting;
import nts.uk.ctx.pr.core.ws.insurance.labor.HistoryInsurance;

@Path("pr/insurance/labor/unemployeerate")
@Produces("application/json")
public class UnemployeeInsuranceRateService extends WebService {
	

	@POST
	@Path("detailHistory/{historyId}")
	public UnemployeeInsuranceRateDto detailHistory(@PathParam("historyId") String historyId) {
		UnemployeeInsuranceRateDto unemployeeInsuranceRate = new UnemployeeInsuranceRateDto();
		unemployeeInsuranceRate.setCompanyCode("companyCode001");
		unemployeeInsuranceRate.setHistoryId(historyId);
		List<UnemployeeInsuranceRateItemDto> rateItems = new ArrayList<UnemployeeInsuranceRateItemDto>();
		UnemployeeInsuranceRateItemDto umInsuranceRateItemAgroforestry = new UnemployeeInsuranceRateItemDto();
		umInsuranceRateItemAgroforestry.setCareerGroup(0);// CareerGroup.Agroforestry
		UnemployeeInsuranceRateItemSettingDto personalSettingAgroforestry = new UnemployeeInsuranceRateItemSettingDto();
		personalSettingAgroforestry.setRate(55.5);
		personalSettingAgroforestry.setRoundAtr(0);// RoundingMethod.RoundUp
		umInsuranceRateItemAgroforestry.setPersonalSetting(personalSettingAgroforestry);
		UnemployeeInsuranceRateItemSettingDto companySettingAgroforestry = new UnemployeeInsuranceRateItemSettingDto();
		companySettingAgroforestry.setRate(55.59);
		companySettingAgroforestry.setRoundAtr(0);// RoundingMethod.RoundUp
		umInsuranceRateItemAgroforestry.setCompanySetting(companySettingAgroforestry);
		rateItems.add(umInsuranceRateItemAgroforestry);
		UnemployeeInsuranceRateItemDto umInsuranceRateItemContruction = new UnemployeeInsuranceRateItemDto();
		umInsuranceRateItemContruction.setCareerGroup(1);// CareerGroup.Contruction
		UnemployeeInsuranceRateItemSettingDto personalSettingContruction = new UnemployeeInsuranceRateItemSettingDto();
		personalSettingContruction.setRate(55.5);
		personalSettingContruction.setRoundAtr(0);// RoundingMethod.RoundUp
		umInsuranceRateItemContruction.setPersonalSetting(personalSettingContruction);
		UnemployeeInsuranceRateItemSettingDto companySettingContruction = new UnemployeeInsuranceRateItemSettingDto();
		companySettingContruction.setRate(55.59);
		companySettingContruction.setRoundAtr(0); // RoundingMethod.RoundUp
		umInsuranceRateItemContruction.setCompanySetting(companySettingContruction);
		rateItems.add(umInsuranceRateItemContruction);
		UnemployeeInsuranceRateItemDto umInsuranceRateItemOther = new UnemployeeInsuranceRateItemDto();
		umInsuranceRateItemOther.setCareerGroup(2);// CareerGroup.Other
		UnemployeeInsuranceRateItemSettingDto personalSettingOther = new UnemployeeInsuranceRateItemSettingDto();
		personalSettingOther.setRate(55.5);
		personalSettingOther.setRoundAtr(0); // RoundingMethod.RoundUp
		umInsuranceRateItemOther.setPersonalSetting(personalSettingOther);
		UnemployeeInsuranceRateItemSettingDto companySettingOther = new UnemployeeInsuranceRateItemSettingDto();
		companySettingOther.setRate(56.0);
		companySettingOther.setRoundAtr(0);//RoundingMethod.RoundUp
		umInsuranceRateItemOther.setCompanySetting(companySettingOther);
		rateItems.add(umInsuranceRateItemOther);
		unemployeeInsuranceRate.setRateItems(rateItems);
		return unemployeeInsuranceRate;
	}

}
