package nts.uk.ctx.pr.formula.app.find.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.app.find.formulaeasycondition.EasyFormulaConditionDto;
import nts.uk.ctx.pr.formula.app.find.formulaeasycondition.FormulaEasyConditionDto;
import nts.uk.ctx.pr.formula.app.find.formulaeasydetail.FormulaEasyDetailDto;
import nts.uk.ctx.pr.formula.app.find.formulaeasystandarditem.FormulaEasyStandardItemDto;
import nts.uk.ctx.pr.formula.app.find.formulamanual.FormulaManualDto;
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

			List<EasyFormulaFindDto> easyFormula = new ArrayList<>();
			formulaEasyConditions.get().getEasyFormulaConditionDto().stream().map(f -> {
				EasyFormulaFindDto easyFormulaFindDto = new EasyFormulaFindDto();
				easyFormulaFindDto.setEasyFormulaCode(f.getEasyFormulaCode());
				easyFormulaFindDto.setValue(f.getFixMoney());
				easyFormulaFindDto.setRefMasterNo(f.getReferenceMasterCode());
				easyFormula.add(easyFormulaFindDto);
				return easyFormula;
			});
			formulaSettingDto.setEasyFormula(easyFormula);

			List<FormulaEasyDetailDto> formulaEasyDetailDtos = formulaEasyDetailRepository
					.findWithOutPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId).stream()
					.map(f -> FormulaEasyDetailDto.fromDomain(f)).collect(Collectors.toList());
			FormulaEasyFinderDto formulaEasyFinderDto = new FormulaEasyFinderDto();
			List<FormulaEasyFinderDto> formulaEasyDetail = new ArrayList<>();
			formulaEasyDetailDtos.stream().map(f -> {
				formulaEasyFinderDto.setEasyFormulaCode(f.getEasyFormulaCode());
				formulaEasyFinderDto.setEasyFormulaName(f.getEasyFormulaName());
				formulaEasyFinderDto.setBaseFixedAmount(f.getBaseFixedAmount());
				formulaEasyFinderDto.setBaseAmountDevision(f.getBaseAmountDevision());
				formulaEasyFinderDto.setBaseFixedAmount(f.getBaseFixedAmount());
				formulaEasyFinderDto.setBaseValueDevision(f.getBaseValueDevision());
				formulaEasyFinderDto.setPremiumRate(f.getPremiumRate());
				formulaEasyFinderDto.setRoundProcessingDevision(f.getRoundProcessingDevision());
				formulaEasyFinderDto.setCoefficientDivision(f.getCoefficientDivision());
				formulaEasyFinderDto.setCoefficientFixedValue(f.getCoefficientFixedValue());
				formulaEasyFinderDto.setAdjustmentDevision(f.getAdjustmentDevision());
				formulaEasyFinderDto.setTotalRounding(f.getTotalRounding());
				formulaEasyFinderDto.setMaxLimitValue(f.getMaxLimitValue());
				formulaEasyFinderDto.setMinLimitValue(f.getMinLimitValue());
				

				List<FormulaEasyStandardItemDto> formulaEasyStandardItemDtos = formulaEasyStandardItemRepository
						.findAll(login.companyCode(), new FormulaCode(formulaCode), historyId, new EasyFormulaCode(f.getEasyFormulaCode()))
						.stream().map(dto -> FormulaEasyStandardItemDto.fromDomain(dto)).collect(Collectors.toList());
				List<String> referenceItemCodes = new ArrayList<>();
				formulaEasyStandardItemDtos.stream().map(easyStandard -> {
					referenceItemCodes.add(easyStandard.getReferenceItemCode());
					return referenceItemCodes;
				});
				formulaEasyFinderDto.setReferenceItemCodes(referenceItemCodes);
				return formulaEasyDetail.add(formulaEasyFinderDto);
			});
			
			
			formulaSettingDto.setFormulaEasyDetail(formulaEasyDetail);

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
