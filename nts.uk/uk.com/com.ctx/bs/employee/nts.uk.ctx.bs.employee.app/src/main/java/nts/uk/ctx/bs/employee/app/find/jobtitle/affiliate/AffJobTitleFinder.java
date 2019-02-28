/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.jobtitle.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author danpv
 *
 */
@Stateless
public class AffJobTitleFinder implements PeregFinder<AffJobTitleDto> {

	@Inject
	private AffJobTitleHistoryItemRepository affJobTitleItemRepo;

	@Inject
	private AffJobTitleHistoryRepository affJobTitleRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00016";
	}

	@Override
	public Class<AffJobTitleDto> dtoClass() {
		return AffJobTitleDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		
		Optional<AffJobTitleHistory> historyOpt;
		if ( query.getInfoId() != null ) {
			historyOpt = affJobTitleRepo.getByHistoryId(query.getInfoId());
		} else {
			historyOpt = affJobTitleRepo.getByEmpIdAndStandardDate(query.getEmployeeId(), query.getStandardDate());
		}
		
		if ( historyOpt.isPresent()) {
			Optional<AffJobTitleHistoryItem> histItemOpt = affJobTitleItemRepo
					.findByHitoryId(historyOpt.get().getHistoryItems().get(0).identifier());
			if ( histItemOpt.isPresent()) {
				return AffJobTitleDto.createFromDomain(histItemOpt.get(), historyOpt.get());
			}
		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.shr.pereg.app.find.PeregFinder#getListData(nts.uk.shr.pereg.app.
	 * find.PeregQuery)
	 */
	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		Optional<AffJobTitleHistory> historyOpt = affJobTitleRepo.getListBySidDesc(AppContexts.user().companyId(),
				query.getEmployeeId());
		if (historyOpt.isPresent()) {
			return historyOpt.get().getHistoryItems().stream()
					.filter(x -> affJobTitleItemRepo.findByHitoryId(x.identifier()).isPresent())
					.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
							x.end().equals(GeneralDate.max()) 
							//&& query.getCtgType() == 3 
							? "" : x.end().toString()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List < GridPeregDomainDto > getAllData(PeregQueryByListEmp query) {
	    List < GridPeregDomainDto > result = new ArrayList < > ();
	    // key - sid , value - pid getEmployeeId getPersonId
	    Map < String, String > mapSids = query.getEmpInfos().stream()
	        .collect(Collectors.toMap(PeregEmpInfoQuery::getEmployeeId, PeregEmpInfoQuery::getPersonId));
	    List < AffJobTitleHistory > affJobLst = affJobTitleRepo
	        .getListByListHidSid(new ArrayList < String > (mapSids.keySet()), query.getStandardDate());
	    List < String > histIds = affJobLst.stream().map(c -> c.getHistoryIds().get(0)).collect(Collectors.toList());
	    List < AffJobTitleHistoryItem > histItems = affJobTitleItemRepo.findByHitoryIds(histIds);
	    
	    histItems.stream().forEach(item -> {
	        Optional < AffJobTitleHistory > affJobTittle = affJobLst.stream()
	        .filter(c -> c.getHistoryIds().get(0).equals(item.getHistoryId())).findFirst();
	        if (affJobTittle.isPresent()) {
	            result.add(new GridPeregDomainDto(item.getEmployeeId(), mapSids.get(item.getEmployeeId()),
	                AffJobTitleDto.createFromDomain(item, affJobTittle.get())));
	        } else {
	            result.add(new GridPeregDomainDto(item.getEmployeeId(), mapSids.get(item.getEmployeeId()), null));
	        }
	    });

	    if (query.getEmpInfos().size() > result.size()) {
	        for (int i = result.size(); i < query.getEmpInfos().size(); i++) {
	            PeregEmpInfoQuery emp = query.getEmpInfos().get(i);
	            result.add(new GridPeregDomainDto(emp.getEmployeeId(), emp.getPersonId(), null));
	        }
	    }
	    return result;
	}
}
