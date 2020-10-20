package nts.uk.ctx.at.shared.app.find.calculation.setting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOT;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor.DeformLaborOTRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author yennh
 *
 */
@Stateless
public class DeformLaborOTFinder {
	@Inject
	private DeformLaborOTRepository repository;

	/**
	 * Find all DeformLaborOT
	 * 
	 * @return
	 */
	public List<DeformLaborOTDto> findAllDeformLaborOT() {
		String companyId = AppContexts.user().companyId();
		return repository.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}
	
	private DeformLaborOTDto convertToDbType(DeformLaborOT deformLaborOT){
		DeformLaborOTDto deformLaborOTDto = new DeformLaborOTDto();
		deformLaborOTDto.setLegalOtCalc(deformLaborOT.getLegalOtCalc());
		return deformLaborOTDto;
	}
}
