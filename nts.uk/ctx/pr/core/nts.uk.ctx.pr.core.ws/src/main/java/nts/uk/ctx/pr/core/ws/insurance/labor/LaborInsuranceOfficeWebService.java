/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeAddCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeAddCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeDeleteCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeDeleteCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeUpdateCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeUpdateCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeDto;
<<<<<<< HEAD:nts.uk/ctx/pr/core/nts.uk.ctx.pr.core.ws/src/main/java/nts/uk/ctx/pr/core/ws/insurance/labor/LaborInsuranceOfficeService.java
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeFindInDto;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeFindOutDto;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeFinder;
=======
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento;
import nts.uk.shr.com.primitive.Memo;
>>>>>>> 913289dff5c93b85229ad0a61a6085d8c4d5e741:nts.uk/ctx/pr/core/nts.uk.ctx.pr.core.ws/src/main/java/nts/uk/ctx/pr/core/ws/insurance/labor/LaborInsuranceOfficeWebService.java

@Path("ctx/pr/core/insurance/labor")
@Produces("application/json")
public class LaborInsuranceOfficeWebService extends WebService {

	/** The find. */
	@Inject
	private LaborInsuranceOfficeFinder find;

	/** The add. */
	@Inject
	private LaborInsuranceOfficeAddCommandHandler add;

	/** The update. */
	@Inject
	private LaborInsuranceOfficeUpdateCommandHandler update;

	/** The delete. */
	@Inject
	private LaborInsuranceOfficeDeleteCommandHandler delete;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall/{companyCode}")
	public List<LaborInsuranceOfficeFindOutDto> findAll(@PathParam("companyCode") String companyCode) {
		return find.findAll(companyCode);
	}

	/**
	 * Find by code.
	 *
	 * @param code
	 *            the code
	 * @return the labor insurance office dto
	 */
	// Find LaborInsuranceOffice By Code
	@POST
<<<<<<< HEAD:nts.uk/ctx/pr/core/nts.uk.ctx.pr.core.ws/src/main/java/nts/uk/ctx/pr/core/ws/insurance/labor/LaborInsuranceOfficeService.java
	@Path("findLaborInsuranceOffice")
	public LaborInsuranceOfficeDto findByCode(LaborInsuranceOfficeFindInDto data) {
		return find.find(data.getCode(), data.getCompanyCode());
=======
	@Path("findLaborInsuranceOffice/{code}")
	public LaborInsuranceOfficeDto findByCode(@PathParam("code") String code) {
		LaborInsuranceOfficeDto laborInsuranceOfficeDto = null;
		List<LaborInsuranceOffice> lstLaborInsuranceOffice = findAllLaborInsuranceOffice();
		for (int index = 0; index < lstLaborInsuranceOffice.size(); index++) {
			if (lstLaborInsuranceOffice.get(index).getCode().toString().equals(code)) {
				laborInsuranceOfficeDto = convertLaborInsuranceOfficeDto(lstLaborInsuranceOffice.get(index));
			}
		}
		return laborInsuranceOfficeDto;
	}

	/**
	 * Find all labor insurance office.
	 *
	 * @return the list
	 */
	// Find All LaborInsuranceOffice
	public List<LaborInsuranceOffice> findAllLaborInsuranceOffice() {
		List<LaborInsuranceOffice> lstLaborInsuranceOffice = new ArrayList<LaborInsuranceOffice>();
		LaborInsuranceOffice laborInsuranceOffice001 = new LaborInsuranceOffice(new LaborInsuranceOfficeGetMemento() {
			@Override
			public Long getVersion() {
				return 0L;
			}

			@Override
			public ShortName getShortName() {
				// TODO Auto-generated method stub
				return new ShortName("shortName");
			}

			@Override
			public String getPrefecture() {
				return "prefecture";
			}

			@Override
			public PotalCode getPotalCode() {
				return new PotalCode("potalCode");
			}

			@Override
			public PicPosition getPicPosition() {
				return new PicPosition("picPosition");
			}

			@Override
			public PicName getPicName() {
				return new PicName("picName");
			}

			@Override
			public String getPhoneNumber() {
				return "4346234645624";
			}

			@Override
			public String getOfficeNoC() {
				return "OfficeNoC";
			}

			@Override
			public String getOfficeNoB() {
				return "OfficeNoB";
			}

			@Override
			public String getOfficeNoA() {
				return "OfficeNoA";
			}

			@Override
			public String getOfficeMark() {
				return "officeMark";
			}

			@Override
			public OfficeName getName() {
				return new OfficeName("XA事業所");
			}

			@Override
			public Memo getMemo() {
				return new Memo("memo");
			}

			@Override
			public KanaAddress getKanaAddress2nd() {
				return new KanaAddress("kanaAddress2nd");
			}

			@Override
			public KanaAddress getKanaAddress1st() {
				return new KanaAddress("kanaAddress1st");
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode("companyCode001");
			}

			@Override
			public OfficeCode getCode() {
				return new OfficeCode("000000000001");
			}

			@Override
			public String getCitySign() {
				return "01";
			}

			@Override
			public Address getAddress2nd() {
				return new Address("address2nd");
			}

			@Override
			public Address getAddress1st() {
				return new Address("address1st");
			}
		});
		lstLaborInsuranceOffice.add(laborInsuranceOffice001);
		return lstLaborInsuranceOffice;
	}

	/**
	 * Convert insurance office O in dto.
	 *
	 * @param laborInsuranceOffice
	 *            the labor insurance office
	 * @return the labor insurance office in dto
	 */
	public LaborInsuranceOfficeInDto convertInsuranceOfficeOInDto(LaborInsuranceOffice laborInsuranceOffice) {
		LaborInsuranceOfficeInDto laborInsuranceOfficeInDto = new LaborInsuranceOfficeInDto();
		laborInsuranceOfficeInDto.setName(laborInsuranceOffice.getName().toString());
		laborInsuranceOfficeInDto.setCode(laborInsuranceOffice.getCode().toString());
		return laborInsuranceOfficeInDto;
	}

	/**
	 * Convert labor insurance office dto.
	 *
	 * @param laborInsuranceOffice
	 *            the labor insurance office
	 * @return the labor insurance office dto
	 */
	public LaborInsuranceOfficeDto convertLaborInsuranceOfficeDto(LaborInsuranceOffice laborInsuranceOffice) {
		LaborInsuranceOfficeDto laborInsuranceOfficeDto = LaborInsuranceOfficeDto.builder()
				.name(laborInsuranceOffice.getName().toString()).code(laborInsuranceOffice.getCode().toString())
				.shortName(laborInsuranceOffice.getShortName().toString())
				.picName(laborInsuranceOffice.getPicName().toString())
				.picPosition(laborInsuranceOffice.getPicPosition().toString())
				.potalCode(laborInsuranceOffice.getPotalCode().toString())
				.prefecture(laborInsuranceOffice.getPrefecture())
				.address1st(laborInsuranceOffice.getAddress1st().toString())
				.address2nd(laborInsuranceOffice.getAddress2nd().toString())
				.kanaAddress1st(laborInsuranceOffice.getKanaAddress1st().toString())
				.kanaAddress2nd(laborInsuranceOffice.getKanaAddress2nd().toString())
				.phoneNumber(laborInsuranceOffice.getPhoneNumber()).citySign(laborInsuranceOffice.getCitySign())
				.officeMark(laborInsuranceOffice.getOfficeMark()).officeNoA(laborInsuranceOffice.getOfficeNoA())
				.officeNoB(laborInsuranceOffice.getOfficeNoB()).officeNoC(laborInsuranceOffice.getOfficeNoC())
				.memo(laborInsuranceOffice.getMemo().toString()).build();
		return laborInsuranceOfficeDto;
>>>>>>> 913289dff5c93b85229ad0a61a6085d8c4d5e741:nts.uk/ctx/pr/core/nts.uk.ctx.pr.core.ws/src/main/java/nts/uk/ctx/pr/core/ws/insurance/labor/LaborInsuranceOfficeWebService.java
	}

	/**
	 * Adds the.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("add")
	public void add(LaborInsuranceOfficeAddCommand command) {
		this.add.handle(command);
	}

	/**
	 * Update.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("update")
	public void update(LaborInsuranceOfficeUpdateCommand command) {
		this.update.handle(command);
	}

	/**
	 * Delete.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("delete")
	public void delete(LaborInsuranceOfficeDeleteCommand command) {
		this.delete.handle(command);
	}
}
