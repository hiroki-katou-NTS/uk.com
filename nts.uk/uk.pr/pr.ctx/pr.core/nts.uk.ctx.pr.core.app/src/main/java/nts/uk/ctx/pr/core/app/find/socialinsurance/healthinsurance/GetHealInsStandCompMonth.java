package nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.HealthInsStandGradePerMonthDto;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceGradePerRewardMonthlyRange;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardGradePerMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthly;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthlyRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensation;

@Stateless
public class GetHealInsStandCompMonth {

    @Inject
    private HealthInsuranceStandardMonthlyRepository healthInsuranceStandardMonthlyRepository;

    // 等級から健康保険標準報酬月額を取得する
    public Long getHealInsStandCompMonth(HealthInsStandardMonthlyInformation param) {
        Long standardMonthlyFee = null;

        if (param.getStartYM() != null) {
            // ドメインモデル「健康保険標準月額」を取得する
            Optional<HealthInsuranceStandardMonthly> healthInsStandardMonthly = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonth(param.getStartYM().yearMonth().v());

            if (healthInsStandardMonthly.isPresent()) {
                standardMonthlyFee = healthInsStandardMonthly.get().getStandardGradePerMonth().stream()
                        .filter(x -> param.getHealInsGrade() != null && x.getHealthInsuranceGrade() == param.getHealInsGrade())
                        .map(x -> x.getStandardMonthlyFee())
                        .findFirst().orElse(null);
            }
        }

        return standardMonthlyFee;
    }
    
    // 等級から健康保険標準報酬月額を取得する - cps003
    public Map<String, Long> getHealInsStandCompMonth(List<HealthInsStandardMonthlyInformation> params) {
        
    	Map<String, Long> result = new HashMap<>();
    	
        params.stream().forEach(c ->{
        	
        	Long standardMonthlyFee = null;
        	
            if (c.getStartYM() != null) {
            	
                // ドメインモデル「健康保険標準月額」を取得する
                Optional<HealthInsuranceStandardMonthly> healthInsStandardMonthly = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonth(c.getStartYM().yearMonth().v());

                if (healthInsStandardMonthly.isPresent()) {
                	
                    standardMonthlyFee = healthInsStandardMonthly.get().getStandardGradePerMonth().stream()
                    		
                            .filter(x -> c.getHealInsGrade() != null && x.getHealthInsuranceGrade() == c.getHealInsGrade())
                           
                            .map(x -> x.getStandardMonthlyFee())
                            
                            .findFirst().orElse(null);
                    
                }
                
            }
            
            result.put(c.getSId(), standardMonthlyFee);   
            
        });

        return result;
    }

    // 報酬月額から健康保険標準報酬月額と健康保険等級を取得する
    public HealthInsStandGradePerMonthDto getHealthInsuranceStandardGradePerMonth(HealthInsStandardMonthlyInformation param) {

        HealthInsStandGradePerMonthDto perMonthDto = new HealthInsStandGradePerMonthDto();

        if (param.getStartYM() != null) {
            // ドメインモデル「健康保険報酬月額範囲」を取得する
            Optional<MonthlyHealthInsuranceCompensation> dataMonth = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonthCom(param.getStartYM().yearMonth().v());

            // 取得した等級毎月額報酬範囲からパラメータ.報酬月額を含む健康保険等級を取得する
            Optional<HealthInsuranceGradePerRewardMonthlyRange> monthlyRanges;
            if (dataMonth.isPresent()) {
                monthlyRanges = dataMonth.get().getHealthInsuranceGradePerRewardMonthlyRange().stream()
                        .filter(x -> param.getHealInsStandMonthlyRemune() != null && x.getRewardMonthlyLowerLimit() <= param.getHealInsStandMonthlyRemune() && param.getHealInsStandMonthlyRemune() <= x.getRewardMonthlyUpperLimit())
                        .findFirst();

                if (!monthlyRanges.isPresent()) {
                    val min = Collections.min(dataMonth.get().getHealthInsuranceGradePerRewardMonthlyRange().stream()
                            .map(HealthInsuranceGradePerRewardMonthlyRange::getRewardMonthlyLowerLimit).collect(Collectors.toList()));

                    if (param.getHealInsStandMonthlyRemune() != null && min > param.getHealInsStandMonthlyRemune()) {
                        perMonthDto.setHealthInsuranceGrade(1);
                    } else if (param.getHealInsStandMonthlyRemune() != null && min < param.getHealInsStandMonthlyRemune()) {

                        perMonthDto.setHealthInsuranceGrade(
                                Collections.max(dataMonth.get().getHealthInsuranceGradePerRewardMonthlyRange().stream()
                                        .map(HealthInsuranceGradePerRewardMonthlyRange::getHealthInsuranceGrade).collect(Collectors.toList()))
                        );

                    }
                } else {
                    perMonthDto.setHealthInsuranceGrade(monthlyRanges.get().getHealthInsuranceGrade());
                }
            }

            // ドメインモデル「健康保険標準月額」を取得する
            Optional<HealthInsuranceStandardMonthly> healthInsStandardMonthly = healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonth(param.getStartYM().yearMonth().v());
            healthInsStandardMonthly.ifPresent(healthInsuranceStandardMonthly ->
                    perMonthDto.setStandardMonthlyFee(
                            healthInsuranceStandardMonthly.getStandardGradePerMonth().stream()
                                    .filter(x -> perMonthDto.getHealthInsuranceGrade() != null && x.getHealthInsuranceGrade() == perMonthDto.getHealthInsuranceGrade())
                                    .map(HealthInsuranceStandardGradePerMonth::getStandardMonthlyFee)
                                    .findFirst().orElse(null)
                    ));
        }

        return perMonthDto;
    }
    
	// 報酬月額から健康保険標準報酬月額と健康保険等級を取得する -cps003 - <sid - dto>
	public Map<String, HealthInsStandGradePerMonthDto> getHealthInsuranceStandardGradePerMonth(
			List<HealthInsStandardMonthlyInformation> params) {

		Map<String, HealthInsStandGradePerMonthDto> result = new HashMap<>();

		params.stream().forEach(param -> {

			HealthInsStandGradePerMonthDto perMonthDto = new HealthInsStandGradePerMonthDto();

			if (param.getStartYM() != null) {

				// ドメインモデル「健康保険報酬月額範囲」を取得する
				Optional<MonthlyHealthInsuranceCompensation> dataMonth = healthInsuranceStandardMonthlyRepository
						
						.getHealthInsuranceStandardMonthlyByStartYearMonthCom(param.getStartYM().yearMonth().v());

				// 取得した等級毎月額報酬範囲からパラメータ.報酬月額を含む健康保険等級を取得する
				Optional<HealthInsuranceGradePerRewardMonthlyRange> monthlyRanges;

				if (dataMonth.isPresent()) {

					monthlyRanges = dataMonth.get().getHealthInsuranceGradePerRewardMonthlyRange().stream()

							.filter(x -> param.getHealInsStandMonthlyRemune() != null
							
									&& x.getRewardMonthlyLowerLimit() <= param.getHealInsStandMonthlyRemune()
									
									&& param.getHealInsStandMonthlyRemune() <= x.getRewardMonthlyUpperLimit())

							.findFirst();

					if (!monthlyRanges.isPresent()) {

						val min = Collections.min(dataMonth.get().getHealthInsuranceGradePerRewardMonthlyRange().stream()

										.map(HealthInsuranceGradePerRewardMonthlyRange::getRewardMonthlyLowerLimit)
										
										.collect(Collectors.toList()));

						if (param.getHealInsStandMonthlyRemune() != null && min > param.getHealInsStandMonthlyRemune()) {

							perMonthDto.setHealthInsuranceGrade(1);

						} else if (param.getHealInsStandMonthlyRemune() != null && min < param.getHealInsStandMonthlyRemune()) {

							perMonthDto.setHealthInsuranceGrade(

									Collections.max(dataMonth.get().getHealthInsuranceGradePerRewardMonthlyRange().stream()
											
											.map(HealthInsuranceGradePerRewardMonthlyRange::getHealthInsuranceGrade)

											.collect(Collectors.toList())));

						}

					} else {

						perMonthDto.setHealthInsuranceGrade(monthlyRanges.get().getHealthInsuranceGrade());
					}
					
				}

				// ドメインモデル「健康保険標準月額」を取得する
				Optional<HealthInsuranceStandardMonthly> healthInsStandardMonthly = healthInsuranceStandardMonthlyRepository
						
						.getHealthInsuranceStandardMonthlyByStartYearMonth(param.getStartYM().yearMonth().v());
				
				healthInsStandardMonthly.ifPresent(healthInsuranceStandardMonthly -> perMonthDto
						
						.setStandardMonthlyFee(
								
								healthInsuranceStandardMonthly.getStandardGradePerMonth().stream()
								
								.filter(x -> perMonthDto.getHealthInsuranceGrade() != null && x.getHealthInsuranceGrade() == perMonthDto.getHealthInsuranceGrade())
								
								.map(HealthInsuranceStandardGradePerMonth::getStandardMonthlyFee).findFirst()
								
								.orElse(null)));
			}

			result.put(param.getSId(), perMonthDto);

		});

		return result;
	}
}
