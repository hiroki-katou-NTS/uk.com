package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import java.util.List;
import java.util.Optional;

/**
* 公共職業安定所
*/
public interface PublicEmploymentSecurityOfficeRepository {

    List<PublicEmploymentSecurityOffice> getPublicEmploymentSecurityOfficeByCompany();

    Optional<PublicEmploymentSecurityOffice> getPublicEmploymentSecurityOfficeById(String cid, String pubEmpSecOfficeCode);

    void add(PublicEmploymentSecurityOffice domain);

    void update(PublicEmploymentSecurityOffice domain);

    void remove(String pubEmpSecOfficeCode);

    List<PublicEmploymentSecurityOffice> getByCidAndCodes(String cid, List<String> codes);
}
