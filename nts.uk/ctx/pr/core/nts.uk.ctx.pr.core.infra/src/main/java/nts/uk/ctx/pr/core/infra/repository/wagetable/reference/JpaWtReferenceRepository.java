/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.StringUtil;
import nts.uk.ctx.pr.core.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.SparePayAtr;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.PaydayRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.payday.Payday;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRefItem;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRef;
import nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository;

/**
 * The Class JpaWtReferenceRepository.
 */
@Stateless
public class JpaWtReferenceRepository extends JpaRepository implements WtReferenceRepository {

	/** The Payday repository. */
	@Inject
	private PaydayRepository paydayRepository;

	/** The basedate param. */
	private final String PREFIX_BASEDATE_PARAM = "@BASEDATE_";

	/** The comma. */
	private final String COMMA = ",";

	/** The space. */
	private final String SPACE = " ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository#
	 * getMasterRefItem(nts.uk.ctx.pr.core.dom.wagetable.reference.WtMasterRef)
	 */
	@Override
	public List<WtCodeRefItem> getMasterRefItem(WtMasterRef masterRef, YearMonth startMonth) {
		// Create query string.
		StringBuilder strBuilder = new StringBuilder();

		// Add query fields
		strBuilder.append("SELECT ");
		strBuilder.append(masterRef.getWageRefField());
		strBuilder.append(COMMA);
		strBuilder.append(masterRef.getWageRefDispField());

		// from table
		strBuilder.append(" FROM ");
		strBuilder.append(masterRef.getWageRefTable());

		// Add conditions
		strBuilder.append(" WHERE CCD = '");
		strBuilder.append(masterRef.getCompanyCode());
		strBuilder.append("'");

		// Add ref query
		String refQuery = masterRef.getWageRefQuery();
		Map<String, Object> mapValues = new HashMap<>();
		if (!StringUtil.isNullOrEmpty(refQuery, true)) {

			List<String> params = this.detectParams(refQuery);
			params.stream().forEach(param -> {
				// Check is base date
				if (param.contains(PREFIX_BASEDATE_PARAM)) {
					mapValues.put(param,
							this.getBaseDate(masterRef.getCompanyCode(), startMonth.v(), param));
				}
			});

			// TODO: apply setParameter after change prefix :
			// mapValues.keySet().stream().forEach(item -> {
			// refQuery.replaceAll(item, "'" + mapValues.get(item) + "'");
			// });

			strBuilder.append(" AND ");
			strBuilder.append(refQuery);
		}

		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Create query
		TypedQuery<Object[]> query = em.createQuery(strBuilder.toString(), Object[].class);

		// TODO: apply setParameter after change prefix :
		if (!StringUtil.isNullOrEmpty(refQuery, true)) {
			// Set parameter
			mapValues.keySet().stream().forEach(item -> {
				query.setParameter(item, mapValues.get(item));
			});
		}

		// Get results
		List<Object[]> results = query.getResultList();

		// Convert data
		List<WtCodeRefItem> codeRefItems = results.stream()
				.map(result -> new WtCodeRefItem((String) result[0], (String) result[1]))
				.collect(Collectors.toList());

		// Return
		return codeRefItems;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.wagetable.reference.WtReferenceRepository#
	 * getCodeRefItem(nts.uk.ctx.pr.core.dom.wagetable.reference.WtCodeRef)
	 */
	@Override
	public List<WtCodeRefItem> getCodeRefItem(WtCodeRef codeRef) {
		// Create query string.
		StringBuilder strBuilder = new StringBuilder();

		// Add query fields
		strBuilder.append("SELECT ");
		strBuilder.append(codeRef.getWagePersonField());
		strBuilder.append(COMMA);
		strBuilder.append(codeRef.getWagePersonField());

		// from table
		strBuilder.append(" FROM ");
		strBuilder.append(codeRef.getWagePersonTable());

		// Add conditions
		strBuilder.append(" WHERE CCD = '");
		strBuilder.append(codeRef.getCompanyCode());
		strBuilder.append("'");

		// Get entity manager
		EntityManager em = this.getEntityManager();

		// Create query
		TypedQuery<Object[]> query = em.createQuery(strBuilder.toString(), Object[].class);

		// Get results
		List<Object[]> results = query.getResultList();

		// Convert data
		List<WtCodeRefItem> codeRefItems = results.stream()
				.map(result -> new WtCodeRefItem((String) result[0], (String) result[1]))
				.collect(Collectors.toList());

		// Return
		return codeRefItems;
	}

	/**
	 * Gets the base date.
	 *
	 * @param companyCode
	 *            the company code
	 * @param processingYm
	 *            the processing ym
	 * @param baseDateParam
	 *            the base date param
	 * @return the base date
	 */
	private GeneralDate getBaseDate(String companyCode, Integer processingYm,
			String baseDateParam) {
		Integer processingNo = Integer.parseInt(baseDateParam.replace(PREFIX_BASEDATE_PARAM, ""));

		List<Payday> paydays = paydayRepository.select1_3(companyCode, processingNo,
				PayBonusAtr.SALARY.value, processingYm, SparePayAtr.NORMAL.value);

		return paydays.get(0).getStdDate();
	}

	/**
	 * Detect params.
	 *
	 * @param conditionStr
	 *            the condition str
	 * @return the list
	 */
	private List<String> detectParams(String conditionStr) {
		List<String> params = new ArrayList<>();

		// TODO: change prefix :
		String pattern = "(?:^|\\s)(:[^ ]+)";
		// String pattern = "(?:^|\\s)(@[^ ]+)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(conditionStr);
		while (m.find()) {
			params.add(m.group(1));
		}

		return params;
	}
}