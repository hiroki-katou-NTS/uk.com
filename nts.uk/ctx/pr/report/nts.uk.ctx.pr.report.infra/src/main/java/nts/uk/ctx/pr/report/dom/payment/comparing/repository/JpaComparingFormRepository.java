package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeader;
import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptPaycompFormHead;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptPaycompFormHeadPK;

@Stateless
public class JpaComparingFormRepository extends JpaRepository implements ComparingFormRepository {

	private final String SELECT_COMPARING_FORM_HEADER_BY_COMPANYCODE = "SELECT c FROM QlsptPaycompFormHead c WHERE c.paycompFormHeadPK.companyCode = :ccd";

	@Override
	public List<ComparingFormHeader> getListComparingFormHeader(String companyCode) {
		return this.queryProxy().query(SELECT_COMPARING_FORM_HEADER_BY_COMPANYCODE, QlsptPaycompFormHead.class)
				.setParameter("ccd", companyCode).getList(c -> convertToDomainFormHeader(c));
	}

	@Override
	public void InsertComparingFormHeader(ComparingFormHeader comparingFormHeader) {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdateComparingFormHeader(ComparingFormHeader comparingFormHeader) {
		// TODO Auto-generated method stub

	}

	@Override
	public void DeleteComparingFormHeader(String companyCode, String formCode) {
		// TODO Auto-generated method stub

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
