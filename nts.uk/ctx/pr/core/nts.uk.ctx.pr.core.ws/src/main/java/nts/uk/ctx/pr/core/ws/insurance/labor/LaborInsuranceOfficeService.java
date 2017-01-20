/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.core.app.insurance.labor.LaborInsuranceOfficeInDto;
import nts.uk.ctx.core.app.insurance.labor.command.LaborInsuranceOfficeAddCommand;
import nts.uk.ctx.core.app.insurance.labor.command.LaborInsuranceOfficeAddCommandHandler;
import nts.uk.ctx.core.app.insurance.labor.command.LaborInsuranceOfficeDeleteCommand;
import nts.uk.ctx.core.app.insurance.labor.command.LaborInsuranceOfficeDeleteCommandHandler;
import nts.uk.ctx.core.app.insurance.labor.command.LaborInsuranceOfficeUpdateCommand;
import nts.uk.ctx.core.app.insurance.labor.command.LaborInsuranceOfficeUpdateCommandHandler;
import nts.uk.ctx.core.app.insurance.labor.find.LaborInsuranceOfficeDto;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.shr.com.primitive.Memo;

@Path("ctx/pr/core/insurance/labor")
@Produces("application/json")
public class LaborInsuranceOfficeService extends WebService {

	/** The add. */
	// Find all LaborInsuranceOffice conection data
	@Inject
	private LaborInsuranceOfficeAddCommandHandler add;

	/** The update. */
	@Inject
	private LaborInsuranceOfficeUpdateCommandHandler update;

	/** The delete. */
	@Inject
	private LaborInsuranceOfficeDeleteCommandHandler delete;

	@POST
	@Path("findall")
	public List<LaborInsuranceOfficeInDto> findAll() {
		List<LaborInsuranceOfficeInDto> lstLaborInsuranceOfficeIn = new ArrayList<LaborInsuranceOfficeInDto>();
		List<LaborInsuranceOffice> lstLaborInsuranceOffice = findAllLaborInsuranceOffice();
		for (int index = 0; index < lstLaborInsuranceOffice.size(); index++) {
			lstLaborInsuranceOfficeIn.add(convertInsuranceOfficeOInDto(lstLaborInsuranceOffice.get(index)));
		}
		return lstLaborInsuranceOfficeIn;
	}

	// Find LaborInsuranceOffice By Code
	@POST
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

	public List<LaborInsuranceOffice> findAllLaborInsuranceOffice() {
		List<LaborInsuranceOffice> lstLaborInsuranceOffice = new ArrayList<LaborInsuranceOffice>();
		LaborInsuranceOffice laborInsuranceOffice001 = new LaborInsuranceOffice();
		laborInsuranceOffice001.setCompanyCode(new CompanyCode("companyCode001"));
		laborInsuranceOffice001.setCode(new OfficeCode("000000000001"));
		laborInsuranceOffice001.setName(new OfficeName("XA事業所"));
		laborInsuranceOffice001.setShortName(new ShortName("shortName"));
		laborInsuranceOffice001.setPicName(new PicName("picName"));
		laborInsuranceOffice001.setPicPosition(new PicPosition("picPosition"));
		laborInsuranceOffice001.setPotalCode(new PotalCode("potalCode"));
		laborInsuranceOffice001.setPrefecture("prefecture");
		laborInsuranceOffice001.setAddress1st(new Address("address1st"));
		laborInsuranceOffice001.setAddress2nd(new Address("address2nd"));
		laborInsuranceOffice001.setKanaAddress1st(new KanaAddress("kanaAddress1st"));
		laborInsuranceOffice001.setKanaAddress2nd(new KanaAddress("kanaAddress2nd"));
		laborInsuranceOffice001.setPhoneNumber("phoneNumber");
		laborInsuranceOffice001.setCitySign("01");
		laborInsuranceOffice001.setOfficeMark("officeMark");
		laborInsuranceOffice001.setOfficeNoA("1234");
		laborInsuranceOffice001.setOfficeNoB("567890");
		laborInsuranceOffice001.setOfficeNoC("1");
		laborInsuranceOffice001.setMemo(new Memo("memo"));
		lstLaborInsuranceOffice.add(laborInsuranceOffice001);
		LaborInsuranceOffice laborInsuranceOffice002 = new LaborInsuranceOffice();
		laborInsuranceOffice002.setCompanyCode(new CompanyCode("companyCode001"));
		laborInsuranceOffice002.setCode(new OfficeCode("000000000002"));
		laborInsuranceOffice002.setName(new OfficeName("B事業所"));
		laborInsuranceOffice002.setShortName(new ShortName("shortName"));
		laborInsuranceOffice002.setPicName(new PicName("picName"));
		laborInsuranceOffice002.setPicPosition(new PicPosition("picPosition"));
		laborInsuranceOffice002.setPotalCode(new PotalCode("potalCode"));
		laborInsuranceOffice002.setPrefecture("prefecture");
		laborInsuranceOffice002.setAddress1st(new Address("address1st"));
		laborInsuranceOffice002.setAddress2nd(new Address("address2nd"));
		laborInsuranceOffice002.setKanaAddress1st(new KanaAddress("kanaAddress1st"));
		laborInsuranceOffice002.setKanaAddress2nd(new KanaAddress("kanaAddress2nd"));
		laborInsuranceOffice002.setPhoneNumber("phoneNumber");
		laborInsuranceOffice002.setCitySign("01");
		laborInsuranceOffice002.setOfficeMark("officeMark");
		laborInsuranceOffice002.setOfficeNoA("1234");
		laborInsuranceOffice002.setOfficeNoB("567890");
		laborInsuranceOffice002.setOfficeNoC("1");
		laborInsuranceOffice002.setMemo(new Memo("memo"));
		lstLaborInsuranceOffice.add(laborInsuranceOffice002);
		LaborInsuranceOffice laborInsuranceOffice003 = new LaborInsuranceOffice();
		laborInsuranceOffice003.setCompanyCode(new CompanyCode("companyCode001"));
		laborInsuranceOffice003.setCode(new OfficeCode("000000000003"));
		laborInsuranceOffice003.setName(new OfficeName("C事業所"));
		laborInsuranceOffice003.setShortName(new ShortName("shortName"));
		laborInsuranceOffice003.setPicName(new PicName("picName"));
		laborInsuranceOffice003.setPicPosition(new PicPosition("picPosition"));
		laborInsuranceOffice003.setPotalCode(new PotalCode("potalCode"));
		laborInsuranceOffice003.setPrefecture("prefecture");
		laborInsuranceOffice003.setAddress1st(new Address("address1st"));
		laborInsuranceOffice003.setAddress2nd(new Address("address2nd"));
		laborInsuranceOffice003.setKanaAddress1st(new KanaAddress("kanaAddress1st"));
		laborInsuranceOffice003.setKanaAddress2nd(new KanaAddress("kanaAddress2nd"));
		laborInsuranceOffice003.setPhoneNumber("phoneNumber");
		laborInsuranceOffice003.setCitySign("01");
		laborInsuranceOffice003.setOfficeMark("officeMark");
		laborInsuranceOffice003.setOfficeNoA("1234");
		laborInsuranceOffice003.setOfficeNoB("567890");
		laborInsuranceOffice003.setOfficeNoC("1");
		laborInsuranceOffice003.setMemo(new Memo("memo"));
		lstLaborInsuranceOffice.add(laborInsuranceOffice003);
		return lstLaborInsuranceOffice;
	}

	public LaborInsuranceOfficeInDto convertInsuranceOfficeOInDto(LaborInsuranceOffice laborInsuranceOffice) {
		LaborInsuranceOfficeInDto laborInsuranceOfficeInDto = new LaborInsuranceOfficeInDto();
		laborInsuranceOfficeInDto.setName(laborInsuranceOffice.getName().toString());
		laborInsuranceOfficeInDto.setCode(laborInsuranceOffice.getCode().toString());
		return laborInsuranceOfficeInDto;
	}

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

	}

	@POST
	@Path("add")
	public void add(LaborInsuranceOfficeAddCommand command) {
		this.add.handle(command);
	}

	@POST
	@Path("update")
	public void update(LaborInsuranceOfficeUpdateCommand command) {
		this.update.handle(command);
	}

	@POST
	@Path("delete")
	public void delete(LaborInsuranceOfficeDeleteCommand command) {
		this.delete.handle(command);
	}
}
