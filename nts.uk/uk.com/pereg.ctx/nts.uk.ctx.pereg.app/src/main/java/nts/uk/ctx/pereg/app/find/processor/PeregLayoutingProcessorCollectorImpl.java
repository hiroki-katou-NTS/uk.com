package nts.uk.ctx.pereg.app.find.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import find.person.contact.PersonContactDto;
import find.person.info.PersonDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.businesstype.BusinessTypeDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.annualleave.AnnualLeaveDto;
import nts.uk.ctx.at.shared.app.find.shortworktime.ShortWorkTimeDto;
import nts.uk.ctx.at.shared.app.find.workingcondition.WorkingConditionDto;
import nts.uk.ctx.bs.employee.app.find.classification.affiliate.AffClassificationDto;
import nts.uk.ctx.bs.employee.app.find.department.affiliate.AffDeptHistDto;
import nts.uk.ctx.bs.employee.app.find.employee.contact.EmpInfoContactDto;
import nts.uk.ctx.bs.employee.app.find.employee.history.AffCompanyHistInfoDto;
import nts.uk.ctx.bs.employee.app.find.employee.mngdata.EmployeeDataMngInfoDto;
import nts.uk.ctx.bs.employee.app.find.jobtitle.affiliate.AffJobTitleDto;
import nts.uk.ctx.bs.employee.app.find.temporaryabsence.TempAbsHisItemDto;
import nts.uk.ctx.bs.employee.app.find.workplace.affiliate.AffWorlplaceHistItemDto;
import nts.uk.ctx.pereg.app.find.employment.history.EmploymentHistoryDto;
import nts.uk.shr.pereg.app.find.PeregFinder;;

@Stateless
@SuppressWarnings("serial")
public class PeregLayoutingProcessorCollectorImpl implements PeregFinderProcessorCollector {

	/** ctg single finder */
	private static final List<TypeLiteral<?>> FINDER_CTG_SINGLE_HANDLER_CLASSES = Arrays.asList(
			// CS00001  社員データ管理
			new TypeLiteral<PeregFinder<EmployeeDataMngInfoDto>>(){},
			// CS00002 個人基本情報
			new TypeLiteral<PeregFinder<PersonDto>>(){},
			// CS00003 所属会社履歴
			new TypeLiteral<PeregFinder<AffCompanyHistInfoDto>>(){},
			// CS00004 分類１
			new TypeLiteral<PeregFinder<AffClassificationDto>>(){},
			// CS00014 雇用
			new TypeLiteral<PeregFinder<EmploymentHistoryDto>>(){},
			// CS00015 部門本務	
			new TypeLiteral<PeregFinder<AffDeptHistDto>>(){},
			// CS00016 職位本務
			new TypeLiteral<PeregFinder<AffJobTitleDto>>(){},
			// CS00017 職場
			new TypeLiteral<PeregFinder<AffWorlplaceHistItemDto>>(){},
			// CS00018 休職休業
			new TypeLiteral<PeregFinder<TempAbsHisItemDto>>(){},
			// CS00019 短時間勤務
			new TypeLiteral<PeregFinder<ShortWorkTimeDto>>(){},
			// CS00020 労働条件
			new TypeLiteral<PeregFinder<WorkingConditionDto>>(){},
			// CS00021 勤務種別
			new TypeLiteral<PeregFinder<BusinessTypeDto>>(){},
			// CS00022 個人連絡先
			new TypeLiteral<PeregFinder<PersonContactDto>>(){},
			// CS00023 社員連絡先
			new TypeLiteral<PeregFinder<EmpInfoContactDto>>(){},
			// CS00024 年休情報
			new TypeLiteral<PeregFinder<AnnualLeaveDto>>(){}
			);

	@Override
	public Set<PeregFinder<?>> peregFinderCollect() {
		return FINDER_CTG_SINGLE_HANDLER_CLASSES.stream().map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregFinder<?>) obj).collect(Collectors.toSet());
	}

}
