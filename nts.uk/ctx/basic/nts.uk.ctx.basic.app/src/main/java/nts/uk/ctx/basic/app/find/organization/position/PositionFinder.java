package nts.uk.ctx.basic.app.find.organization.position;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.position.Position;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;


	@Stateless
	public class PositionFinder {

		@Inject
		private PositionRepository positionRepository;
		
		public List<PositionDto> init() {
			String companyCode = AppContexts.user().companyCode();
			return positionRepository.findAll(companyCode)
					.stream().map(e->{return convertToDto(e);}).collect(Collectors.toList());
		}

		private PositionDto convertToDto(Position position) {
			PositionDto positionDto = new PositionDto();
			positionDto.setJobCode(position.getJobCode().v());
			positionDto.setHistoryID(position.getHistoryID());
			positionDto.setMemo(position.getMemo().v());
			positionDto.setJobOutCode(position.getJobOutCode().v());
			positionDto.setEndDate(position.getEndDate().localDate());
			positionDto.setStartDate(position.getStartDate().localDate());
			return positionDto;
		}

		public List<PositionDto> getAllPosition(String companyCode) {
			return this.positionRepository.getPositions(companyCode).stream().map(position -> PositionDto.fromDomain(position))
					.collect(Collectors.toList());
		}
}
