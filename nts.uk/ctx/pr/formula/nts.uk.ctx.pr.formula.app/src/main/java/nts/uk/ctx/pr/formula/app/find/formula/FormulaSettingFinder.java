package nts.uk.ctx.pr.formula.app.find.formula;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.app.find.formulaeasycondition.EasyFormulaConditionDto;
import nts.uk.ctx.pr.formula.app.find.formulaeasycondition.FormulaEasyConditionDto;
import nts.uk.ctx.pr.formula.app.find.formulaeasydetail.FormulaEasyDetailDto;
import nts.uk.ctx.pr.formula.app.find.formulamanual.FormulaManualDto;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyConditionRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyDetailRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaManualRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class FormulaSettingFinder {

	@Inject
	FormulaEasyConditionRepository formulaEasyConditionRepository;

	@Inject
	FormulaEasyDetailRepository formulaEasyDetailRepository;

	@Inject
	FormulaManualRepository formulaManualRepository;

	public FormulaSettingDto init(String formulaCode, String historyId, int difficultyAtr) {

		LoginUserContext login = AppContexts.user();
		FormulaSettingDto formulaSettingDto = new FormulaSettingDto();

		if (difficultyAtr == 0) {
			Optional<FormulaEasyConditionDto> formulaEasyConditions = formulaEasyConditionRepository
					.find(login.companyCode(), new FormulaCode(formulaCode), historyId).map(f -> {
						FormulaEasyConditionDto formulaEasy = new FormulaEasyConditionDto();
						formulaEasy.setFixFormulaAtr(f.getFixFormulaAtr().value);
						List<EasyFormulaConditionDto> easyFormulaConditionDtos = new ArrayList<>();
						EasyFormulaConditionDto conditionDto = new EasyFormulaConditionDto();
						conditionDto.setEasyFormulaCode(f.getEasyFormulaCode().v());
						conditionDto.setFixMoney(f.getFixMoney().v());
						conditionDto.setReferenceMasterCode(f.getReferenceMasterCode().v());
						easyFormulaConditionDtos.add(conditionDto);
						formulaEasy.setEasyFormulaConditionDto(easyFormulaConditionDtos);
						return formulaEasy;
					});
			
			formulaSettingDto.setFixFormulaAtr(formulaEasyConditions.get().getFixFormulaAtr());
			/* 14-3 */

			Optional<FormulaEasyDetailDto> formulaEasyDetailDtos = formulaEasyDetailRepository
					.findWithOutPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId)
					.map(f -> FormulaEasyDetailDto.fromDomain(f));

			formulaSettingDto.setEasyFormulaName(formulaEasyDetailDtos.get().getEasyFormulaName());
			formulaSettingDto.setBaseFixedAmount(formulaEasyDetailDtos.get().getBaseFixedAmount());
			formulaSettingDto.setBaseAmountDevision(formulaEasyDetailDtos.get().getBaseAmountDevision());
			formulaSettingDto.setBaseFixedAmount(formulaEasyDetailDtos.get().getBaseFixedAmount());
			formulaSettingDto.setBaseValueDevision(formulaEasyDetailDtos.get().getBaseValueDevision());
			formulaSettingDto.setPremiumRate(formulaEasyDetailDtos.get().getPremiumRate());
			formulaSettingDto.setRoundProcessingDevision(formulaEasyDetailDtos.get().getRoundProcessingDevision());
			formulaSettingDto.setCoefficientDivision(formulaEasyDetailDtos.get().getCoefficientDivision());
			formulaSettingDto.setCoefficientFixedValue(formulaEasyDetailDtos.get().getCoefficientFixedValue());
			formulaSettingDto.setAdjustmentDevision(formulaEasyDetailDtos.get().getAdjustmentDevision());
			formulaSettingDto.setTotalRounding(formulaEasyDetailDtos.get().getTotalRounding());
			formulaSettingDto.setMaxLimitValue(formulaEasyDetailDtos.get().getMaxLimitValue());
			formulaSettingDto.setMinLimitValue(formulaEasyDetailDtos.get().getMinLimitValue());

		} else if (difficultyAtr == 1) {
			Optional<FormulaManualDto> formulaManualDto = formulaManualRepository
					.findByPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId)
					.map(f -> FormulaManualDto.fromDomain(f));

			formulaSettingDto.setFormulaContent(formulaManualDto.get().getFormulaContent());
			formulaSettingDto.setReferenceMonthAtr(formulaManualDto.get().getReferenceMonthAtr());
			formulaSettingDto.setRoundAtr(formulaManualDto.get().getRoundAtr());
			formulaSettingDto.setRoundDigit(formulaManualDto.get().getRoundDigit());
		}

		return formulaSettingDto;
	}

}
