package nts.uk.ctx.core.app.command.printdata.companystatuwrite.command;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.core.app.find.printdata.companystatuwrite.CreateCompanyWriteDto;
import nts.uk.ctx.core.app.find.printdata.companystatuwrite.CusCompanyWriteDto;
import nts.uk.ctx.core.dom.printdata.CompanyStatutoryWrite;
import nts.uk.ctx.core.dom.printdata.CompanyStatutoryWriteRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class CreateCompanyWriteHandler extends CommandHandlerWithResult<CompanyStatutoryWriteCommand, CreateCompanyWriteDto> {

    @Inject
    private CompanyStatutoryWriteRepository companyStatutoryWriteRepository;

    @Override
    protected CreateCompanyWriteDto handle(CommandHandlerContext<CompanyStatutoryWriteCommand> context) {
        CreateCompanyWriteDto response = new CreateCompanyWriteDto();
        CompanyStatutoryWriteCommand data = context.getCommand();
        Optional<CompanyStatutoryWrite> exit = companyStatutoryWriteRepository.getCompanyStatutoryWriteById(AppContexts.user().companyId(),data.getCode());
        if(exit.isPresent()) {
            response = new CreateCompanyWriteDto(null,null,"Msg_3");
        } else {
            companyStatutoryWriteRepository.add(mappingDomain(data));
            List<CompanyStatutoryWrite> listCompanyStatu = companyStatutoryWriteRepository.getByCid(AppContexts.user().companyId());
            List<CusCompanyWriteDto> companies  = listCompanyStatu.stream().map(x -> new CusCompanyWriteDto(x.getCode().v(),x.getName().v())).collect(Collectors.toList());
            response = new CreateCompanyWriteDto(data.getCode(),companies,"Msg_16");
        }
		return response;
    }

    private CompanyStatutoryWrite mappingDomain(CompanyStatutoryWriteCommand data) {
        return new CompanyStatutoryWrite(AppContexts.user().companyId(),data.getCode(),data.getName(),data.getKanaName(),
                data.getAddress1().isEmpty() ? null : data.getAddress1() ,data.getAddress2().isEmpty() ? null : data.getAddress2(),data.getAddressKana1().isEmpty() ? null : data.getAddressKana1(),data.getAddressKana2().isEmpty() ? null : data.getAddressKana2(),data.getPhoneNumber().isEmpty() ? null : data.getPhoneNumber(),data.getPostalCode().isEmpty() ? null:data.getPostalCode() ,
                data.getNotes(),data.getClubRepresentativePosition().isEmpty() ? null : data.getClubRepresentativePosition(),data.getClubRepresentativeName().isEmpty() ? null : data.getClubRepresentativeName(),data.getLinkingDepartment(),data.getCorporateNumber(),
                data.getAccountingOfficeTelephoneNumber().isEmpty() ? null : data.getAccountingOfficeTelephoneNumber(),data.getAccountingOfficeName().isEmpty() ? null : data.getAccountingOfficeName(),data.getSalaryPaymentMethodAndDueDate1().isEmpty() ? null : data.getSalaryPaymentMethodAndDueDate1(),
                data.getSalaryPaymentMethodAndDueDate2().isEmpty() ? null : data.getSalaryPaymentMethodAndDueDate2(),data.getSalaryPaymentMethodAndDueDate3().isEmpty() ? null : data.getSalaryPaymentMethodAndDueDate3(),data.getAccountManagerName().isEmpty() ? null : data.getAccountManagerName(),data.getBusinessLine1().isEmpty() ? null : data.getBusinessLine1(),
                data.getBusinessLine2().isEmpty() ? null : data.getBusinessLine2(),data.getBusinessLine3().isEmpty() ? null : data.getBusinessLine3(),data.getTaxOffice().isEmpty() ? null : data.getTaxOffice(),data.getVibrantLocationFinancialInstitutions().isEmpty() ? null : data.getVibrantLocationFinancialInstitutions(),data.getNameBankTransferInstitution().isEmpty() ? null : data.getNameBankTransferInstitution(),
                data.getContactName().isEmpty() ? null : data.getContactName(),data.getContactClass().isEmpty() ? null : data.getContactClass(),data.getContactPhoneNumber().isEmpty() ? null : data.getContactPhoneNumber());
    }
}
