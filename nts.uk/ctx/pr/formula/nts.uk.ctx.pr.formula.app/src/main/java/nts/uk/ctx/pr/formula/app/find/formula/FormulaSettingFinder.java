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
				if (f.getFixFormulaAtr() == 1) {
					Optional<FormulaEasyDetailDto> formulaEasyDetailDto = formulaEasyDetailRepository
							.findByPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId,
									new EasyFormulaCode(easyFormulaFindDto.getEasyFormulaCode()))
							.map(formulaEasyDetail -> FormulaEasyDetailDto.fromDomain(formulaEasyDetail));

					FormulaEasyFinderDto formulaEasyFinderDto = new FormulaEasyFinderDto();
					formulaEasyFinderDto.setEasyFormulaCode(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getEasyFormulaCode() : null);
					formulaEasyFinderDto.setEasyFormulaName(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getEasyFormulaName() : null);
					formulaEasyFinderDto.setBaseFixedAmount(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getBaseFixedAmount() : null);
					formulaEasyFinderDto.setBaseAmountDevision(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getBaseAmountDevision() : null);
					formulaEasyFinderDto.setBaseFixedAmount(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getBaseFixedAmount() : null);
					formulaEasyFinderDto.setBaseValueDevision(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getBaseValueDevision() : null);
					formulaEasyFinderDto.setPremiumRate(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getPremiumRate() : null);
					formulaEasyFinderDto.setRoundProcessingDevision(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getRoundProcessingDevision() : null);
					formulaEasyFinderDto.setCoefficientDivision(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getCoefficientDivision() : null);
					formulaEasyFinderDto.setCoefficientFixedValue(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getCoefficientFixedValue() : null);
					formulaEasyFinderDto.setAdjustmentDevision(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getAdjustmentDevision() : null);
					formulaEasyFinderDto.setTotalRounding(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getTotalRounding() : null);
					formulaEasyFinderDto.setMaxLimitValue(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getMaxLimitValue() : null);
					formulaEasyFinderDto.setMinLimitValue(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getMinLimitValue() : null);
					formulaEasyFinderDto.setBaseFixedValue(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getBaseFixedValue() : null);
					formulaEasyFinderDto.setEasyFormulaTypeAtr(formulaEasyDetailDto.isPresent() ? formulaEasyDetailDto.get().getEasyFormulaTypeAtr() : null);

					// Select reference Code from FormulaEasyStandardItem
					List<FormulaEasyStandardItemDto> formulaEasyStandardItemDtos = formulaEasyStandardItemRepository
							.findAll(login.companyCode(), new FormulaCode(formulaCode), historyId,
									new EasyFormulaCode(f.getEasyFormulaCode()))
							.stream().map(dto -> FormulaEasyStandardItemDto.fromDomain(dto))
							.collect(Collectors.toList());

					// set reference Code in List<String> referenceItemCodes
					formulaEasyFinderDto
							.setReferenceItemCodes(formulaEasyStandardItemDtos.stream().map(easyStandard -> {
								return easyStandard.getReferenceItemCode();
							}).collect(Collectors.toList()));

					easyFormulaFindDto.setFormulaEasyDetail(formulaEasyFinderDto);
				}
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
