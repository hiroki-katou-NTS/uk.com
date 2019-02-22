package nts.uk.ctx.pereg.app.command.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.uk.ctx.pereg.dom.person.error.ErrorWarningEmployeeInfo;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.GridInputContainer;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@ApplicationScoped
public class GridCommandFacade {
	@Inject
	private PeregCommandFacade commandFacade;

	@Inject
	private ManagedParallelWithContext parallel;

	private static final Map<String, String> startDateItemCodes;
	static {
		Map<String, String> aMap = new HashMap<>();
		// 分類１
		aMap.put("CS00004", "IS00026");
		// 雇用
		aMap.put("CS00014", "IS00066");
		// 職位本務
		aMap.put("CS00016", "IS00077");
		// 職場
		aMap.put("CS00017", "IS00082");
		// 休職休業
		aMap.put("CS00018", "IS00087");
		// 短時間勤務
		aMap.put("CS00019", "IS00102");
		// 労働条件
		aMap.put("CS00020", "IS00119");
		// 勤務種別
		aMap.put("CS00021", "IS00255");
		// 労働条件２
		aMap.put("CS00070", "IS00781");

		startDateItemCodes = Collections.unmodifiableMap(aMap);
	}

	@SuppressWarnings("finally")
	public Collection<?> registerHandler(GridInputContainer gridInputContainer) {
		
		List<Object> resultsSync = Collections.synchronizedList(new ArrayList<>());
		
		List<Object> errorLstSync = Collections.synchronizedList(new ArrayList<>());
		
		try { 
			
			this.parallel.forEach(gridInputContainer.getEmployees(), input -> {
				
				PeregInputContainer container = new PeregInputContainer(input.getPersonId(), input.getEmployeeId(), Arrays.asList(input.getInput()));
				
			
				
				try {
					
					Object obj = commandFacade.registerHandler(container);
					
					resultsSync.add(obj);
					
				} catch (Throwable t) {
					
					BusinessException exp = (BusinessException) t.getCause();
					
					if (startDateItemCodes.containsKey(input.getInput().getCategoryCd())) {
						
						String itemCode = startDateItemCodes.get(input.getInput().getCategoryCd());
						
						Optional<ItemValue> itemOptional = input.getInput().getItems().stream()
								.filter(c -> {return c.itemCode().equals(itemCode);}).findFirst();
						
						if (itemOptional.isPresent()) {
							ErrorWarningEmployeeInfo errorWarning = new ErrorWarningEmployeeInfo(input.getEmployeeId(),
									input.getEmployeeCd(), input.getEmployeeName(), input.getOrder(), true, 1, itemOptional.get().itemName(), exp.getMessage());							
							errorLstSync.add(errorWarning);
							
						}
					}
				}
			});
		} catch (Throwable t) {
			
			throw t;
			
		} finally {
			
			if (errorLstSync.size() > 0) {
				
				return errorLstSync;
				
			}
			
			return resultsSync;
		}

	}
}
