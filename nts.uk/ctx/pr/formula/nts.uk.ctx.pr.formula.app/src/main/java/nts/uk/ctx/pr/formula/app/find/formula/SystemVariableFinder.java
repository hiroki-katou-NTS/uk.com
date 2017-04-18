package nts.uk.ctx.pr.formula.app.find.formula;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.formula.dom.repository.SystemVariableRepository;

/**
 * @author nampt
 *
 */
@Stateless
public class SystemVariableFinder {

	@Inject
	private SystemVariableRepository systemVariableRepository;

	public List<SystemVariableDto> init() {

		List<SystemVariableDto> systemVariables = new ArrayList<>();

		systemVariables.add(new SystemVariableDto("システム日付(年月日)", "1"));
		systemVariables.add(new SystemVariableDto("システム日付（年月）", "2"));
		systemVariables.add(new SystemVariableDto("システム日付（年）", "3"));
		systemVariables.addAll(systemVariableRepository.findAll().stream().map(f -> SystemVariableDto.fromDomain(f))
				.collect(Collectors.toList()));

		systemVariables.forEach(item -> {
			if (item.getSystemVariableCode().equals("1")) {
				item.setResult(GeneralDate.today().toString());
			} else if (item.getSystemVariableCode().equals("2")) {
				item.setResult(GeneralDate.today().yearMonth().toString());
			} else if (item.getSystemVariableCode().equals("3")) {
				item.setResult(String.valueOf(GeneralDate.today().year()));
			} else if (item.getSystemVariableCode().equals("6")) {
				BigDecimal timeNotationSet = new BigDecimal("");
				BigDecimal baseHours = new BigDecimal("");
				if (timeNotationSet == BigDecimal.ONE) {
					item.setResult(baseHours.divide(new BigDecimal(60), 2, RoundingMode.HALF_UP).toString());
				} else {
					int hour = baseHours.intValue() / 60;
					int minute = baseHours.intValue() % 60;
					item.setResult(hour + ":" + minute);
				}
			}
		});

		return systemVariables;
	}

}
