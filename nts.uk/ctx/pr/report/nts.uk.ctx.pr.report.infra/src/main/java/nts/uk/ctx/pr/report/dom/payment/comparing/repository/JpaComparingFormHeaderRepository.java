package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeader;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeaderRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptPaycompFormHead;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptPaycompFormHeadPK;

@Stateless
public class JpaComparingFormHeaderRepository extends JpaRepository implements ComparingFormHeaderRepository {

	private final String SELECT_COMPARING_FORM_HEADER_BY_COMPANYCODE = "SELECT c FROM QlsptPaycompFormHead c "
			+ "WHERE c.paycompFormHeadPK.companyCode = :ccd";

	private final String SELECT_A_COMPARING_FORM_HEADER = "SELECT c FROM QlsptPaycompFormHead c "
			+ "WHERE c.paycompFormHeadPK.companyCode = :ccd and c.paycompFormHeadPK.formCode = :formCode";

	@Override
	public List<ComparingFormHeader> getListComparingFormHeader(String companyCode) {
		return this.queryProxy().query(SELECT_COMPARING_FORM_HEADER_BY_COMPANYCODE, QlsptPaycompFormHead.class)
				.setParameter("ccd", companyCode).getList(c -> convertToDomainFormHeader(c));
	}

	@Override
	public Optional<ComparingFormHeader> getComparingFormHeader(String companyCode, String formCode) {
		return this.queryProxy().query(SELECT_A_COMPARING_FORM_HEADER, QlsptPaycompFormHead.class)
				.setParameter("ccd", companyCode).setParameter("formCode", formCode)
				.getSingle(c -> convertToDomainFormHeader(c));
	}

	@Override
	public void insertComparingFormHeader(ComparingFormHeader comparingFormHeader) {
		this.commandProxy().insert(convertToEntityQlsptPaycompFormHead(comparingFormHeader));
	}

	@Override
	public void updateComparingFormHeader(ComparingFormHeader comparingFormHeader) {
		this.commandProxy().update(convertToEntityQlsptPaycompFormHead(comparingFormHeader));
	}

	@Override
	public void deleteComparingFormHeader(String companyCode, String formCode) {
		val entityPK = new QlsptPaycompFormHeadPK(companyCode, formCode);
		this.commandProxy().remove(QlsptPaycompFormHead.class, entityPK);
	}

	private static ComparingFormHeader convertToDomainFormHeader(QlsptPaycompFormHead entity) {
		return ComparingFormHeader.createFromJavaType(entity.paycompFormHeadPK.companyCode,
				entity.paycompFormHeadPK.formCode, entity.formName);
	}

	private static QlsptPaycompFormHead convertToEntityQlsptPaycompFormHead(ComparingFormHeader domain) {
		val entity = new QlsptPaycompFormHead();
		val entityPK = new QlsptPaycompFormHeadPK();
		entityPK.companyCode = domain.getCompanyCode();
		entityPK.formCode = domain.getFormCode().v();
		entity.paycompFormHeadPK = entityPK;
		entity.formName = domain.getFormName().v();
		return entity;
	}

}
