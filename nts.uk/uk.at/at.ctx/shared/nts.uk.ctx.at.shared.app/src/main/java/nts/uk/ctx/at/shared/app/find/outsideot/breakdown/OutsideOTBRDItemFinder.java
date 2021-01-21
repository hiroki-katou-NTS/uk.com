/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.breakdown;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTBRDItemDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeFinder.
 */
@Stateless
public class OutsideOTBRDItemFinder {
	
	/** The repository. */
	@Inject
	private OutsideOTSettingRepository repository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<OutsideOTBRDItemDto> findAll() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		List<OutsideOTBRDItem> overtimeBRDItems = this.repository.findAllBRDItem(companyId);

		// check exist data
		if(CollectionUtil.isEmpty(overtimeBRDItems)){
			List<OutsideOTBRDItemDto> resData = new ArrayList<>();
			for(int i = BreakdownItemNo.ONE.value; i <=BreakdownItemNo.TEN.value ; i++){
				OutsideOTBRDItemDto dto = new OutsideOTBRDItemDto();
				dto.defaultData(i);
				resData.add(dto);
			}
			return resData;
		}
		// return data by find repository
		return overtimeBRDItems.stream().map(domain -> {
			OutsideOTBRDItemDto dto = new OutsideOTBRDItemDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
	
	
}
