package nts.uk.ctx.pr.formula.app.find.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyConditionRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyDetailRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyStandardItemRepository;
import nts.uk.ctx.pr.formula.dom.repository.FormulaManualRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author Nam-PT activity 8 C screen
 *
 */
@Stateless
public class FormulaSettingFinder {

	@Inject
	FormulaEasyStandardItemRepository formulaEasyStandardItemRepository;

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
			// かんたん計算_条件.SEL-1
			List<FormulaEasyConditionDto> formulaEasyConditions = formulaEasyConditionRepository
					.find(login.companyCode(), new FormulaCode(formulaCode), historyId).stream().map(f -> {
						FormulaEasyConditionDto formulaEasyConditionDto = new FormulaEasyConditionDto();
						formulaEasyConditionDto.setEasyFormulaCode(f.getEasyFormulaCode().v());
						formulaEasyConditionDto.setFixFormulaAtr(f.getFixFormulaAtr().value);
						formulaEasyConditionDto.setFixMoney(f.getFixMoney().v());
						formulaEasyConditionDto.setReferenceMasterCode(f.getReferenceMasterCode().v());
						return formulaEasyConditionDto;
					}).collect(Collectors.toList());

			List<EasyFormulaFindDto> easyFormula = new ArrayList<>();
			formulaEasyConditions.forEach(f -> {
				EasyFormulaFindDto easyFormulaFindDto = new EasyFormulaFindDto();
				easyFormulaFindDto.setEasyFormulaCode(f.getEasyFormulaCode());
				easyFormulaFindDto.setValue(f.getFixMoney());
				easyFormulaFindDto.setRefMasterNo(f.getReferenceMasterCode());
				easyFormulaFindDto.setFixFormulaAtr(f.getFixFormulaAtr());
				Optional<FormulaEasyDetailDto> formulaEasyDetailDto = formulaEasyDetailRepository
						.findByPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId,
								new EasyFormulaCode(easyFormulaFindDto.getEasyFormulaCode()))
						.map(formulaEasyDetail -> FormulaEasyDetailDto.fromDomain(formulaEasyDetail));

				FormulaEasyFinderDto formulaEasyFinderDto = new FormulaEasyFinderDto();
				if (formulaEasyDetailDto.isPresent()) {
					formulaEasyFinderDto.setEasyFormulaCode(formulaEasyDetailDto.get().getEasyFormulaCode());
					formulaEasyFinderDto.setEasyFormulaName(formulaEasyDetailDto.get().getEasyFormulaName());
					formulaEasyFinderDto.setBaseFixedAmount(formulaEasyDetailDto.get().getBaseFixedAmount());
					formulaEasyFinderDto.setBaseAmountDevision(formulaEasyDetailDto.get().getBaseAmountDevision());
					formulaEasyFinderDto.setBaseFixedAmount(formulaEasyDetailDto.get().getBaseFixedAmount());
					formulaEasyFinderDto.setBaseValueDevision(formulaEasyDetailDto.get().getBaseValueDevision());
					formulaEasyFinderDto.setPremiumRate(formulaEasyDetailDto.get().getPremiumRate());
					formulaEasyFinderDto
							.setRoundProcessingDevision(formulaEasyDetailDto.get().getRoundProcessingDevision());
					formulaEasyFinderDto.setCoefficientDivision(formulaEasyDetailDto.get().getCoefficientDivision());
					formulaEasyFinderDto
							.setCoefficientFixedValue(formulaEasyDetailDto.get().getCoefficientFixedValue());
					formulaEasyFinderDto.setAdjustmentDevision(formulaEasyDetailDto.get().getAdjustmentDevision());
					formulaEasyFinderDto.setTotalRounding(formulaEasyDetailDto.get().getTotalRounding());
					formulaEasyFinderDto.setMaxLimitValue(formulaEasyDetailDto.get().getMaxLimitValue());
					formulaEasyFinderDto.setMinLimitValue(formulaEasyDetailDto.get().getMinLimitValue());
					formulaEasyFinderDto.setBaseFixedValue(formulaEasyDetailDto.get().getBaseFixedValue());
					formulaEasyFinderDto.setEasyFormulaTypeAtr(formulaEasyDetailDto.get().getEasyFormulaTypeAtr());
				}

				// Select reference Code from FormulaEasyStandardItem
				List<FormulaEasyStandardItemDto> formulaEasyStandardItemDtos = formulaEasyStandardItemRepository
						.findAll(login.companyCode(), new FormulaCode(formulaCode), historyId,
								new EasyFormulaCode(f.getEasyFormulaCode()))
						.stream().map(dto -> FormulaEasyStandardItemDto.fromDomain(dto)).collect(Collectors.toList());

				// set reference Code in List<String> referenceItemCodes
				formulaEasyFinderDto.setReferenceItemCodes(formulaEasyStandardItemDtos.stream().map(easyStandard -> {
					return easyStandard.getReferenceItemCode();
				}).collect(Collectors.toList()));

				easyFormulaFindDto.setFormulaEasyDetail(formulaEasyFinderDto);
				easyFormula.add(easyFormulaFindDto);
			});
			formulaSettingDto.setEasyFormula(easyFormula);

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
